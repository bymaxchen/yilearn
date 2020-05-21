package com.zhuiyi.ms.learn.service.api;

import com.zhuiyi.ms.learn.entity.TChallengeMission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuiyi.ms.learn.entity.bo.ResponsePage;
import com.zhuiyi.ms.learn.entity.vo.req.ChallengeMissionReqVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bymax
 * @since 2019-12-31
 */
public interface ITChallengeMissionService extends IService<TChallengeMission> {
    ResponsePage findAll(ChallengeMissionReqVO challengeMissionReqVO);

    TChallengeMission create(ChallengeMissionReqVO challengeMissionReqVO);

    Boolean delete(Integer id);

    Boolean update(Integer id, ChallengeMissionReqVO challengeMissionReqVO);
}
