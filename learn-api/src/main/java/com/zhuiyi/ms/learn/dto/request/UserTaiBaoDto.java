package com.zhuiyi.ms.learn.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author rodbate
 * @Title: UserTaiBaoDto
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/3/416:52
 */
@Data
public class UserTaiBaoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String coopCode;
    /**
     *  用于光华接口鉴权
     */
    private String accessToken;
    /**
     *  讯飞声纹注册，认证唯一注册标识
     */
    private String frontUserId;

}
