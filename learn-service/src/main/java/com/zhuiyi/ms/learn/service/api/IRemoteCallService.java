package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.dto.request.RequestTaskLogsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author rodbate
 * @Title: IRemoteCallService
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/2/2620:42
 */
@Service
public interface IRemoteCallService {

    ResponseEntity remoteCall(RequestTaskLogsDto requestTaskLogsDto);
}
