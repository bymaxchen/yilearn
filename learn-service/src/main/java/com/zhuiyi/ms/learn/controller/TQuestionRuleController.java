package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleReqVO;
import com.zhuiyi.ms.learn.service.impl.TQuestionRuleServiceImpl;
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
 * @since 2019-10-16
 */
@RestController
@RequestMapping("/learn/api/v1/questionRules")
public class TQuestionRuleController {
    @Resource
    private TQuestionRuleServiceImpl tQuestionRuleService;

    @PostMapping("list")
    public ResponseEntity findAll(@RequestBody QuestionRuleListReqVO questionRuleListReqVO) {
        return ResponseBuilder.success(tQuestionRuleService.findAll(questionRuleListReqVO));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody QuestionRuleReqVO questionRuleReqVO) {
        return ResponseBuilder.success(tQuestionRuleService.create(questionRuleReqVO));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody QuestionRuleReqVO questionRuleReqVO) {
        return ResponseBuilder.success(tQuestionRuleService.updateById(id, questionRuleReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tQuestionRuleService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tQuestionRuleService.getById(id));
    }

    @GetMapping("/{id}/questionList")
    public ResponseEntity findAllQuestions(@PathVariable("id") Integer ruleId) {
        return ResponseBuilder.success(tQuestionRuleService.findAllQuestions(ruleId));
    }

    @PostMapping("/list/detail")
    public ResponseEntity findAllDetail(@RequestBody QuestionRuleListReqVO questionRuleListReqVO) {
        return ResponseBuilder.success(tQuestionRuleService.findAllDetail(questionRuleListReqVO));
    }
}
