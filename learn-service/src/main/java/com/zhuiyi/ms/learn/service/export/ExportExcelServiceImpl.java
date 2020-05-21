package com.zhuiyi.ms.learn.service.export;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zhuiyi.ms.learn.common.DateUtil;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.model.ServiceStaticticExcelModel;
import com.zhuiyi.ms.learn.dto.request.RequestServiceScoreDto;
import com.zhuiyi.ms.learn.dto.response.ResponseServiceScoreDto;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.dto.response.TotalTaskInfoDto;
import com.zhuiyi.ms.learn.entity.bo.ViolationTimesBO;
import com.zhuiyi.ms.learn.mapper.TaskLogsInfoMapper;
import com.zhuiyi.ms.learn.service.TaskLogInfoServiceImpl;
import com.zhuiyi.ms.learn.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rodbate
 * @Title: ExportExcelServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/11/2111:36
 */
@Service
public class ExportExcelServiceImpl {

    @Value("${excel.downLoadExcelUrl}")
    private String downLoadExcelUrl;

    @Autowired
    private TaskLogsInfoMapper taskLogsInfoMapper;

    public void serviceStaticticExportExcel(RequestServiceScoreDto requestServiceScoreDto,HttpServletResponse response)
            throws Exception{

        List<ResponseServiceScoreDto> responseServiceScoreDtos = getExportServiceScore(requestServiceScoreDto);
        List<ServiceStaticticExcelModel>  serviceStaticticExcelModels = new ArrayList<>();
        if(null != responseServiceScoreDtos && responseServiceScoreDtos.size() > 0){
            for(ResponseServiceScoreDto responseServiceScoreDto : responseServiceScoreDtos){
                ServiceStaticticExcelModel serviceStaticticExcelModel = new ServiceStaticticExcelModel();
                serviceStaticticExcelModel.setServiceId(responseServiceScoreDto.getServiceId());
                serviceStaticticExcelModel.setServiceName(responseServiceScoreDto.getServiceName());
                serviceStaticticExcelModel.setGlobalViolationTimes(responseServiceScoreDto.getGlobalViolationTimes());
                serviceStaticticExcelModel.setProcessViolationTimes(responseServiceScoreDto.getProcessViolationTimes());
                serviceStaticticExcelModel.setAverageScore(responseServiceScoreDto.getAverageScore());
                serviceStaticticExcelModel.setTrainTimes(responseServiceScoreDto.getTrainTimes());
                serviceStaticticExcelModels.add(serviceStaticticExcelModel);
            }
        }
        try {
            ServletOutputStream out = response.getOutputStream();


            response.setContentType("application/force-download");

            String fileName = FileUtil.getFileName("serviceScoreStatistic");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" +
                    URLEncoder.encode(fileName, "UTF-8"));
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);


            Sheet sheet1 = new Sheet(1, 0,ServiceStaticticExcelModel.class);
            sheet1.setSheetName("导出数据");
            writer.write(serviceStaticticExcelModels, sheet1);

