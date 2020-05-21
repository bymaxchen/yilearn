package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.entity.vo.req.ExamResultReqVO;
import com.zhuiyi.ms.learn.service.impl.ExamHistoryDirector;
import com.zhuiyi.ms.learn.service.impl.TExamServiceImpl;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@RestController
@RequestMapping("/learn/api/v1/exam")
public class TExamController {

    @Resource
    private TExamServiceImpl tExamService;

    @Autowired
    private ExamHistoryDirector examHistoryDirector;

    @PostMapping("result")
    public ResponseEntity create(@RequestBody ExamResultReqVO examResultReqVO) {
        return ResponseBuilder.success(examHistoryDirector.perform(examResultReqVO));
    }

    @GetMapping("result/{id}")
    public ResponseEntity findOne(@PathVariable("id") String id) {
        return ResponseBuilder.success(tExamService.findOne(id));
    }
}
