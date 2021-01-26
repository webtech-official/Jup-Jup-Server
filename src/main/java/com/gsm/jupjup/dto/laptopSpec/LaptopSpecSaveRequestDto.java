package com.gsm.jupjup.dto.laptopSpec;

import com.gsm.jupjup.model.LaptopSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LaptopSpecSaveRequestDto {
    private String CPU;
    private String GPU;
    private String RAM;
    private String SSD;
    private String HDD;

    @Builder
    public LaptopSpecSaveRequestDto(String CPU, String GPU, String RAM, String SSD, String HDD){
        this.CPU = CPU;
        this.GPU = GPU;
        this.RAM = RAM;
        this.SSD = SSD;
        this.HDD = HDD;
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
                .build();
    }
}
