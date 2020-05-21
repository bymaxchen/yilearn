package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.QuestionRuleCategoryReqVO;
import com.zhuiyi.ms.learn.service.impl.TQuestionCategoryServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TQuestionRuleCategoryServiceImpl;
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
@RequestMapping("/learn/api/v1/questionRules/category")
public class TQuestionRuleCategoryController {
    @Resource
    private TQuestionRuleCategoryServiceImpl tQuestionRuleCategoryService;


    @GetMapping("")
    public ResponseEntity findAll() {
        return ResponseBuilder.success(tQuestionRuleCategoryService.findAll());
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody QuestionRuleCategoryReqVO questionRuleCategoryReqVO) {
        return ResponseBuilder.success(tQuestionRuleCategoryService.updateById(id, questionRuleCategoryReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tQuestionRuleCategoryService.deleteById(id));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody QuestionRuleCategoryReqVO questionRuleCategoryReqVO) {
        return ResponseBuilder.success(tQuestionRuleCategoryService.create(questionRuleCategoryReqVO));
    }


}
