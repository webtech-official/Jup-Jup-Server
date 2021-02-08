package com.gsm.jupjup.service.notice;

import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Notice;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface NoticeService {

    //공지 사항 저장
    Long SaveNotice(NoticeSaveDto noticeSaveDto, HttpServletRequest req);

    //공지 사항 수정
    Notice UpdateNotice(NoticeSaveDto noticeSaveDto);

    //공지 사항 삭제
    void DeleteNotice(Long noticeIdx);

    //공지 사항 모두 조회
    Notice FindAllNotice();

    //공지 사항 Idx로 조회
    Notice FindByNoticeIdx(Long noticeIdx);

}
