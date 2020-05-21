package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bymax
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TChatLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private String sessionId;

    private String chatId;

    private String content;

    /**
     * èº«ä»½ï¼š1-åå¸­ 2-å®¢æˆ·
     */
    private Integer identity;

    private String serviceId;

    private String serviceName;

    private Date createTime;

    private String startTimestamp;

    private String endTimestamp;

    /**
     * 会话下标
     */
    private Integer groupId;

    /**
     * 0:未删除；1：删除
     */
    private Integer deleteStatus;

    /**
     * 0: 未违规； 1：违规
     */
    private Integer isViolation;

    private Integer examPartQuestionId;
}
