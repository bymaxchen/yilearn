package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author rodbate
 * @Title: RequestServiceScoreDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/9/2114:54
 */
@Data
public class RequestServiceScoreDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private int taskType;
    private String startTime;
    private String endTime;
    private List<String> serviceIds;

    private Float minScore;

    private Float maxScore;

    private String query;

    private String serviceName;
    private int currentPage;
    private int pageSize;
    private int orderBy;
    private int offset;
    private int limit;
    private String dbName;

    @Override
    public String toString() {
        return "RequestServiceScoreDto{" +
                "taskType=" + taskType +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", serviceIds=" + serviceIds +
                ", minScore=" + minScore +
                ", maxScore=" + maxScore +
                ", query='" + query + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", orderBy=" + orderBy +
                ", offset=" + offset +
                ", limit=" + limit +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
