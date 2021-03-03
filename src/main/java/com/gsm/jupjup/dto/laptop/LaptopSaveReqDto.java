package com.gsm.jupjup.dto.laptop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopSaveReqDto {
    private String laptopSerialNumber;
    private String studentName;
    private String classNumber;
    private Long specIdx;

    public Laptop toEntity(Admin admin, LaptopSpec laptopSpec){
        return Laptop.builder()
                .admin(admin)
                .laptopSerialNumber(this.laptopSerialNumber)
                .studentName(this.studentName)
                .classNumber(this.classNumber)
                .laptopSpec(laptopSpec)
                .build();
    }
}
