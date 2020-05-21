package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.PaperCategoryReqVO;
import com.zhuiyi.ms.learn.service.impl.TPaperCategoryServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TPaperPartServiceImpl;
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
@RequestMapping("/learn/api/v1/papers/category")
public class TPaperCategoryController {
    @Resource
    private TPaperCategoryServiceImpl tPaperCategoryService;

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody PaperCategoryReqVO paperCategoryReqVO) {
        return ResponseBuilder.success(tPaperCategoryService.updateOne(id, paperCategoryReqVO));
    }

    @GetMapping("")
    public ResponseEntity findAll() {
        return ResponseBuilder.success(tPaperCategoryService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer paperCategoryId) {
        return ResponseBuilder.success(tPaperCategoryService.deleteById(paperCategoryId));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody PaperCategoryReqVO paperCategoryReqVO) {
        return ResponseBuilder.success(tPaperCategoryService.create(paperCategoryReqVO));
    }
}
