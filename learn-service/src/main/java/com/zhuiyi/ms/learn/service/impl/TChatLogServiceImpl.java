package com.zhuiyi.ms.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuiyi.ms.learn.entity.TChatLog;
import com.zhuiyi.ms.learn.entity.vo.req.ChatLogListReqVO;
import com.zhuiyi.ms.learn.mapper.TChatLogMapper;
import com.zhuiyi.ms.learn.service.api.ITChatLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@DS("#session.dataSourceName")
@Service
public class TChatLogServiceImpl extends ServiceImpl<TChatLogMapper, TChatLog> implements ITChatLogService {
    @Override
    public List<TChatLog> findAll(ChatLogListReqVO chatLogListReqVO) {
        Page page = new Page();
        page.setSize(Optional.ofNullable(chatLogListReqVO.getPageSize()).orElse(10));
        page.setCurrent(Optional.ofNullable(chatLogListReqVO.getPageNo()).orElse(1));
        IPage<TChatLog> iPage = this.page(page);
        return iPage.getRecords();
    }
}
