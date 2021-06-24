package com.gsm.jupjup.dto.laptopSpec;

import com.gsm.jupjup.model.LaptopSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LaptopSpecDto {
    @NotBlank(message = "CPU를 입력해주세요.")
    private String CPU;

    @NotBlank(message = "GPU를 입력해주세요.")
    private String GPU;

    @NotBlank(message = "RAM를 입력해주세요.")
    private String RAM;

    @NotBlank(message = "SSD를 입력해주세요.")
    private String SSD;

    @NotBlank(message = "HDD를 입력해주세요.")
    private String HDD;

    @NotBlank(message = "노트북 브랜드를 입력해주세요.")
    private String laptopBrand;

    @NotBlank(message = "노트북 이름을 입력해주세요.")
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
