package com.zhuiyi.ms.learn.entity.vo.req;

import lombok.Data;

@Data
public class PaperCategoryReqVO {
    private String paperCategoryName;

    private Integer paperCategoryId;

    private Integer parentId;
}
