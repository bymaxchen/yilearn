package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: 返回该业务库下的所有规则
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/1/1616:55
 */
@Data
public class ResponseReviewRulesDto  implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer totalRules;
    private List<ReviewRulesDto> allRules;
}