            writer.finish();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ResponseServiceScoreDto> getExportServiceScore(RequestServiceScoreDto requestServiceScoreDto){



        String dbName = SessionContextHolder.getDbName();
        ResponseVO responseVO = new ResponseVO();
        List<ResponseServiceScoreDto> responseServiceScoreDtos = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        int currentPage = 0;
        if (requestServiceScoreDto.getCurrentPage() > 0) {
            currentPage = requestServiceScoreDto.getCurrentPage() - 1;
        }
        int pageSize = requestServiceScoreDto.getPageSize();
        if (pageSize <= 0) {
            pageSize = 10;
        }
        requestServiceScoreDto.setLimit(pageSize);
        requestServiceScoreDto.setOffset(currentPage * pageSize);
        requestServiceScoreDto.setTaskType(requestServiceScoreDto.getTaskType() == 0 ?
                1 : requestServiceScoreDto.getTaskType());
        requestServiceScoreDto.setDbName(dbName);
        Integer totalNums = taskLogsInfoMapper.getTotalServiceScore(requestServiceScoreDto);
        if(null == totalNums){
            totalNums = 0;
        }
        int allPages = (totalNums + pageSize - 1) / pageSize;
        responseVO.setTotal(totalNums);
        responseVO.setAllPages(allPages);
        responseVO.setPageSize(pageSize == 0 ? 1 : pageSize);
        responseVO.setCurrentPage(requestServiceScoreDto.getCurrentPage() == 0 ? 1 : requestServiceScoreDto.getCurrentPage());
        if (totalNums > 0) {
            //1.获取统计元数据
            responseServiceScoreDtos = taskLogsInfoMapper.
                    getServiceTotalScoreDto(requestServiceScoreDto);
            //2.遍历不同客服名称，得到其下的taskId
            if (null != responseServiceScoreDtos && responseServiceScoreDtos.size() > 0) {

                requestServiceScoreDto.setServiceIds(responseServiceScoreDtos.stream().map(item -> item.getServiceId()).collect(Collectors.toList()));
                List<TotalTaskInfoDto> totalTaskInfoDtos = taskLogsInfoMapper.getTaskIdAndService(requestServiceScoreDto);
                Map<String, List<Integer>> serviceId2IdList = new HashMap<>(500);
                for(TotalTaskInfoDto taskInfoDto: totalTaskInfoDtos) {
                    List<Integer> idList = serviceId2IdList.get(taskInfoDto.getServiceId());
                    if (idList == null) {
                        serviceId2IdList.put(taskInfoDto.getServiceId(), new ArrayList<>(Arrays.asList(taskInfoDto.getId())));
                    } else {
                        idList.add(taskInfoDto.getId());
                    }

                }
                List<Integer> taskIdList = totalTaskInfoDtos.stream().map(item -> item.getId()).collect(Collectors.toList());
                if (!taskIdList.isEmpty()) {
                    Map<Integer, Integer> id2processViolationTimes = new HashMap<>(200);
                    List<ViolationTimesBO> processViolationTimesList = taskLogsInfoMapper.getViolationTimesByTaskIdList(1, taskIdList, dbName);
                    for(ViolationTimesBO violationTimesBO: processViolationTimesList) {
                        id2processViolationTimes.put(violationTimesBO.getId(), violationTimesBO.getViolationTimes());
                    }

                    Map<Integer, Integer> id2globalViolationTimes = new HashMap<>(200);
                    List<ViolationTimesBO> globalViolationTimesList = taskLogsInfoMapper.getViolationTimesByTaskIdList(2, taskIdList, dbName);
                    for(ViolationTimesBO violationTimesBO: globalViolationTimesList) {
                        id2globalViolationTimes.put(violationTimesBO.getId(), violationTimesBO.getViolationTimes());
                    }

                    for(ResponseServiceScoreDto responseServiceScoreDto: responseServiceScoreDtos) {
                        int processViolationTimes = 0;
                        int globalViolationTimes = 0;
                        for(Integer id: serviceId2IdList.get(responseServiceScoreDto.getServiceId())) {
                            processViolationTimes += id2processViolationTimes.getOrDefault(id, 0);
                            globalViolationTimes += id2globalViolationTimes.getOrDefault(id, 0);
                        }
                        responseServiceScoreDto.setProcessViolationTimes(processViolationTimes);
                        responseServiceScoreDto.setGlobalViolationTimes(globalViolationTimes);
                    }

                }
            }
        }

        if (requestServiceScoreDto.getMaxScore() == null) {
            requestServiceScoreDto.setMaxScore(1000f);
        }

        if (requestServiceScoreDto.getMinScore() == null) {
            requestServiceScoreDto.setMinScore(-1000f);
        }
        return responseServiceScoreDtos.stream().filter(item -> item.getAverageScore() < requestServiceScoreDto.getMaxScore()
                && item.getAverageScore() > requestServiceScoreDto.getMinScore()).collect(Collectors.toList());
    }



    public String createTempExcel (){

        StringBuffer excelPath = new StringBuffer(downLoadExcelUrl);
        excelPath.append("/").append(DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                "yyyyMMdd"));
        String prefix = DateUtil.TimestampToString(new Timestamp(System.currentTimeMillis()),
                "yyyyMMddHHmmss");
        String suffix = ".xlsx";
        return FileUtil.createTempFile(prefix, suffix, excelPath.toString());
    }

}

