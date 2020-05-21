package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TChatLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.vo.req.ChatLogListReqVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
public interface ITChatLogService extends IService<TChatLog> {
    List<TChatLog> findAll(ChatLogListReqVO chatLogListReqVO);
}
