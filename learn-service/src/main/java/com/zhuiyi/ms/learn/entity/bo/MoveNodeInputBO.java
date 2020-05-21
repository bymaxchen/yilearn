package com.zhuiyi.ms.learn.entity.bo;

import lombok.Data;

/**
 *  移动嵌套集模型的sql输入参数对象
 */

@Data
public class MoveNodeInputBO {
    private Integer newPos;

    private Integer width;

    private Integer distance;

    private Integer oldRPos;

    private Integer tmpPos;
}
