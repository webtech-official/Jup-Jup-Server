package com.gsm.jupjup.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equ_Idx;

    private String name;

    private String content;

    private int count;

    @Lob
    private String img_equipment; //BLOB

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