package com.gsm.jupjup.model;

import lombok.*;

import javax.persistence.*;

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

    public void update(int count) {
        this.count = count;
    }

    public void updateAmount(int count){
        this.count = count;
    }
}