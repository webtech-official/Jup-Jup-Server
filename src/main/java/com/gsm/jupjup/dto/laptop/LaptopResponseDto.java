package com.gsm.jupjup.dto.laptop;

import com.gsm.jupjup.model.Laptop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LaptopResponseDto {
    private String laptopSerialNumber;
    private String laptopName;
    private String laptopBrand;
    private LocalDateTime creationTime;

    @Builder
    public LaptopResponseDto(Laptop entity){
        this.laptopSerialNumber = entity.getLaptopSerialNumber();
        this.laptopName = entity.getLaptopName();
        this.laptopBrand = entity.getLaptopBrand();
    }
}
