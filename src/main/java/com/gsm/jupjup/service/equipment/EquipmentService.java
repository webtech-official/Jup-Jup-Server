package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.EquipmentDuplicateException;
import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.advice.exception.FileExtensionNotMatchImageException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final EquipmentRepo equipmentRepo;

    public void save(EquipmentUploadDto equipmentUploadDto) throws IOException {
        //Equipment name 을 기준으로 중복처리
        duplicateChk(equipmentUploadDto.getName());
        //파일 저장후 image Path 변수에 담기
        String equipmentImgPath = SaveImgFile(equipmentUploadDto.getImg_equipment());
        //equipmentUploadDto 에 file path 값 념겨
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);
        Equipment equipment = equipmentUploadDto.toEntity();

        equipmentRepo.save(equipment);
    }

    @Transactional
    public void update(String name, int count) {
        Equipment equipment = equipmentFindBy(name);
        equipment.updateAmount(equipment.getCount() + count);
    }

    @Transactional
    public void AllUpdate(String oldName, EquipmentUploadDto equipmentUploadDto) throws IOException {
        Equipment equipment = equipmentFindBy(oldName);
        //파일 저장후 image Path 변수에 담기
        String equipmentImgPath = SaveImgFile(equipmentUploadDto.getImg_equipment());
        //기존에 있떤 파일 삭제
        imgDelete(equipment.getImg_equipment());
        //새로운 img path 저장
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);
        equipment.updateAll(equipmentUploadDto);
    }



    @Transactional
    public void deleteByName(String name){
        String equipmentName = equipmentFindBy(name).getName();
        equipmentRepo.deleteAllByName(equipmentName);
    }

    @Transactional(readOnly = true)
    public EquipmentResDto findByName(String name) throws IOException {
        Equipment equipment = equipmentFindBy(name);
        EquipmentResDto equipmentResDto = new EquipmentResDto(equipment);
        //img 를 byte 로 바꿔서 변환
        equipmentResDto.setImg_equipment(getImgByte(equipment.getImg_equipment()));

        return equipmentResDto;
    }

    @Transactional(readOnly = true)
    public List<EquipmentResDto> findAll() throws IOException {
        List<Equipment> equipmentList = equipmentRepo.findAll();
        List<EquipmentResDto> equipmentResDtoList = new ArrayList<>();

        // equipment List 를 equipmentResDtoList 형식에 밪게 변환해서 추가해주는 for 문
        for(Equipment e : equipmentList){
            //img 를 byte 로 바꿔서 변환
            byte[] EquipmentByteImg = getImgByte(e.getImg_equipment());
            //Equipment 값을 EquipmentResDto 로 가공해서 변환
            equipmentResDtoList.add(
                    new EquipmentResDto().builder()
                            .name(e.getName())
                            .content(e.getContent())
                            .count(e.getCount())
                            .img_equipment(EquipmentByteImg)
                        .build()
            );
        }
        return equipmentResDtoList;
    }

    /******Service 에서만 사용하는 매서******/
    //Equipment 를 name 으로 찾고 Entity 만드는 매서드
    public Equipment equipmentFindBy(String name){
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    public boolean duplicateChk(String name){
        try{
            equipmentFindBy(name);
        } catch (EquipmentNotFoundException e){
            return false;
        }
        throw new EquipmentDuplicateException();
    }

    /** img save method
     * img 예외를 체크한후 img file을 저장한다.
     * @param img
     * @return imgLocation (이미지 주소)
     * @throws ImageNotFoundException, FileExtensionNotMatchImageException, IOException
     */
    public String SaveImgFile(MultipartFile img) throws IOException {
        final String imgDirectoryPath = "src/main/resources/static/image/";    //static directory 위치
        String nameOfImg = "";
        //img null 체크후 true 반환시 파일 로직
        if(imgChk(img)) {
            nameOfImg = imgNameMake(img.getName(), img.getContentType().split("/")[1]);
            File targetImg = new File(imgDirectoryPath + nameOfImg);

            InputStream fileStream = img.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetImg);
        }
        return imgDirectoryPath + nameOfImg;
    }

    /** img 예외처리 method
     * img 를 예외체크를 해서 아무 예외도 나오지 않는다면 ture 를 반환한다.
     * @param img
     * @return ture(img 에 예외가 없을)
     */
    public boolean imgChk(MultipartFile img){
        if(img == null)
            throw new ImageNotFoundException();
        else if(!img.getContentType().split("/")[0].equals("image"))  //type 이 image 가 아니면
            throw new FileExtensionNotMatchImageException();
        else
            return true;
    }

    /**
     * 기자재 img 만들어주는 method
     * 현제 시간 + 사진이름 으로 만들어 반환
     * @param imgName
     * @param imageExtension
     * @return nameOfImg
     */
    public String imgNameMake(String imgName, String imageExtension){
        StringBuilder nameOfImg = new StringBuilder();

        nameOfImg.append(imgName);
        nameOfImg.append(new Date().getTime());
        nameOfImg.append("." + imageExtension);

        return nameOfImg.toString();
    }

    /**
     * img 가 저징된 path 를 받아서 img 를 byte[]로 변환
     * @param imgPath
     * @return
     * @throws IOException
     */
    public byte[] getImgByte(String imgPath) throws IOException {
        File img = new File(imgPath);

        return Files.readAllBytes(img.toPath());
    }

    //img 삭제 매서드
    public void imgDelete(String oldImgPath){
        File img = new File(oldImgPath);
        if(img.exists()){
            img.delete();
        }
    }

}
