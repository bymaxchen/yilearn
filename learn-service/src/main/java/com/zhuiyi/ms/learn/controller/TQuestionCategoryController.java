package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.QuestionCategoryReqVO;
import com.zhuiyi.ms.learn.service.impl.TQuestionCategoryServiceImpl;
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
@RestController
@RequestMapping("/learn/api/v1/questions/category")
public class TQuestionCategoryController {
    @Resource
    private TQuestionCategoryServiceImpl tQuestionCategoryService;

    @GetMapping("")
    public ResponseEntity findAll(@RequestParam("type") Integer type) {
        return ResponseBuilder.success(tQuestionCategoryService.findAll(type));
    }

    @PutMapping("/{questionCategoryId}")
    public ResponseEntity update(@PathVariable("questionCategoryId") Integer questionCategoryId, @RequestBody QuestionCategoryReqVO questionCategoryReqVO) {
        return ResponseBuilder.success(tQuestionCategoryService.updateById(questionCategoryId, questionCategoryReqVO));
    }

    @DeleteMapping("/{questionCategoryId}")
    public ResponseEntity delete(@PathVariable("questionCategoryId") Integer questionCategoryId) {
        return ResponseBuilder.success(tQuestionCategoryService.deleteById(questionCategoryId));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody QuestionCategoryReqVO questionCategoryReqVO) {
        return ResponseBuilder.success(tQuestionCategoryService.create(questionCategoryReqVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tQuestionCategoryService.getById(id));
    }
}
