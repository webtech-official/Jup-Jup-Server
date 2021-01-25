package com.gsm.jupjup.dto.equipmentAllow;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentAllowSaveDto {
    private int amount;
    private String reason;

    @JsonIgnore
    private Equipment equipment;

    public EquipmentAllow toEntity(){
        return EquipmentAllow.builder()
                .amount(this.amount)
                .reason(this.reason)
                .equipment(this.equipment)
                .build();
    }
}
