package com.gsm.jupjup.controller.release;

import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.model.response.SingleResult;
import com.gsm.jupjup.service.notice.NoticeService;
import com.querydsl.jpa.impl.JPAQuery;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Api(tags = {"6. 공지사항"})
@RestController
@CrossOrigin(origins = "http://localhost:3000") //해당 origin 승인하기
@RequestMapping("/v1")
public class NoticeController {

    private final NoticeService noticeService;
    private final ResponseService responseService;

    @ApiOperation(value = "공지사항 등록", notes = "관리자가 공지사항을 등록한다.")
    @PostMapping("/admin/notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult saveNotice(@ApiParam(value = "공지 사항 등록 정보", required = true) @RequestBody NoticeSaveDto noticeSaveDto) {
        noticeService.SaveNotice(noticeSaveDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "공지사항 삭제", notes = "관리자가 공지사항을 삭제한다.")
    @DeleteMapping("/admin/notice/{noticeidx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult deleteNotice(@ApiParam(value = "공지 사항 Idx", required = true) @PathVariable Long noticeidx){
        noticeService.DeleteNotice(noticeidx);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "공지사항 모두 조회", notes = "관리자/회원이 공지사항을 모두 조회한다.")
    @GetMapping("/notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public ListResult<Notice> FindAllNotice(){
        return noticeService.FindAllNotice();
    }

    @ApiOperation(value = "공지사항 1개 조회", notes = "관리자/회원이 공지사항을 1개 조회한다.")
    @GetMapping("/notice/{noticeidx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public SingleResult<Notice> FindNoticeByNoticeIdx(@ApiParam(value = "공지 사항 Idx", required = true) @PathVariable Long noticeidx){
        return noticeService.FindByNoticeIdx(noticeidx);
    }

    @ApiOperation(value = "공지사항 수정", notes = "관리자가 공지 사항을 수정한다.")
    @PutMapping("/admin/notice/{noticeidx}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult updateNotice(@ApiParam(value = "공지 사항 수정 정보", required = true) @RequestBody NoticeSaveDto noticeSaveDto , @ApiParam(value = "공지 사항 Idx", required = true) @PathVariable Long noticeidx){
        noticeService.UpdateNotice(noticeSaveDto, noticeidx);
        return responseService.getSuccessResult();
    }
}
