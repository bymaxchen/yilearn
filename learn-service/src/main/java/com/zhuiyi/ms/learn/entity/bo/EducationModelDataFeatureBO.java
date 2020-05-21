package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

/**
 * @author rodbate
 * @Title: EducationModelDataFeature
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/12/3011:39
 */
@Data
public class EducationModelDataFeatureBO {

    /**
     * 可不传
     */
    private Integer id;
    /**
     * 会话流水内容
     */
    private String name;
    /**
     * 标签Id;可不传
     */
    private Integer tagId;
    /**
     * 0表示未训练，1表示已训练
     */
    private Integer trained;
    /**
     * 教育类型。10代表常规教育
     */
    private Integer teachType;
    /**
     * 数据操作结果。10添加到标签；11重新添加到标签；12缺失语义；13拒识数据；14忽略；20从标签中删除；31修改标注数据内容
     */
    private Integer handleResult;
    /**
     *  来源，10代表Excel带入，20代表后台导入
     */
    private Integer sourceType;
    /**
     * 会话流水的发生时间
     */
    private String createdTime;
    /**
     * 创建人，若无可填-1
     */
    private String createdBy;
    private String updatedTime;
    /**
     * 创建人，若无可填-1
     */
    private String updatedBy;

}
