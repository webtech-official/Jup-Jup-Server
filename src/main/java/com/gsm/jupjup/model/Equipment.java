package com.gsm.jupjup.model;

import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Equipment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long equ_Idx;

    private String name;

    private String content;

    private int count;
    private String img_equipment;

    public void updateAll(EquipmentUploadDto equipmentUploadDto) {
        this.name = equipmentUploadDto.getName();
        this.content = equipmentUploadDto.getContent();
        this.count = equipmentUploadDto.getCount();
        this.img_equipment = equipmentUploadDto.getImgEquipmentLocation();
    }

    public void updateAmount(int count){
        this.count = count;
    }
}