package com.gsm.jupjup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class EquipmentAllow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eqa_Idx;

    @Column
    private int amount;

    @Column(length = 300)
    private String reason;

    @Enumerated(EnumType.STRING)
    private EquipmentAllowEnum equipmentEnum;

    @ManyToOne
    @JoinColumn(name = "equ_Idx")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "adminIdx")
    private Admin admin;

    public void update(int amount, String reason){
        this.amount = amount;
        this.reason = reason;
    }

    //================== 비지니스 메소드 ======================//

    //연관 관계 매핑
    public void EquipmentAdmin_Mapping(Admin admin, Equipment equipment){
        this.admin = admin;
        this.equipment = equipment;
    }

    public void Change_Accept() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Accept;
    }

    public void Change_Reject() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Reject;
    }

    public void Change_Return() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Return;
    }

    public void Change_Rental() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Return;
    }

    public void Delete_EquipmentAllow() {
        this.equipment = null;
    }


}