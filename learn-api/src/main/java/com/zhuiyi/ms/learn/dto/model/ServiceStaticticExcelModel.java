package com.zhuiyi.ms.learn.dto.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: ServiceStaticticExcelModel
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:11
 */
@Data
public class ServiceStaticticExcelModel extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "学员工号" ,index = 0)
    private String serviceId;

    @ExcelProperty(value = "学员名称" ,index = 1)
    private String serviceName;

    @ExcelProperty(value = "练习次数" ,index =2)
    private Integer trainTimes;

    @ExcelProperty(value = "流程扣分次数" ,index =3)
    private int processViolationTimes;

    @ExcelProperty(value = "全局扣分次数" ,index =4)
    private int globalViolationTimes;

    @ExcelProperty(value = "练习平均分数" ,index =5)
    private Float averageScore;
}
