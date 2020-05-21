package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.dto.request.CreateBusinessDTO;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.service.DataBaseInfoServiceImpl;
import com.zhuiyi.ms.learn.service.DataConfigurationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rodbate
 * @Title: DateConfigurationController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/10/2316:21
 */
@RequestMapping("/data")
@RestController
public class DateConfigurationController {

    @Autowired
    private DataConfigurationServiceImpl dataConfigurationService;

    @Autowired
    private DataBaseInfoServiceImpl dataBaseInfoService;

    @GetMapping("/getTables")
    public BaseResponse getTables(){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(dataConfigurationService.getTableInfo());
        return baseResponse;
    }

    @PostMapping("/createBusinessDb")
    public BaseResponse createBusinessDb(@RequestBody CreateBusinessDTO createBusinessDTO){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setMsg(dataBaseInfoService.createBusinessDb(createBusinessDTO));
        return baseResponse;
    }

    @PostMapping("/deleteBusinessDb")
    public BaseResponse deleteBusinessDb(@RequestBody CreateBusinessDTO createBusinessDTO){

        //1.业务库逻辑关系软删除
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(dataBaseInfoService.deleteBusinessDb(createBusinessDTO));
        return baseResponse;
    }

}
