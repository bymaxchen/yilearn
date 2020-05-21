package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rodbate
 * @Title: validateController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/12/717:06
 */
@RequestMapping("/learnService")
@RestController
public class validateController {

    @GetMapping("/validate")
    public BaseResponse responseVO (){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setMsg("java is running !!!");
        return baseResponse;
    }

}
