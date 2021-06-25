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

    @Column(nullable = false, length = 1000)
    private String CPU;

    @Column(nullable = false, length = 1000)
    private String GPU;

    @Column(nullable = false, length = 1000)
    private String RAM;

    @Column(nullable = false, length = 1000)
    private String SSD;

    @Column(nullable = false, length = 1000)
    private String HDD;

    @Column(nullable = false, length = 1000)     // 노트북 이름
    private String laptopName;

    @Column(nullable = false, length = 1000)   // 노트북 제조사
    private String laptopBrand;

    public void update(LaptopSpecDto laptopSpecDto){
        this.CPU = laptopSpecDto.getCPU();
        this.GPU = laptopSpecDto.getGPU();
        this.RAM = laptopSpecDto.getRAM();
        this.SSD = laptopSpecDto.getSSD();
        this.HDD = laptopSpecDto.getHDD();
    }
}
