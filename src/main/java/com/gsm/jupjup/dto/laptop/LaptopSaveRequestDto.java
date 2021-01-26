package com.gsm.jupjup.dto.laptop;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long specIdx;

    @Builder
    public LaptopSaveRequestDto(String laptopName, String laptopBrand, Long specIdx){
        this.laptopName = laptopName;
        this.laptopBrand = laptopBrand;
        this.specIdx = specIdx;
    }
}
