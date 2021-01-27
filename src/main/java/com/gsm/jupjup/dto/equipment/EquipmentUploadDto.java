package com.gsm.jupjup.dto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Equipment;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentUploadDto {
    private MultipartFile img_equipment;
    private String name;
    private String content;
    private int count;
    @JsonIgnore
    private String imgEquipmentLocation;

    @Builder
    public EquipmentUploadDto(MultipartFile img_equipment, String name, String content, int count) {
        this.img_equipment = img_equipment;
        this.name = name;
        this.content = content;
        this.count = count;
    }

    public Equipment toEntity(){
        return Equipment.builder()
                .name(this.name)
                .img_equipment(this.imgEquipmentLocation)
                .content(this.content)
                .count(this.count)
                .build();
    }
}
