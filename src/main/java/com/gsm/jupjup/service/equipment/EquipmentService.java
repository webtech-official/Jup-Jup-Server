package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.advice.exception.FileExtensionNotMatchImageException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
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
        //파일 저장후 image Path 변수에 담기
        String equipmentImgPath = SaveImgFile(equipmentUploadDto.getImg_equipment());
        //equipmentUploadDto 에 file path 값 념겨줌
        equipmentUploadDto.setImgEquipmentLocation(equipmentImgPath);

        Equipment equipmentDomain = equipmentUploadDto.toEntity();
        equipmentRepo.save(equipmentDomain);
    }

    @Transactional
    public void update(String name, int count){
        Equipment equipment = equipmentFindBy(name);
        equipment.update(count);
    }

    @Transactional
    public void deleteByName(String name){
        String equipmentName = equipmentFindBy(name).getName();
        equipmentRepo.deleteAllByName(equipmentName);
    }

    @Transactional(readOnly = true)
    public EquipmentResDto findByIdx(String name) throws IOException {
        Equipment equipment = equipmentFindBy(name);
        EquipmentResDto equipmentResDto = new EquipmentResDto(equipment);
        //img 를 byte로 바꿔서 변환
        equipmentResDto.setImg_equipment(getImgByte(equipment.getImg_equipment()));

        return equipmentResDto;
    }


    public List<EquipmentResDto> findAll() throws IOException {
        List<Equipment> equipmentList = equipmentRepo.findAll();
        List<EquipmentResDto> equipmentResDtoList = new ArrayList<>();

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
            System.out.println(e.getName());

        }
        return equipmentResDtoList;
    }

    /******일반 Method 컨트롤러에서 매소드 호출 안함******/
    //Equipment를 name으로 찾고 Entity만드는 매서드
    public Equipment equipmentFindBy(String name){
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    /** img save method
     * img 예외를 체크한후 img file을 저장한다.
     * @param img
     * @return imgLocation (이미지 주소)
     * @throws ImageNotFoundException, FileExtensionNotMatchImageException, IOException
     */
    public String SaveImgFile(MultipartFile img) throws IOException {
        final String imgDirectoryPath = "src/main/resources/static/image/";    //static directory 위치
        String nameOfImg = null;
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
        if(img.isEmpty())
            throw new ImageNotFoundException();
        else if(!img.getContentType().split("/")[0].equals("image"))  //파일 확장자가 image 가 아니면
            throw new FileExtensionNotMatchImageException();
        else
            return true;
    }

    public String imgNameMake(String imgName, String imageExtension){
        StringBuilder nameOfImg = new StringBuilder();

        nameOfImg.append(imgName);
        nameOfImg.append(new Date().getTime());
        nameOfImg.append("." + imageExtension);

        return nameOfImg.toString();
    }

    public byte[] getImgByte(String imgPath) throws IOException {
        File img = new File(imgPath);

        return Files.readAllBytes(img.toPath());
    }

}
