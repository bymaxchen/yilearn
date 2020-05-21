package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.ExamTrainingReqVO;
import com.zhuiyi.ms.learn.service.impl.TExamTrainingServiceImpl;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bymax
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/learn/api/v1/examTraining")
public class TExamTrainingController {
    @Resource
    private TExamTrainingServiceImpl tExamTrainingService;

    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody ExamTrainingReqVO examTrainingReqVO) {
        return ResponseBuilder.success(tExamTrainingService.findAll(examTrainingReqVO));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody ExamTrainingReqVO examTrainingReqVO) {
        return ResponseBuilder.success(tExamTrainingService.create(examTrainingReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tExamTrainingService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody ExamTrainingReqVO examTrainingReqVO) {
        return ResponseBuilder.success(tExamTrainingService.update(id, examTrainingReqVO));
    }
}
