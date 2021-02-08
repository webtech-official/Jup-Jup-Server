package com.gsm.jupjup.controller.v1;

import com.gsm.jupjup.dto.notice.NoticeSaveDto;
import com.gsm.jupjup.model.Laptop;
import com.gsm.jupjup.model.Notice;
import com.gsm.jupjup.model.response.CommonResult;
import com.gsm.jupjup.model.response.ListResult;
import com.gsm.jupjup.model.response.ResponseService;
import com.gsm.jupjup.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"3. 공지사항"})
@RestController
@RequestMapping("/v1")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ResponseService responseService;

    @ApiOperation(value = "공지사항 등록", notes = "관리자가 공지사항을 등록한다.")
    @PostMapping("/notice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    public CommonResult saveNotice(@RequestBody NoticeSaveDto noticeSaveDto, HttpServletRequest req){
        noticeService.SaveNotice(noticeSaveDto, req);
        return responseService.getSuccessResult();
    }

}
