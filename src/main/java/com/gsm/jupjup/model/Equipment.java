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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equ_Idx;

    private String name;

    private String content;

    private int count;
    private String img_equipment;

    /**
     * 기자재 전체 수정 메소드
     * @param equipmentUploadDto 기자재 업데이트 정보
     */
    public void updateAll(EquipmentUploadDto equipmentUploadDto) {
        this.name = equipmentUploadDto.getName();
        this.content = equipmentUploadDto.getContent();
        this.count = equipmentUploadDto.getCount();
        this.img_equipment = equipmentUploadDto.getImgEquipmentLocation();
    }

    /**
     * 기자재 수량 업데이트
     * @param count 기자재 수량
     */
    public void updateAmount(int count){
        this.count = count;
    }
}