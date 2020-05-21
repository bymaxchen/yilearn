package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.RequestCategoryDto;
import com.zhuiyi.ms.learn.dto.response.ResponseCategoryDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.mapper.CategoryInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author rodbate
 * @Title: CategoryInfoServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1815:52
 */
@Service
public class CategoryInfoServiceImpl  {

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    /**
      * @Description:  保存节点
    　* @author kloazhang
    　* @date 2018/9/18 16:03
    */
    public int saveCategory( RequestCategoryDto requestCategoryDto){

        String dbName = SessionContextHolder.getDbName();
        requestCategoryDto.setCreateTime(new Date());
        requestCategoryDto.setUpdateTime(requestCategoryDto.getCreateTime());
        int existCode = -505;
        Map<String,Object> params = new HashMap<>();
        params.put("dbName", dbName);
        params.put("name", requestCategoryDto.getName());
        int exist = categoryInfoMapper.countCategory(params);
        if (0 < exist){
            return existCode;
        }
        requestCategoryDto.setDbName(dbName);
        return categoryInfoMapper.saveCategory(requestCategoryDto);
    }

    /**
      * @Description:  删除节点
    　* @author kloazhang
    　* @date 2018/9/18 16:04
    */
    public int deleteCategory(RequestCategoryDto requestCategoryDto) {

        requestCategoryDto.setDbName(SessionContextHolder.getDbName());
        return categoryInfoMapper.deleteCategory(requestCategoryDto);
    }

    /**
      * @Description: 更新节点分类
    　* @author kloazhang
    　* @date 2018/9/18 16:04
    */
    public int updateCategory(RequestCategoryDto requestCategoryDto){

        String dbName = SessionContextHolder.getDbName();
        requestCategoryDto.setUpdateTime(new Date());
        Map<String,Object> params = new HashMap<>();
        params.put("existCategoryId", requestCategoryDto.getId());
        params.put("name", requestCategoryDto.getName());
        params.put("dbName", dbName);
        params.put("type", requestCategoryDto.getType());
        int existCode = -505;
        int exist = 0;
        exist = categoryInfoMapper.countCategory(params);
        if( 0 < exist){
            return existCode;
        }
        requestCategoryDto.setDbName(dbName);
        return  categoryInfoMapper.updateCategory(requestCategoryDto);
    }

    /**
      * @Description:  获取所有节点分类--树形结构
    　* @author kloazhang
    　* @date 2018/9/18 16:06
    */
    public ResponseVO getAllCategories(RequestCategoryDto requestCategoryDto) {

        String dbName = SessionContextHolder.getDbName();
        ResponseVO responseVO = new ResponseVO();
        List<ResponseCategoryDto> responseRuleInfoDtos = new ArrayList<>();
        Map<String,Object> params  = new HashMap<>();
        params.put("dbName", dbName);
        params.put("type",requestCategoryDto.getType());
        params.put("id",requestCategoryDto.getId());
        if(null != requestCategoryDto.getPagination() &&
                true == requestCategoryDto.getPagination()){
            int currentPage = 0 ;
            if(requestCategoryDto.getCurrentPage() > 0){
                currentPage = requestCategoryDto.getCurrentPage() - 1;
            }
            int pageSize = requestCategoryDto.getPageSize();
            if(pageSize <= 0 ){
                pageSize = 10;
            }
            params.put("limit",pageSize);
            params.put("offset",currentPage * pageSize);
            int totalNums = categoryInfoMapper.countCategory(params);
            int allPages = (totalNums + pageSize - 1) / pageSize;
            responseVO.setTotal(totalNums);
            responseVO.setAllPages(allPages);
            responseVO.setPageSize(pageSize == 0 ? 1:pageSize);
            responseVO.setCurrentPage(requestCategoryDto.getCurrentPage() ==0? 1:requestCategoryDto.getCurrentPage());
            if(totalNums>0){
                requestCategoryDto.setOffset(currentPage);
                requestCategoryDto.setLimit(pageSize);
                responseRuleInfoDtos=categoryInfoMapper.getAllCategories(params);
            }
            responseVO.setData(responseRuleInfoDtos);
            return responseVO;
        }
        responseRuleInfoDtos=categoryInfoMapper.getAllCategories(params);
        responseVO.setTotal(responseRuleInfoDtos.size());
        responseVO.setData(responseRuleInfoDtos);
        return responseVO;
    }

}
