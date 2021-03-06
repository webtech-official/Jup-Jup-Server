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

    /**
     * ROLE_Accept으로 바꾸어주는 메소드
     */
    public void change_ROLE_Accept(){
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Accept;
    }

    /**
     * ROLE_Reject으로 바꾸어주는 메소드
     */
    public void change_ROLE_Reject(){
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Reject;
    }

    /**
     * ROLE_Return으로 바꾸어주는 메소드
     */
    public void change_ROLE_Return(){
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Return;
    }

    /**
     * ROLE_Rental으로 바꾸어주는 메소드
     */
    public void change_ROLE_Rental(){
        this.equipmentEnum = EquipmentAllowEnum.ROLE_Rental;
    }

    /**
     * Admin과 Equipment 관계 설정
     */
    public void Relation_Mapping(Equipment equipment, Admin admin) {
        this.admin = admin;
        this.equipment = equipment;
    }
}