package com.zhuiyi.ms.learn.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuiyi.ms.learn.entity.TChallengeExam;
import com.zhuiyi.ms.learn.entity.vo.req.ChallengeExamReqVO;
import com.zhuiyi.ms.learn.service.impl.TChallengeExamServiceImpl;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bymax
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/learn/api/v1/challengeExam")
public class TChallengeExamController {
    @Resource
    private TChallengeExamServiceImpl tChallengeExamService;


    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody ChallengeExamReqVO challengeExamReqVO) {
        return ResponseBuilder.success(tChallengeExamService.findAll(challengeExamReqVO));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody ChallengeExamReqVO challengeExamReqVO) {
        return ResponseBuilder.success(tChallengeExamService.create(challengeExamReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tChallengeExamService.removeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody ChallengeExamReqVO challengeExamReqVO) {
        TChallengeExam tChallengeExam = new TChallengeExam();
        BeanUtils.copyProperties(challengeExamReqVO, tChallengeExam);
        tChallengeExam.setId(id);
        return ResponseBuilder.success(tChallengeExamService.updateById(tChallengeExam));
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tChallengeExamService.findOne(id));
    }

}
