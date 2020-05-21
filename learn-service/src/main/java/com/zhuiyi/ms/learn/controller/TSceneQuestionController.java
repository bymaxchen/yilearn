package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.SceneQuestionReqVO;
import com.zhuiyi.ms.learn.service.impl.TSceneQuestionServiceImpl;
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
@RequestMapping("/learn/api/v1/sceneQuestions")
public class TSceneQuestionController {
    @Resource
    private TSceneQuestionServiceImpl tSceneQuestionService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody SceneQuestionReqVO sceneQuestionReqVO) {
        return ResponseBuilder.success(tSceneQuestionService.create(sceneQuestionReqVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tSceneQuestionService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody SceneQuestionReqVO sceneQuestionReqVO) {
        return ResponseBuilder.success(tSceneQuestionService.updateById(id, sceneQuestionReqVO));
    }

}
