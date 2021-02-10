package com.gsm.jupjup.dto.equipmentAllow;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Equipment;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
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
    private Boolean isReturn = false;


    public EquipmentAllow toEntity(){
        return EquipmentAllow.builder()
                .equipmentEnum(EquipmentAllowEnum.ROLE_Waiting)
                .amount(this.amount)
                .reason(this.reason)
                .isReturn(this.isReturn)
                .build();
    }
}