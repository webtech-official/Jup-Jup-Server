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

    //================= 비즈니스 메소드 =================//
    public void Change_ROLE_Rental() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Rental;
    }

    public void Change_ROLE_Return() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Return;
    }

    public void Change_ROLE_Reject() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Reject;
    }

    public void Change_ROLE_Accept() {
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Accept;
    }

    public void Equipment_Admin_Mapping(Equipment equipment, Admin admin) {
        this.equipment = equipment;
        this.admin = admin;
    }

    public void Delete_Equipment() {
        this.equipment = null;
    }
}