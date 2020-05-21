package com.zhuiyi.ms.learn.entity.vo.req;

import lombok.Data;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Data
public class PaperListReqVO {
    Page page;

    private Integer level;

    private String searchText;

    private Integer type;

    private Integer paperCategoryId;
}
