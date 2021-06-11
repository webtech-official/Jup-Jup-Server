package com.gsm.jupjup.dto.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSaveDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @JsonIgnore
    private Long adminIdx;

    public Notice toEntity(){
        return Notice.builder()
                .title(this.title)
                .content(this.content)
                .adminIdx(this.adminIdx)
                .build();
    }

}
