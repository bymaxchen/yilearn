package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.entity.vo.req.ChallengeMissionReqVO;
import com.zhuiyi.ms.learn.service.impl.TChallengeExamServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TChallengeMissionServiceImpl;
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
 * @since 2019-12-31
 */
@RestController
@RequestMapping("/learn/api/v1/challengeMission")
public class TChallengeMissionController {
    @Resource
    private TChallengeMissionServiceImpl tChallengeMissionService;

    @PostMapping("/list")
    public ResponseEntity findAll(@RequestBody ChallengeMissionReqVO challengeMissionReqVO) {
        return ResponseBuilder.success(tChallengeMissionService.findAll(challengeMissionReqVO));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody ChallengeMissionReqVO challengeExamReqVO) {
        return ResponseBuilder.success(tChallengeMissionService.create(challengeExamReqVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        return ResponseBuilder.success(tChallengeMissionService.delete(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody ChallengeMissionReqVO challengeExamReqVO) {
        return ResponseBuilder.success(tChallengeMissionService.update(id, challengeExamReqVO));
    }
}
