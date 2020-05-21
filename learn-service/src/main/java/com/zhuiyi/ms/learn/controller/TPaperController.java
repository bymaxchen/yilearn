package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.PaperListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.PaperReqVO;
import com.zhuiyi.ms.learn.service.impl.TPaperCategoryServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TPaperServiceImpl;
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
@RequestMapping("/learn/api/v1/papers")
public class TPaperController {
    @Resource
    private TPaperServiceImpl tPaperService;

    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody PaperListReqVO paperListReqVO) {
        return ResponseBuilder.success(tPaperService.findAll(paperListReqVO));
    }

    @GetMapping("/list/question/{id}")
    public ResponseEntity findAllByQuestionId(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tPaperService.findAllByQuestionId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tPaperService.removeById(id));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody PaperReqVO paperReqVO) {
        return ResponseBuilder.success(tPaperService.create(paperReqVO));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody PaperReqVO paperReqVO) {
        return ResponseBuilder.success(tPaperService.updateOne(id, paperReqVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tPaperService.findOne(id));
    }

    @PutMapping("/removeResetFlag/{id}")
    public ResponseEntity RemoveResetFlag(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tPaperService.RemoveResetFlag(id));
    }

}
