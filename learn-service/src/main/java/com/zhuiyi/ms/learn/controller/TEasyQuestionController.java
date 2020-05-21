package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.EasyQuestionReqVO;
import com.zhuiyi.ms.learn.service.impl.TBaseQuestionServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TEasyQuestionServiceImpl;
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
@RequestMapping("/learn/api/v1/easyQuestions")
public class TEasyQuestionController {
    @Resource
    private TEasyQuestionServiceImpl tEasyQuestionService;

    @PostMapping("")
    public ResponseEntity create(@RequestBody EasyQuestionReqVO easyQuestionReqVO) {
        return ResponseBuilder.success(tEasyQuestionService.create(easyQuestionReqVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tEasyQuestionService.findOne(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody EasyQuestionReqVO easyQuestionReqVO) {
        return ResponseBuilder.success(tEasyQuestionService.updateById(id, easyQuestionReqVO));
    }
}
