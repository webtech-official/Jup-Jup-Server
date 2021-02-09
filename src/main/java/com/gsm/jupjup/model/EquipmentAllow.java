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
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class EquipmentAllow extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eqa_Idx;

    @Column
    private int amount;

    @Column(length = 300)
    private String reason;

    @Enumerated(EnumType.STRING)
    private EquipmentAllowEnum equipmentEnum;

    @Column
    private Boolean isReturn;

    private Long equipmentIdx;

    private Long adminIdx;

    public void update(int amount, String reason){
        this.amount = amount;
        this.reason = reason;
    }
}