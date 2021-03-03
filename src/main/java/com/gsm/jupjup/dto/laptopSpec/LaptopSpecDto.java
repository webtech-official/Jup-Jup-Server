package com.gsm.jupjup.dto.laptopSpec;

import com.gsm.jupjup.model.LaptopSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LaptopSpecDto {
    private String CPU;
    private String GPU;
    private String RAM;
    private String SSD;
    private String HDD;
    private String laptopBrand;
    private String laptopName;

    @Builder
    public LaptopSpecDto(String CPU, String GPU, String RAM, String SSD, String HDD, String laptopBrand, String laptopName){
        this.CPU = CPU;
        this.GPU = GPU;
        this.RAM = RAM;
        this.SSD = SSD;
        this.HDD = HDD;
        this.laptopBrand = laptopBrand;
        this.laptopName = laptopName;
    }

    //LaptopSpecDomain에 값을 넘겨준다.
    public LaptopSpec toEntity(){
        return LaptopSpec.builder()
                .CPU(CPU)
                .GPU(GPU)
                .RAM(RAM)
                .RAM(RAM)
                .SSD(SSD)
                .HDD(HDD)
                .laptopBrand(laptopBrand)
                .laptopName(laptopName)
                .build();
    }
}
