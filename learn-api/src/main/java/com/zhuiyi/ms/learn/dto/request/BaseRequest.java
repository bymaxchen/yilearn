/**   
 *   
 * 文件名：BaseRequest.java   
 * 版本信息：   
 * 日期：2018年5月30日 上午11:54:29  
 * Copyright @Zhuiyi Inc 2018    
 * 版权所有   
 *   
 */
package com.zhuiyi.ms.learn.dto.request;
import lombok.Data;

/**
 *
 *
 * @author crazyhu@wezhuiyi.com
 * @createTime 2018-09-18 11:59:53
 */
@Data
public class BaseRequest {

    /**
     * 页码：第一页传1
     */
    private Integer pageNo;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 排序方式
     */
    private Integer sortType;
}
