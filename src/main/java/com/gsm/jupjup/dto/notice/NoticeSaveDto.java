package com.gsm.jupjup.dto.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.EquipmentAllow;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.EquipmentAllowEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSaveDto {

    private String title;
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
