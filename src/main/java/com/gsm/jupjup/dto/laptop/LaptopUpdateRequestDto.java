package com.gsm.jupjup.dto.laptop;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopUpdateRequestDto {
    private String laptopName;
    private String laptopBrand;
}
