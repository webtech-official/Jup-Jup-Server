package com.gsm.jupjup.model;

import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class EquipmentAllow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eqa_Idx;

    @Column
    private int amount;

    @Column(length = 300)
    private String reason;

    @Enumerated(EnumType.STRING)
    private EquipmentAllowEnum equipmentEnum;

    @CreatedDate
    private LocalDateTime allow_at;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "eq_Idx")
    private Equipment equipment;


    public void update(int amount, String reason){
        this.amount = amount;
        this.reason = reason;
    }
}