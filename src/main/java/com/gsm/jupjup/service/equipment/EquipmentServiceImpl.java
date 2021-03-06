package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.EquipmentDuplicateException;
import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.QEquipment;
import com.gsm.jupjup.repo.EquipmentAllowRepo;
import com.gsm.jupjup.repo.EquipmentRepo;
import com.gsm.jupjup.util.S3Uploader;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EquipmentServiceImpl implements EquipmentService{

    private final EquipmentRepo equipmentRepo;
    private final EquipmentAllowRepo equipmentAllowRepo;
    private final JPAQueryFactory query;
    private final S3Uploader s3Uploader;

    /**
     * 기자재 저장
     * @param equipmentUploadDto 기자재 저장 정보
     * @throws IOException 에러 처리
     */
    @Override
    public void save(EquipmentUploadDto equipmentUploadDto) throws IOException {
        //Equipment name 을 기준으로 중복처리
        duplicateChk(equipmentUploadDto.getName());
        //파일 저장후 image Path 변수에 담기
        String equipmentImgPath = s3Uploader.upload(equipmentUploadDto.getImg_equipment(), "static");
        //equipmentUploadDto 에 file path 값 념겨
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);
        Equipment equipment = equipmentUploadDto.toEntity();
        equipmentRepo.save(equipment);
    }

    /**
     * 기자재 수량 업데이트
     * @param name 해당 기자재 이름
     * @param count 업데이트 수량 (+)
     */
    @Transactional
    @Override
    public void update(String name, int count) {
        Equipment equipment = equipmentFindBy(name);
        equipment.updateAmount(equipment.getCount() + count);
    }

    /**
     * 기자재 정보 전체 수정
     * @param oldName 기자재의 전 이름
     * @param equipmentUploadDto 새롭게 저장할 기자재의 정보
     * @throws IOException 에러 처리
     */
    @Transactional
    @Override
    public void AllUpdate(String oldName, EquipmentUploadDto equipmentUploadDto) throws IOException {
        Equipment equipment = equipmentFindBy(oldName);
        if(!oldName.equals(equipmentUploadDto.getName())){
            duplicateChk(equipmentUploadDto.getName());
        }

        String oldFileS3Location = s3Uploader.getLocationFileName(equipment.getImg_equipment());
        s3Uploader.deleteS3(oldFileS3Location);

        String equipmentImgPath = s3Uploader.upload(equipmentUploadDto.getImg_equipment(), "static");
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);
        equipment.updateAll(equipmentUploadDto);
    }

    /**
     * 해당 기자재 삭제
     * @param idx 기자재 번호
     */
    @Override
    public void deleteByEquipmentIdx(Long idx){
        Equipment equipment = equipmentRepo.findById(idx).orElseThrow(EquipmentNotFoundException::new);
        String s3ImgLocation = s3Uploader.getLocationFileName(equipment.getImg_equipment()); // s3리소스 Location 가져오기
        List<EquipmentAllow> equipmentAllows = equipmentAllowRepo.findByEquipment(equipment);

        s3Uploader.deleteS3(s3ImgLocation);

        for (EquipmentAllow equipmentAllow : equipmentAllows) {
            equipmentAllow.setEquipment(null);
        }
        equipmentRepo.deleteById(idx);
    }

    /**
     * 기자재 이름 검색
     * @param name 해당 기자재 이름
     * @return EquipmentResDto
     * @throws IOException 에러 처리
     */
    @Override
    public EquipmentResDto findByName(String name) throws IOException {
        Equipment equipment = equipmentFindBy(name);
        EquipmentResDto equipmentResDto = new EquipmentResDto(equipment);
        //img 를 byte 로 바꿔서 변환
        return equipmentResDto;
    }

    /**
     * 기자재 모두 검색
     * @return equipmentList
     * @throws IOException 에러 처리
     */
    @Override
    public List<Equipment> findAll() throws IOException {
        List<Equipment> equipmentList = equipmentRepo.findAll();
        return equipmentList;
    }

    /**
     * 기자재 이름 검색 함수
     * @param name 기자재 이름
     * @return Equipment
     */
    @Override
    public Equipment equipmentFindBy(String name){
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    /**
     * 기자재 키워드 검색
     * @param keyword 검색 키워드
     * @return equipmentList
     * query = query.where(qUserEntity.email.like(userEmail)); //지정된 str(userEmail)과 같으면 return
     * query = query.where(qUserEntity.email.contains(userEmail)); //지정된 str(userEmail)이 포함되는 경우 true를 return
     */
    @Override
    public List<Equipment> findByKeyword(String keyword) throws Exception {
        QEquipment qequipment = QEquipment.equipment;

        List<Equipment> equipmentList = query.select(qequipment)
                .from(qequipment)
                .where(qequipment.name.contains(keyword))
                .fetch();
        if(equipmentList.isEmpty())
            throw new Exception("");
        return equipmentList;
    }

    /**
     * 기자재 이름 중복 체크 함수
     * @param name 기자재 이름
     * @return boolean
     */
    public boolean duplicateChk(String name){
        try{
            equipmentFindBy(name);
        } catch (EquipmentNotFoundException e){
            return false;
        }
        throw new EquipmentDuplicateException();
    }


}
