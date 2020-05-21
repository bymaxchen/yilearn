package com.zhuiyi.ms.learn.async;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.zhuiyi.ms.learn.entity.bo.ExamBO;
import com.zhuiyi.ms.learn.entity.vo.res.ExamResultResVO;
import com.zhuiyi.ms.learn.service.impl.TExamServiceImpl;
import com.zhuiyi.ms.learn.service.impl.TExamSessionServiceImpl;
import com.zhuiyi.ms.learn.util.HttpContextUtils;
import com.zhuiyi.ms.learn.util.HttpUtil;
import com.zhuiyi.ms.learn.util.KeyUtil;
import com.zhuiyi.ms.learn.util.LoggerUtil;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@Component
public class YouBangExamPushAsync {
    @Value("${examResultPushHost}")
    private String examResultPushHost;

    @Value("${jwtPublicKey}")
    private String jwtPublicKey;

    @Value("${jwtPrivateKey}")
    private String jwtPrivateKey;

    @Autowired
    private TExamServiceImpl examService;

    @Async("taskExecutor")
    public void pushResult(ExamBO examBO, String dbName) {
        HttpContextUtils.setDs(dbName);

        ExamResultResVO examResultResVO = examService.findOne(examBO.getExamId());

        ExamBO overview = examResultResVO.getOverview();
        overview.setActualScore(examBO.getActualScore());
        overview.setIsPass(overview.getActualScore() - overview.getPassScore() >= 0);

        try {
            RSAPrivateKey privateKey = KeyUtil.getPrivateKeyFromString(jwtPrivateKey);

            RSAPublicKey publicKey = KeyUtil.getPublicKeyFromString(jwtPublicKey);
            Algorithm algorithmRS = Algorithm.RSA256(publicKey, privateKey);
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "RS256");
            header.put("typ", "JWT");

            String Authorization = JWT.create().withHeader(header).withClaim("identity_code", examBO.getIdentityCode()).withClaim("name", examBO.getName())
                    .sign(algorithmRS);

            Headers.Builder result = new Headers.Builder();
            result.add("Authorization", Authorization);
            result.add("Accept", "application/vnd.edusoho.v2+json");

            long startTime = System.currentTimeMillis();
            HttpUtil.httpPost(examResultPushHost + "/api/roleplay_result", JSON.toJSON(examResultResVO), result.build());
            long endTime = System.currentTimeMillis();    //获取结束时间
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间

        } catch (Exception exception){
            LoggerUtil.error("failed to push result cause", exception);
        }

    }
}
