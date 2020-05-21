package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestSemanticToRuleDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2619:14
 */
@Data
public class RequestSemanticToRuleDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int semanticId;
    private List<Long> semanticIds;
}
