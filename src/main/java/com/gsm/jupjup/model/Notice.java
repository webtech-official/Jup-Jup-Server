package com.gsm.jupjup.model;

import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_Idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    //Admin 외래키 매핑
    private Long adminIdx;

    public void updateAll(NoticeSaveDto noticeSaveDto) {
        this.title = noticeSaveDto.getTitle();
        this.content = noticeSaveDto.getContent();
    }

}