package com.zhuiyi.ms.learn.controller;

import com.zhuiyi.ms.learn.dto.request.ReqAudioTtsDTO;
import com.zhuiyi.ms.learn.dto.request.ReqDeleteTtsDTO;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.exception.BaseException;
import com.zhuiyi.ms.learn.service.AudioTtsService;
import com.zhuiyi.ms.learn.service.impl.AudioTtsDirector;
import com.zhuiyi.ms.learn.service.impl.TransAudioTtsFromLocalToDfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rodbate
 * @Title: AudioTtsController
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1614:33
 */
@RequestMapping("/learn/tts")
@RestController
public class AudioTtsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioTtsController.class);

    @Autowired
    private AudioTtsDirector audioTtsDirector;

    @Autowired
    private TransAudioTtsFromLocalToDfs transAudioTtsFromLocalToDfs;

    @Autowired
    private AudioTtsService audioTtsService;

    /**
      * @Description: 保存TTS操作
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/29 14:29
    */
    @PostMapping("/saveAudio")
    public BaseResponse  saveAudio(@RequestBody @Validated ReqAudioTtsDTO reqAudioTtsDTO){
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        Map<String,Object> data = new HashMap<>();
        try{
            data.put("logId", audioTtsDirector.saveAudioTts(reqAudioTtsDTO));
        }catch (Exception ex){
            LOGGER.info(ex.toString());
            baseResponse.setCode(MsgCodeEnum.UNKONWN_ERROR.getCode());
            baseResponse.setMsg(ex.toString());
        }
        baseResponse.setData(data);
        return baseResponse;
    }

    @PostMapping("/getAudioByText")
    public BaseResponse getAudioByText(@RequestBody ReqAudioTtsDTO reqAudioTtsDTO) throws Exception{
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        Map<String,Object> data = new HashMap<>();
        data.put("audio", audioTtsDirector.getAudioTtsByText(reqAudioTtsDTO));
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
      * @Description: 批量删除ExamTts记录; 只是删除题目-正在使用tts的情况
     *  不会手动删除磁盘上的tts文件，若需要删除旧文件，需集合表手动删除
    　* @param ${tags}
    　* @return 删除成功后的条数
    　* @throws
    　* @author kloazhang
    　* @date 2019/5/29 11:37
    */
    @PostMapping("/deleteTts")
    public BaseResponse deleteTts(@RequestBody @Valid ReqDeleteTtsDTO reqDeleteTtsDTO){
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        baseResponse.setData(audioTtsService.deleteExamTtsRecord(reqDeleteTtsDTO));
        return baseResponse;
    }


    /**
      * @Description: 同步tts：从本地磁盘同步到Dfs
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2020/1/7 16:00
    */
    @GetMapping("/transTtsToDfs")
    public BaseResponse transTtsToDfs() throws BaseException {
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        try{
            baseResponse.setData(transAudioTtsFromLocalToDfs.transTtsToDfs());
        }catch(Exception ex){
           throw new BaseException(MsgCodeEnum.UNKONWN_ERROR.getCode(),ex.toString());
        }
        return baseResponse;
    }


    /**
     * @Description: 同步tts：从Dfs同步到本地磁盘
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2020/1/7 16:00
     */
    @GetMapping("/transTtsToLocal")
    public BaseResponse transTtsToLocal() throws BaseException {
        BaseResponse baseResponse = new BaseResponse(MsgCodeEnum.SUCCESS);
        try{
            baseResponse.setData(transAudioTtsFromLocalToDfs.transTtsToLocal());
        }catch(Exception ex){
            throw new BaseException(MsgCodeEnum.UNKONWN_ERROR.getCode(),ex.toString());
        }
        return baseResponse;
    }




}
