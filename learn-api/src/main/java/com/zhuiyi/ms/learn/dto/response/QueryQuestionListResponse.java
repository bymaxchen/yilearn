package com.zhuiyi.ms.learn.dto.response;

import com.zhuiyi.ms.learn.dto.transfer.QuestionInfoDto;
import lombok.Data;

import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-19 18:02
 **/
@Data
public class QueryQuestionListResponse {

    private Long count;

    private List<QuestionInfoDto> dataList;
}
