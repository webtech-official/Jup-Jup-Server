package com.gsm.jupjup.model;

import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EquipmentAllow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eqa_Idx;

    @Column
    private int amount;

    @Column(length = 300)
    private String reason;

    @Enumerated(EnumType.STRING)
    private EquipmentAllowEnum equipmentEnum = EquipmentAllowEnum.ROLE_Waiting;

    @CreatedDate
    private LocalDateTime allow_at;

    @ManyToOne
    @JoinColumn(name = "eq_Idx")
    private Equipment equipment;

    @Builder
    public EquipmentAllow(int amount, String reason, Equipment equipment){
        this.amount = amount;
        this.reason = reason;
        this.equipment = equipment;
    }

    public void update(int amount, String reason){
        this.amount = amount;
        this.reason = reason;
    }

}
