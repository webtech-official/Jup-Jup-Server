package com.gsm.jupjup.service.notice;

import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.NoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private ResponseService responseService;

    @Autowired
    NoticeRepo noticeRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public Long SaveNotice(NoticeSaveDto noticeSaveDto, HttpServletRequest req) {
        //현재 로그인된 사용자 구하기
        String userEmail = GetUserEmail(req);
        Admin admin = adminRepo.findByEmail(userEmail).orElseThrow(null);
        noticeSaveDto.setAdminIdx(admin.getAuth_Idx());
        return noticeRepo.save(noticeSaveDto.toEntity()).getNotice_Idx();
    }

    @Override
    public CommonResult UpdateNotice(NoticeSaveDto noticeSaveDto, Long noticeIdx) {
        Notice notice = noticeRepo.findById(noticeIdx).orElseThrow(null);
        notice.updateAll(noticeSaveDto);
        return null;
    }

    @Transactional
    @Override
    public void DeleteNotice(Long noticeIdx) {
        noticeRepo.deleteById(noticeIdx);
    }

    @Override
    public ListResult<Notice> FindAllNotice() {
        return responseService.getListResult(noticeRepo.findAll());
    }

    @Override
    public SingleResult<Notice> FindByNoticeIdx(Long noticeIdx) {
        Notice notice = noticeRepo.findById(noticeIdx).orElseThrow(null);
        return responseService.getSingleResult(notice);
    }

    public String GetUserEmail(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        String userEmail = jwtTokenProvider.getUserPk(token);
        return userEmail;
    }

}
