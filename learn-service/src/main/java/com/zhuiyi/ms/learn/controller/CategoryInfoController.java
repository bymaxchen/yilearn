package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.api.CategoryInfoIF;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.RequestCategoryDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseCategoryDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.service.CategoryInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rodbate
 * @Title: CategoryInfoController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1816:06
 */
@RestController
public class CategoryInfoController implements CategoryInfoIF {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryInfoController.class);

    private static final Integer EXIST_CODE = -505;

    @Autowired
    private CategoryInfoServiceImpl categoryInfoService;

    @Override
    public BaseResponse<ResponseVO> saveCategory(@RequestBody RequestCategoryDto requestCategoryDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        int result = categoryInfoService.saveCategory(requestCategoryDto);
        baseResponse.setData(requestCategoryDto);
        if(EXIST_CODE == result){
            baseResponse.setCode(EXIST_CODE);
            baseResponse.setMsg("cateGory Name Exist !!!");
        }
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> updateCategory(@RequestBody RequestCategoryDto requestCategoryDto){

        BaseResponse baseResponse;
        int result = categoryInfoService.updateCategory(requestCategoryDto);
        baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(requestCategoryDto);
        if(EXIST_CODE == result){
            baseResponse.setCode(EXIST_CODE);
            baseResponse.setMsg("cateGory Name Exist !!!");
        }
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> deleteCategory(@RequestBody RequestCategoryDto requestCategoryDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(categoryInfoService.deleteCategory(requestCategoryDto));
        return baseResponse;
    }

    @Override
    public BaseResponse<ResponseVO> getCategory(@RequestBody RequestCategoryDto requestCategoryDto){

        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        ResponseVO responseVO = categoryInfoService.getAllCategories(requestCategoryDto);
        baseResponse.setData(responseVO);
        return baseResponse;
    }
}
