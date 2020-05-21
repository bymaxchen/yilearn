package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.ExamSessionReqVO;
import com.zhuiyi.ms.learn.service.impl.TExamSessionServiceImpl;
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
 * @since 2019-12-31
 */
@RestController
@RequestMapping("/learn/api/v1/examSession")
public class TExamSessionController {
    @Resource
    private TExamSessionServiceImpl tExamSessionService;

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody ExamSessionReqVO examSessionReqVO) {
        return ResponseBuilder.success(tExamSessionService.update(id, examSessionReqVO));
    }

    @GetMapping("/challengeMissionExam")
    public ResponseEntity getChallengeMissionExam(@RequestParam("agentId") Integer agentId) {
        return ResponseBuilder.success(tExamSessionService.getChallengeMissionExam(agentId));
    }

    @PostMapping("/finishSession")
    public ResponseEntity finishSession(@RequestBody ExamSessionReqVO examSessionReqVO) {
        return ResponseBuilder.success(tExamSessionService.finishSession(examSessionReqVO));
    }

    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody ExamSessionReqVO examSessionReqVO) {
        return ResponseBuilder.success(tExamSessionService.findAll(examSessionReqVO));
    }
}
