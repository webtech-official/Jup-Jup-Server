package com.gsm.jupjup.service.notice;

import com.gsm.jupjup.config.security.JwtTokenProvider;
import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.repo.AdminRepo;
import com.gsm.jupjup.repo.NoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class NoticeServiceImpl implements NoticeService{

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
    public Notice UpdateNotice(NoticeSaveDto noticeSaveDto) {
        return null;
    }

    @Override
    public void DeleteNotice(Long noticeIdx) {

    }

    @Override
    public Notice FindAllNotice() {
        return null;
    }

    @Override
    public Notice FindByNoticeIdx(Long noticeIdx) {
        return null;
    }

    public String GetUserEmail(HttpServletRequest req){
        String token = jwtTokenProvider.resolveToken(req);
        String userEmail = jwtTokenProvider.getUserPk(token);
        return userEmail;
    }

}
