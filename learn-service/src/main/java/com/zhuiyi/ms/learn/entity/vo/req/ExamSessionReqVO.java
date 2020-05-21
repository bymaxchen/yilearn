package com.zhuiyi.ms.learn.entity.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TExamSession;
import lombok.Data;

@Data
public class ExamSessionReqVO  extends TExamSession {
    private Page page;
}
