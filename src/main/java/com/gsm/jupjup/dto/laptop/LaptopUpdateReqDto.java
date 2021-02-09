package com.gsm.jupjup.dto.laptop;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopUpdateReqDto {
    private String laptopName;
    private String laptopBrand;
}
