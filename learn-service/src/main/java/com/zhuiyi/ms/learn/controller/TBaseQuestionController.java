package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.QuestionListReqVO;
import com.zhuiyi.ms.learn.service.impl.TBaseQuestionServiceImpl;
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
 * @since 2019-10-15
 */
@RequestMapping("/learn/api/v1/questions")
@RestController
public class TBaseQuestionController {
    @Resource
    private TBaseQuestionServiceImpl tBaseQuestionService;

    @PostMapping("")
    public ResponseEntity findAll(@RequestBody QuestionListReqVO questionListReqVO) {
        return ResponseBuilder.success(tBaseQuestionService.findAll(questionListReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tBaseQuestionService.removeById(id));
    }
}
