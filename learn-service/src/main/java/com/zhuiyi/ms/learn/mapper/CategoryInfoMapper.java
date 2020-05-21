package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.dto.request.RequestCategoryDto;
import com.zhuiyi.ms.learn.dto.response.ResponseCategoryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author rodbate
 * @Title: CategoryInfoMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:46
 */
@Mapper
public interface CategoryInfoMapper {


    /**
     * @Description:  保存节点
    　* @author kloazhang
    　* @date 2018/9/17 16:05
     */
    int saveCategory(RequestCategoryDto requestCategoryDto);

    /**
     * @Description:  删除节点
    　* @author kloazhang
    　* @date 2018/9/17 16:06
     */
    int deleteCategory(RequestCategoryDto requestCategoryDto);

    /**
     * @Description:  修改节点
    　* @author kloazhang
    　* @date 2018/9/17 16:06
     */
    int updateCategory(RequestCategoryDto requestCategoryDto);

    /**
     * @Description:  查询节点
    　* @author kloazhang
    　* @date 2018/9/17 16:07
     */
    List<ResponseCategoryDto> getAllCategories(Map<String,Object> params);

    /**
      * @Description:  获取分页的总数
    　* @author kloazhang
    　* @date 2018/9/19 21:08
    */
    int countCategory(Map<String,Object> params);
}
