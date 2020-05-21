package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.ChatLogListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;
import com.zhuiyi.ms.learn.service.impl.TChatLogServiceImpl;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@RestController
@RequestMapping("/learn/api/v1/chatLog")
public class TChatLogController {
    @Resource
    private TChatLogServiceImpl tChatLogService;

    @GetMapping("/list")
    public ResponseEntity findAll(ChatLogListReqVO chatLogListReqVO) {
        return ResponseBuilder.success(tChatLogService.findAll(chatLogListReqVO));
    }
}
