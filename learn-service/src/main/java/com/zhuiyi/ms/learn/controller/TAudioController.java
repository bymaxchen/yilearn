package com.zhuiyi.ms.learn.controller;


import com.zhuiyi.ms.learn.cache.AudioBytesCache;
import com.zhuiyi.ms.learn.entity.vo.req.AudioReqVO;
import com.zhuiyi.ms.learn.service.impl.TAudioServiceImpl;
import com.zhuiyi.ms.learn.util.ResponseBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bymax
 * @since 2020-02-13
 */
@RestController
public class TAudioController {
    @Resource
    private TAudioServiceImpl tAudioService;

    @Autowired
    private AudioBytesCache audioBytesCache;

    @GetMapping("/learn/api/v1/audio/status")
    public ResponseEntity getStatus(@RequestParam("sessionId") String sessionId) {
        return ResponseBuilder.success(tAudioService.getStatus(sessionId));
    }

    @PostMapping("/learn/api/v1/audio/save")
    public ResponseEntity saveAudio(@RequestBody AudioReqVO audioReqVO) {
        return ResponseBuilder.success(tAudioService.saveAudio(audioReqVO));
    }

    /**
     * 为了兼容safari的range请求，参考:https://blog.csdn.net/u010120886/article/details/79007001
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = {"/learn-java/learn/api/v1/audio/download", "/learn/api/v1/audio/download"}, method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getParameter("sessionId");
        String range = request.getHeader("Range");
        byte [] bytes = tAudioService.downloadByte(sessionId, range);
        long contentLength = bytes.length;

        int start = 0, end = 0;
        if(range != null && range.startsWith("bytes=")){
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            if(values.length > 1){
                end = Integer.parseInt(values[1]);
            }
        }
        int requestSize = 0;
        if(end != 0 && end > start){
            requestSize = end - start + 1;
        } else {
            requestSize = Integer.MAX_VALUE;
        }

        final int bufferSize = 4096;

        byte[] buffer = new byte[bufferSize];
        response.setContentType("audio/mpeg");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", sessionId);
        response.setHeader("Last-Modified", new Date().toString());
        //第一次请求只返回content length来让客户端请求多次实际数据
        if(range == null){
            response.setHeader("Content-length", contentLength + "");
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }else{
            //以后的多次以断点续传的方式来返回视频数据
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
            long requestStart = 0, requestEnd = 0;
            String[] ranges = range.split("=");
            if(ranges.length > 1){
                String[] rangeDatas = ranges[1].split("-");
                requestStart = Integer.parseInt(rangeDatas[0]);
                if(rangeDatas.length > 1){
                    requestEnd = Integer.parseInt(rangeDatas[1]);
                }
            }
            long length = 0;
            if(requestEnd > 0){
                length = requestEnd - requestStart + 1;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
            }else{
                length = contentLength - requestStart;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes "+ requestStart + "-" + (contentLength - 1) + "/" + contentLength);
            }
        }

        ServletOutputStream out = response.getOutputStream();
        int needSize = requestSize;


        if (end != 0) {
            bytes = ArrayUtils.subarray(bytes, start, end + 1);
        }



        int i = 0;
        while(needSize > 0){
            int beginIndex = i * bufferSize;
            int endIndex = (i + 1) * bufferSize;
            i++;

            if (endIndex > bytes.length) {
                endIndex = bytes.length;
            }
            buffer = ArrayUtils.subarray(bytes, beginIndex, endIndex);

            int len = endIndex - beginIndex;

            if(needSize < buffer.length){
                out.write(buffer, 0, needSize);
            } else {
                out.write(buffer, 0, len);
                if(len < buffer.length){
                    break;
                }
            }
            needSize -= buffer.length;
        }

        audioBytesCache.emptyBytesOfDfs(sessionId);

        out.close();
    }
}
