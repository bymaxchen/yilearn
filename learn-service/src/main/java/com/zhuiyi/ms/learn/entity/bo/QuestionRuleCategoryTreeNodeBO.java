package com.zhuiyi.ms.learn.entity.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TQuestionRuleCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class QuestionRuleCategoryTreeNodeBO {
    private Integer questionRuleCategoryId;

    private String questionRuleCategoryName;

    private Integer questionRuleCount;

    private List<QuestionRuleCategoryTreeNodeBO> children;

    private Boolean canOperate;

    public List<QuestionRuleCategoryTreeNodeBO> getTree(List<TQuestionRuleCategory> poList, List<TQuestionRuleCategory> id2CountList) {
        HashMap<Integer, Integer> id2CountMap = new HashMap();
        for(TQuestionRuleCategory tQuestionRuleCategory: id2CountList) {
            id2CountMap.put(tQuestionRuleCategory.getQuestionRuleCategoryId(), tQuestionRuleCategory.getQuestionRuleCount());
        }

        return this.generateTree(0, 0, Integer.MAX_VALUE, poList, id2CountMap);
    }

    public List<QuestionRuleCategoryTreeNodeBO> generateTree(int depth, int lft, int rgt, List<TQuestionRuleCategory> poList, HashMap<Integer, Integer> id2CountMap) {
        List<QuestionRuleCategoryTreeNodeBO> questionCategoryTreeNodeBOList = new ArrayList<>();
        for(TQuestionRuleCategory po:poList) {
            if (po.getDepth() == depth && po.getLft() > lft && po.getRgt() < rgt) {
                QuestionRuleCategoryTreeNodeBO questionRuleCategoryTreeNodeBO = new QuestionRuleCategoryTreeNodeBO();
                BeanUtils.copyProperties(po, questionRuleCategoryTreeNodeBO);
                questionRuleCategoryTreeNodeBO.setQuestionRuleCount(id2CountMap.get(questionRuleCategoryTreeNodeBO.getQuestionRuleCategoryId()));
                List<QuestionRuleCategoryTreeNodeBO> children = generateTree(depth + 1, po.getLft(),po.getRgt(), poList, id2CountMap);
                if (children !=null && !children.isEmpty()) {
                    questionRuleCategoryTreeNodeBO.setChildren(children);
                }
                questionCategoryTreeNodeBOList.add(questionRuleCategoryTreeNodeBO);
            }
        }
        return questionCategoryTreeNodeBOList;
    }
}
