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
@Service
public class EquipmentServiceImpl implements EquipmentService{

    private final EquipmentRepo equipmentRepo;
    private final EquipmentAllowRepo equipmentAllowRepo;
    private final JPAQueryFactory query;
    private final S3Uploader s3Uploader;

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

    @Transactional
    @Override
    public void update(Long eq_idx, int count) {
        Equipment equipment = equipmentRepo.findById(eq_idx).orElseThrow(null);
        equipment.updateAmount(equipment.getCount() + count);
    }

    @Transactional
    @Override
    public void AllUpdate(Long eq_idx, EquipmentUploadDto equipmentUploadDto) throws IOException {
        Equipment equipment = equipmentRepo.findById(eq_idx).orElseThrow(null);
        if(!equipment.getName().equals(equipmentUploadDto.getName())){
            duplicateChk(equipmentUploadDto.getName());
        }

        String oldFileS3Location = s3Uploader.getLocationFileName(equipment.getImg_equipment());
        s3Uploader.deleteS3(oldFileS3Location);

        String equipmentImgPath = s3Uploader.upload(equipmentUploadDto.getImg_equipment(), "static");
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);
        equipment.updateAll(equipmentUploadDto);
    }


    @Override
    public void deleteByEquipmentIdx(Long idx){
        Equipment equipment = equipmentRepo.findById(idx).orElseThrow(EquipmentNotFoundException::new);
        String s3ImgLocation = s3Uploader.getLocationFileName(equipment.getImg_equipment()); // s3리소스 Location 가져오기
        List<EquipmentAllow> equipmentAllows = equipmentAllowRepo.findByEquipment(equipment);

        s3Uploader.deleteS3(s3ImgLocation);

        for (EquipmentAllow equipmentAllow : equipmentAllows) {
            equipmentAllow.Delete_Equipment();
        }
        equipmentRepo.deleteById(idx);
    }

    @Override
    public EquipmentResDto findByName(String name) throws IOException {
        Equipment equipment = equipmentFindBy(name);
        EquipmentResDto equipmentResDto = new EquipmentResDto(equipment);
        //img 를 byte 로 바꿔서 변환
        return equipmentResDto;
    }

    @Override
    public List<Equipment> findAll() throws IOException {
        List<Equipment> equipmentList = equipmentRepo.findAll();
        return equipmentList;
    }


    @Override
    //Equipment 를 name 으로 찾고 Entity 만드는 매서드
    public Equipment equipmentFindBy(String name){
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    /**
     *
     * @param keyword
     * @return
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

    @Override
    public Equipment findByIdx(Long idx) {
        return equipmentRepo.findById(idx).orElseThrow(null);
    }

    /******ServiceImpl 에서만 사용하는 매서드******/
    public boolean duplicateChk(String name){
        try{
            equipmentFindBy(name);
        } catch (EquipmentNotFoundException e){
            return false;
        }
        throw new EquipmentDuplicateException();
    }


}
