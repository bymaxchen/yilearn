package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

import java.util.List;

/**
 * @author rodbate
 * @Title: EducationMetadataVO
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/3011:44
 */
@Data
public class EducationMetadataVO {

    private Integer busId;
    //数据模型
    private List<EducationModuleBO> moduleData;
}
