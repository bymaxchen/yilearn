package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleListReqVO;
import com.zhuiyi.ms.learn.entity.vo.req.SceneRuleReqVO;
import com.zhuiyi.ms.learn.service.impl.TSceneRuleServiceImpl;
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
@RequestMapping("/learn/api/v1/sceneRules")
public class TSceneRuleController {
    @Resource
    private TSceneRuleServiceImpl tSceneRuleService;

    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody SceneRuleListReqVO sceneRuleListReqVO) {
        return ResponseBuilder.success(tSceneRuleService.findAll(sceneRuleListReqVO));
    }


    @PostMapping("")
    public ResponseEntity create(@RequestBody SceneRuleReqVO questionRuleReqVO) {
        return ResponseBuilder.success(tSceneRuleService.create(questionRuleReqVO));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody SceneRuleReqVO sceneRuleReqVO) {
        return ResponseBuilder.success(tSceneRuleService.updateById(id, sceneRuleReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tSceneRuleService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tSceneRuleService.getById(id));
    }

    @GetMapping("/{id}/questionList")
    public ResponseEntity findAllQuestions(@PathVariable("id") Integer ruleId) {
        return ResponseBuilder.success(tSceneRuleService.findAllQuestions(ruleId));
    }

    @PostMapping("/list/detail")
    public ResponseEntity findAllDetail(@RequestBody SceneRuleListReqVO sceneRuleListReqVO) {
        return ResponseBuilder.success(tSceneRuleService.findAllDetail(sceneRuleListReqVO));
    }
}
