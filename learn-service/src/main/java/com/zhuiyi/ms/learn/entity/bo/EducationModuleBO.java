package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

import java.util.List;

/**
 * @author rodbate
 * @Title: EducationModuleBO
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/3011:43
 */
@Data
public class EducationModuleBO {

    /**
     *  客户 | 坐席 模型bId
     */
    private Long bId;
    /**
     *  会话流水内容实体
     */
    private List<EducationModelDataFeatureBO> dataFeature;
}
