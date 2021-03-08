package com.gsm.jupjup.model;

import com.gsm.jupjup.dto.equipment.EquipmentUploadDto;
import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notice_Idx;

    private String title;
    
    private String content;

    //Admin 외래키 매핑
    private Long adminIdx;

    /**
     * 공지사항 업데이트 메소드
     * @param noticeSaveDto 공지사항 업데이트 정보
     */
    public void updateAll(NoticeSaveDto noticeSaveDto) {
        this.title = noticeSaveDto.getTitle();
        this.content = noticeSaveDto.getContent();
    }

}
