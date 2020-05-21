package com.zhuiyi.ms.learn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TSceneQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer sceneQuestionId;

    /**
     * 题目包含的全局规则
     */
    private String globalRules;

    /**
     * 题目包含的会话流程
     */
    private String processRules;

    private String coreQuestion;

    private String sceneIntro;

    private String customerInfoTitleGroup;

}
