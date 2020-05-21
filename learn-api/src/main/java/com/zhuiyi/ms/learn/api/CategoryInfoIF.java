package com.zhuiyi.ms.learn.api;

import com.zhuiyi.ms.learn.dto.request.RequestCategoryDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author rodbate
 * @Title: CategoryInfoIF
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1911:28
 */
@RequestMapping("/learn/rule/category")
public interface CategoryInfoIF {

    @PostMapping("/saveCategory")
    BaseResponse<ResponseVO> saveCategory(RequestCategoryDto requestCategoryDto) throws  Exception;

    @PostMapping("/updateCategory")
    BaseResponse<ResponseVO> updateCategory(RequestCategoryDto requestCategoryDto);

    @PostMapping("/deleteCategory")
    BaseResponse<ResponseVO> deleteCategory(RequestCategoryDto requestCategoryDto);

    @PostMapping("/getCategory")
    BaseResponse<ResponseVO> getCategory(RequestCategoryDto requestCategoryDto);

}
