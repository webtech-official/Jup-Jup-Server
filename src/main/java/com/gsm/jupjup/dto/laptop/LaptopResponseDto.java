package com.gsm.jupjup.dto.laptop;

import com.gsm.jupjup.model.Laptop;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LaptopResponseDto {
    private String laptopSerialNumber;
    private String laptopName;
    private String laptopBrand;
}
