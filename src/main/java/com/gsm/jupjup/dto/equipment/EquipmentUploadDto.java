package com.gsm.jupjup.dto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Category;
import com.gsm.jupjup.model.Equipment;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentUploadDto {

    @NotBlank(message = "이미지를 넣어주세요.")
    private MultipartFile img_equipment;

    @NotBlank(message = "가자재 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "기자재 내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "기자재 카테고리를 입력해주세요.")
    private String category;

    private int count;

    @JsonIgnore
    private String imgEquipmentLocation;
    @JsonIgnore
    private Category categoryClass;

    @Builder
    public EquipmentUploadDto(MultipartFile img_equipment, String name, String content, int count, String category) {
        this.img_equipment = img_equipment;
        this.category = category;
        this.name = name;
        this.content = content;
        this.count = count;
    }

    public Equipment toEntity(){
        return Equipment.builder()
                .category(categoryClass)
                .name(this.name)
                .img_equipment(this.imgEquipmentLocation)
                .content(this.content)
                .count(this.count)
                .build();
    }
}
