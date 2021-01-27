package com.gsm.jupjup.dto.equipment;

import com.gsm.jupjup.model.Equipment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentResDto {
    private String name;
    private byte[] img_equipment;
    private String content;
    private int count;

    @Builder
    public EquipmentResDto(String name, String content, byte[] img_equipment, int count){
        this.name = name;
        this.content = content;
        this.img_equipment = img_equipment;
        this.count = count;
    }

    public EquipmentResDto(Equipment equipmentDomain){
        this.name = equipmentDomain.getName();
        this.content = equipmentDomain.getContent();
        this.count = equipmentDomain.getCount();
    }
}
