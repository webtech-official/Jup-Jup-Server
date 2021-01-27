package com.gsm.jupjup.service.equipment;

import com.gsm.jupjup.advice.exception.EquipmentNotFoundException;
import com.gsm.jupjup.advice.exception.ImageNotFoundException;
import com.gsm.jupjup.dto.equipment.EquipmentResDto;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.repo.EquipmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EquipmentService {

    private final EquipmentRepo equipmentRepo;


    public void save(EquipmentUploadDto equipmentUploadDto) {
        if(equipmentUploadDto.getImg_equipment().isEmpty()) throw new ImageNotFoundException();

        Equipment equipmentDomain = equipmentUploadDto.toEntity();
        equipmentRepo.save(equipmentDomain);
    }

    @Transactional
    public void update(String name, int count) throws Exception {
        Equipment equipment = equipmentFindBy(name);
        equipment.update(count);
    }

    @Transactional
    public void deleteByName(String name) throws Exception {
        String equipmentName = equipmentFindBy(name).getName();
        equipmentRepo.deleteAllByName(equipmentName);
    }

    @Transactional(readOnly = true)
    public EquipmentResDto findByIdx(String name) throws Exception {
        Equipment equipment = equipmentFindBy(name);
        return new EquipmentResDto(equipment);
    }


    /******일반 Method 들******/

    //Equipment를 name으로 찾고 Entity만드는 매서드
    public Equipment equipmentFindBy(String name) throws Exception {
        return equipmentRepo.findByName(name).orElseThrow(EquipmentNotFoundException::new);
    }

    //img 파일 저장
    public void SaveImgFile(MultipartFile img){
        Date date = new Date();
        StringBuilder nameOfImg = new StringBuilder();

    }

    /** img 예외처리 method
     * img 를 예외체크를 해서 아무 예외도 나오지 않는다면 ture 를 반환한다.
     * @param img
     * @return ture(img가 아무 예외도 않나오면 반환)
     */
    public boolean imgChk(MultipartFile img){
        if(img.isEmpty())
            throw new ImageNotFoundException();
        else
            return true;

    }

}
