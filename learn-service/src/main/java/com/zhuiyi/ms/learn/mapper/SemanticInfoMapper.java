package com.zhuiyi.ms.learn.mapper;

import com.sun.tracing.dtrace.ModuleAttributes;
import com.zhuiyi.ms.learn.dto.request.RequestSemanticDto;
import com.zhuiyi.ms.learn.dto.response.ResponseSemanticDto;
import com.zhuiyi.ms.learn.dto.response.SemanticToRulesDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author rodbate
 * @Title: SemanticInfoMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1816:56
 */
@Mapper
public interface SemanticInfoMapper {
    /**
     * @Description: 板保存标签
    　* @author kloazhang
    　* @date 2018/9/17 17:09
     */
    int createSemantic(RequestSemanticDto requestSemanticDto);

    /**
     * @Description:  删除标签
    　* @author kloazhang
    　* @date 2018/9/17 17:55
     */
    int deleteSemantic(@Param(value = "id") int semanticId);
    /**
     * @Description: 修改标签
    　* @author kloazhang
    　* @date 2018/9/17 17:55
     */
    int updateSemantic(RequestSemanticDto requestSemanticDto);

    /**
     * @Description:  查询标签
    　* @author kloazhang
    　* @date 2018/9/17 17:55
     */
    List<ResponseSemanticDto> getSemantic(Map<String, Object> params);

    /**
      * @Description:  返回分页查询语义标签总数
    　* @author kloazhang
    　* @date 2018/9/19 20:37
    */
    int countSemantic(Map<String, Object> params);

    /**
      * @Description: 根据标签Id获取相应规则
    　* @author kloazhang
    　* @date 2018/9/26 19:10
    */
    List<SemanticToRulesDto> getRulesBySemanticId(Map<String,Object> params);
}
