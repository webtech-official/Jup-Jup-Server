package com.gsm.jupjup.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Equipment {

    @Id
    private String name;

    private String content;

    private int count;

    @Lob
    private byte[] img_equipment; //BLOB

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EquipmentAllow> logs = new ArrayList<>();

    public void update(int count) {
        this.count = count;
    }

    public void updateAmount(int count){
        this.count = count;
    }
}