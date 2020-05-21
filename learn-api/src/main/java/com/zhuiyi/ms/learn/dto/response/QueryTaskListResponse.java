package com.zhuiyi.ms.learn.dto.response;

import com.zhuiyi.ms.learn.dto.transfer.TrainTaskDto;
import lombok.Data;

import java.util.List;

/**
 * 此类的描述是：
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-09-21 16:12
 **/
@Data
public class QueryTaskListResponse {

    private Long count;

    private List<TrainTaskDto> dataList;
}
