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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eq_Idx;

    private String name;

    @Lob
    private byte[] img_equipment; //BLOB

    private String content;

    private int count;

    public void update(String name) {
        this.name = name;
    }
}