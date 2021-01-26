package com.gsm.jupjup.dto.laptop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LaptopSaveRequestDto {
    private String laptopSerialNumber;
    private String laptopName;
    private String laptopBrand;
    private LocalDateTime creationTime;
    private Long specIdx;

    @Builder
    public LaptopSaveRequestDto(String laptopSerialNumber, String laptopName, String laptopBrand, Long specIdx){
        this.laptopSerialNumber = laptopSerialNumber;
        this.laptopName = laptopName;
        this.laptopBrand = laptopBrand;
        this.specIdx = specIdx;
    }
}