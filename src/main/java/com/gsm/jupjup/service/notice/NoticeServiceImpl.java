package com.gsm.jupjup.service.notice;

import com.gsm.jupjup.advice.exception.NotFoundNoticeException;
import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Admin;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.QNotice;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.service.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.repo.NoticeRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

    private final ResponseService responseService;
    private final NoticeRepo noticeRepo;
    private final JPAQueryFactory query;

    @Override
    public Long SaveNotice(NoticeSaveDto noticeSaveDto) {
        //현재 로그인된 사용자 구하기
        Long userIdx = currentUser().getAuth_Idx();
        noticeSaveDto.setAdminIdx(userIdx);
        return noticeRepo.save(noticeSaveDto.toEntity()).getNotice_Idx();
    }

    @Transactional
    @Override
    public void UpdateNotice(NoticeSaveDto noticeSaveDto, Long noticeIdx) {
        Notice notice = noticeRepo.findById(noticeIdx).orElseThrow(NotFoundNoticeException::new);
        notice.updateAll(noticeSaveDto);
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
        Notice notice = noticeRepo.findById(noticeIdx).orElseThrow(NotFoundNoticeException::new);
        return responseService.getSingleResult(notice);
    }

    @Override
    public List<Notice> findALL(){
        QNotice qNotice = QNotice.notice;

        List<Notice> noticeList = query.select(qNotice)
                .from(qNotice)
                .fetch();
        return noticeList;
    }


    //현재 사용자의 ID를 Return
    public static Admin currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin user = (Admin) authentication.getPrincipal();
        return user;
    }

    //현재 사용자가 "ROLE_ADMIN"이라는 ROLE을 가지고 있는지 확인
    public static boolean hasAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().filter(o -> o.getAuthority().equals("ROLE_ADMIN")).findAny().isPresent();
    }
}
