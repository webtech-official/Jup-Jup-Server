package com.gsm.jupjup.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LaptopSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specIdx;

    @Column
    private String CPU;

    @Column
    private String GPU;

    @Column
    private String RAM;

    @Column
    private String SSD;

    @Column
    private String HDD;
}
