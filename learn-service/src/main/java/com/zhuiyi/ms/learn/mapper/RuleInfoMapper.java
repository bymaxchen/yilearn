package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.dto.request.RequestRuleInfoDto;
import com.zhuiyi.ms.learn.dto.request.UseTimesDto;
import com.zhuiyi.ms.learn.dto.response.*;
import com.zhuiyi.ms.learn.entity.RuleInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author rodbate
 * @Title: RuleInfoMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/1817:21
 */
@Mapper
public interface RuleInfoMapper {

    /**
     * @Description: 保存规则
    　* @author kloazhang
    　* @date 2018/9/17 21:16
     */
    int saveRuleInfo(RequestRuleInfoDto requestRuleInfoDto);

//    /**
//     * @Description:  保存规则与语义标签关系
//    　* @author kloazhang
//    　* @date 2018/9/17 21:16
//     */
//    int createRuleAndSemantic(Map<String, Object> map);

    /**
     * @Description:  删除规则
    　* @author kloazhang
    　* @date 2018/9/17 22:55
     */
    int deleteRuleInfo(@Param(value = "id") int ruleId,@Param(value = "dbName") String dbName);

//    /**
//     * @Description:  删除规则与关系映射
//    　* @author kloazhang
//    　* @date 2018/9/17 23:04
//     */
//    int deleteRuleAndSemanticByRuleId(@Param(value = "id") int ruleId,@Param(value = "dbName") String dbName);

    /**
     * @Description:  修改规则
    　* @author kloazhang
    　* @date 2018/9/17 22:55
     */
    int updateRuleInfo(RequestRuleInfoDto requestRuleInfoDto);

    /**
     * @Description:  查询规则
    　* @author kloazhang
    　* @date 2018/9/17 22:56
     */
    List<ResponseRuleInfoDto> getRules(Map<String,Object> params);

    /**
      * @Description: 返回规则分页查询总数
    　* @author kloazhang
    　* @date 2018/9/19 20:10
    */
    int countRules(Map<String,Object> params);


    /**
      * @Description:  返回创建规则名称相同的规则数
    　* @author kloazhang
    　* @date 2018/9/27 23:20
    */
    Integer countExistRuleName(Map<String,Object> params);

    /**
      * @Description:  返回语义标签所绑定的规则数
    　* @author kloazhang
    　* @date 2018/10/7 11:09
    */
    List<InvolvedRulesDto> getInvolvedRulesBySemanticId(Map<String,Object> params);

    /**
      * @Description: 获取该业务库下的所有规则
    　* @param ${tags}
    　* @return 该业务库下的所有规则
    　* @throws
    　* @author kloazhang
    　* @date 2019/1/16 16:35
    */
    List<RuleInfoEntity> getReviewRules(Map<String,Object> params);

}
