package com.gsm.jupjup.dto.laptop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.LaptopSpec;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaptopSaveReqDto {

    @NotBlank(message = "노트북 시리얼 번호을 입력해주세요.")
    private String laptopSerialNumber;

    @NotBlank(message = "학생 이름을 입력해주세요.")
    private String studentName;

    @Size(max = 4, min = 4, message = "학번은 4글자로 입력해주세요")
    @NotBlank(message = "학생 학번을 입력해주세요.")
    private String classNumber;

    @NotBlank(message = "스팩 번호를 입력해주세요.")
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
