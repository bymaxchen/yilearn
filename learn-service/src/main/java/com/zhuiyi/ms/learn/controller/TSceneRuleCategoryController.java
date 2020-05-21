package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleCategoryReqVO;
import com.zhuiyi.ms.learn.service.impl.TSceneRuleCategoryServiceImpl;
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
 * @since 2019-10-22
 */
@RestController
@RequestMapping("/learn/api/v1/sceneRules/category")
public class TSceneRuleCategoryController {
    @Resource
    private TSceneRuleCategoryServiceImpl tSceneRuleCategoryService;


    @GetMapping("")
    public ResponseEntity findAll() {
        return ResponseBuilder.success(tSceneRuleCategoryService.findAll());
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody SceneRuleCategoryReqVO questionRuleCategoryReqVO) {
        return ResponseBuilder.success(tSceneRuleCategoryService.updateById(id, questionRuleCategoryReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id, @RequestParam("type") Integer type) {
        return ResponseBuilder.success(tSceneRuleCategoryService.deleteById(id, type));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody SceneRuleCategoryReqVO questionRuleCategoryReqVO) {
        return ResponseBuilder.success(tSceneRuleCategoryService.create(questionRuleCategoryReqVO));
    }
}
