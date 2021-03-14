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
}