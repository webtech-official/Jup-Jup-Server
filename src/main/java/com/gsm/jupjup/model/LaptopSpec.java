package com.gsm.jupjup.model;

import com.gsm.jupjup.dto.laptopSpec.LaptopSpecDto;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LaptopSpec extends BaseTimeEntity {
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

    @Column
    // 노트북 이름
    private String laptopName;

    @Column
    // 노트북 제조사
    private String laptopBrand;

    public void update(LaptopSpecDto laptopSpecDto){
        this.CPU = laptopSpecDto.getCPU();
        this.GPU = laptopSpecDto.getGPU();
        this.RAM = laptopSpecDto.getRAM();
        this.SSD = laptopSpecDto.getSSD();
        this.HDD = laptopSpecDto.getHDD();
    }
}
