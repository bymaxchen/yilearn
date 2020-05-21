package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticDto;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticToRuleDto;
import com.zhuiyi.ms.learn.dto.response.ResponseSemanticDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.dto.response.SemanticToRulesDto;
import com.zhuiyi.ms.learn.mapper.SemanticInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author rodbate
 * @Title: SemanticInfoServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1816:59
 */
@Service
public class SemanticInfoServiceImpl {

    @Autowired
    private SemanticInfoMapper semanticInfoMapper;

    /**
      * @Description:  创建语义标签
    　* @author kloazhang
    　* @date 2018/9/18 17:00
    */
    public int createSemantic(RequestSemanticDto requestSemanticDto) throws  Exception{
        Date createTime = DateUtil.timestamp2Date(requestSemanticDto.getCreateTimestamp(), "yyyy-MM-dd HH:mm:ss");
        requestSemanticDto.setCreateTime(createTime);
        return semanticInfoMapper.createSemantic(requestSemanticDto);
    }

    /**
      * @Description:  删除语义标签
    　* @author kloazhang
    　* @date 2018/9/18 17:01
    */
    public int deleteSemantic(int id) {
        return semanticInfoMapper.deleteSemantic(id);
    }

    /**
      * @Description: 更新语义标签
    　* @author kloazhang
    　* @date 2018/9/18 17:01
    */
    public int updateSemantic(RequestSemanticDto requestSemanticDto) throws Exception {
        Date updateTime = DateUtil.timestamp2Date(requestSemanticDto.getUpdateTimestamp(),"yyyy-MM-dd HH:mm:ss");
        requestSemanticDto.setUpdateTime(updateTime);
        return semanticInfoMapper.updateSemantic(requestSemanticDto);
    }


    /**
      * @Description:  查询标签
    　* @author kloazhang
    　* @date 2018/9/18 17:02
    */
    public ResponseVO getSemantic(RequestSemanticDto requestSemanticDto) {
        Map<String,Object> params = new HashMap<>();
        ResponseVO responseVO = new ResponseVO();
        params.put("id", requestSemanticDto.getId());
        List<ResponseSemanticDto> responseSemanticDtos = new ArrayList<>();
        if(null != requestSemanticDto.getPagination() &&
                true == requestSemanticDto.getPagination()){
            //分页
            int currentPage = 0 ;
            if(requestSemanticDto.getCurrentPage() > 0){
                currentPage = requestSemanticDto.getCurrentPage() - 1;
            }
            int pageSize = requestSemanticDto.getPageSize();
            if(pageSize <= 0 ){
                pageSize = 10;
            }
            params.put("limit",pageSize);
            params.put("offset",currentPage * pageSize);
            int totalNums = semanticInfoMapper.countSemantic(params);
            int allPages = (totalNums + pageSize - 1) / pageSize;
            responseVO.setTotal(totalNums );
            responseVO.setAllPages(allPages);
            responseVO.setPageSize(pageSize == 0? 1:pageSize);
            responseVO.setCurrentPage(requestSemanticDto.getCurrentPage()==0?1:requestSemanticDto.getCurrentPage());
            if(totalNums >0){
                responseSemanticDtos = semanticInfoMapper.getSemantic(params);
            }
            responseVO.setData(responseSemanticDtos);
            return responseVO;
        }
        responseSemanticDtos = semanticInfoMapper.getSemantic(params);
        responseVO.setData(responseSemanticDtos);
        responseVO.setTotal(responseSemanticDtos.size());
        return responseVO;
    }

    public ResponseVO getRulesBySemanticId(RequestSemanticToRuleDto requestSemanticToRuleDto){

        ResponseVO responseVO = new ResponseVO();
        Map<String,Object> params = new HashMap<>();
        params.put("semanticIds",requestSemanticToRuleDto.getSemanticIds());
        List<SemanticToRulesDto> semanticToRulesDtos = semanticInfoMapper.getRulesBySemanticId(params);
        responseVO.setData(semanticToRulesDtos);
        return  responseVO;
    }

}
