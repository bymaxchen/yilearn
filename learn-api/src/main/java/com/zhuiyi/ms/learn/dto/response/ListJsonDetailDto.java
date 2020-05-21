package com.zhuiyi.ms.learn.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: ListJsonDetailDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/814:15
 */
@Data
public class ListJsonDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<GhJsonDetailDto> categoryList;
}
