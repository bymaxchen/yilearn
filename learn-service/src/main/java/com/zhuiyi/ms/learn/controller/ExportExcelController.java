package com.zhuiyi.ms.learn.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhuiyi.ms.learn.api.ExportExcelIF;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.service.download.ExcelDownload;
import com.zhuiyi.ms.learn.service.export.ExportExcelServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author rodbate
 * @Title: ExportExcelController
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:57
 */
@RestController
public class ExportExcelController implements ExportExcelIF {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskInfoController.class);

    @Autowired
    private ExportExcelServiceImpl exportExcelService;

    @Autowired
    private ExcelDownload excelDownload;


    @Override
    public void exportServiceStatistic(@RequestBody RequestServiceScoreDto requestServiceScoreDto, HttpServletResponse response) {
        LOGGER.info("Export excel request params", requestServiceScoreDto.toString());
        try{
            exportExcelService.serviceStaticticExportExcel(requestServiceScoreDto,response);
        }catch (Exception ex){
            LOGGER.info(ex.toString());
        }
    }

    @Override
    public void exportServiceStatistic1(RequestServiceScoreDto requestServiceScoreDto, HttpServletResponse response) {
        LOGGER.info("Export excel request params", requestServiceScoreDto.toString());
        requestServiceScoreDto.setTaskType(1);
        requestServiceScoreDto.setStartTime("2020-05-01 00:00:00");
        requestServiceScoreDto.setEndTime("2020-05-07 23:59:59");

        try{
            exportExcelService.serviceStaticticExportExcel(requestServiceScoreDto,response);
        }catch (Exception ex){
            LOGGER.info(ex.toString());
        }
    }
}
