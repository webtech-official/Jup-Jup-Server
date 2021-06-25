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

    @Column(nullable = false)
    private String name;

    @Column(length = 4000)
    private String content;

    @Column(nullable = false)
    private int count;

    private String img_equipment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryIdx")
    private Category category;

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