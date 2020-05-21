package com.zhuiyi.ms.learn.dto.response;

import com.zhuiyi.ms.learn.dto.transfer.QuestionClassifyDto;
import lombok.Data;

import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-18 15:41
 **/
@Data
public class QuestionClassifyListResponse {

    private List<QuestionClassifyDto> dataList;
}
