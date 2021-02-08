package com.gsm.jupjup.service.notice;

import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.SingleResult;

import javax.servlet.http.HttpServletRequest;


public interface NoticeService {

    //공지 사항 저장
    Long SaveNotice(NoticeSaveDto noticeSaveDto);

    //공지 사항 수정
    CommonResult UpdateNotice(NoticeSaveDto noticeSaveDto, Long noticeIdx);

    //공지 사항 삭제
    void DeleteNotice(Long noticeIdx);

    //공지 사항 모두 조회
    ListResult<Notice> FindAllNotice();

    //공지 사항 Idx로 조회
    SingleResult<Notice> FindByNoticeIdx(Long noticeIdx);

}
