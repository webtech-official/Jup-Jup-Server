package com.gsm.jupjup.dto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentUploadDto {
    private MultipartFile img_equipment;
    private byte[] img_equipmentToByte;

    private String name;
    private String content;
    private int count;

    @Builder
    public EquipmentUploadDto(MultipartFile img_equipment, String name, String content, int count) throws IOException {
        this.img_equipment = img_equipment;
        this.img_equipmentToByte = img_equipment.getBytes();
        this.name = name;
        this.content = content;
        this.count = count;
    }

    public Equipment toEntity(){
        return Equipment.builder()
                .name(this.name)
                .img_equipment(this.img_equipmentToByte)
                .content(this.content)
                .count(this.count)
                .build();
    }
}
