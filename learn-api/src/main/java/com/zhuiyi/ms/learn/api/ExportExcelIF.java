package com.zhuiyi.ms.learn.api;

import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author rodbate
 * @Title: ExportExcelIF
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:19
 */
@RequestMapping("/learn/download")
public interface ExportExcelIF {

    @PostMapping(value = "/exportServiceStatistic")
    void exportServiceStatistic(RequestServiceScoreDto requestServiceScoreDto, HttpServletResponse response);

    @GetMapping(value = "/exportServiceStatistic")
    void exportServiceStatistic1(RequestServiceScoreDto requestServiceScoreDto, HttpServletResponse response);
}
