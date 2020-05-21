package com.zhuiyi.ms.learn.entity.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuiyi.ms.learn.entity.TQuestionCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class QuestionCategoryTreeNodeBO {
    @JSONField(name = "id")
    private Integer questionCategoryId;

    @JSONField(name = "name")
    private String questionCategoryName;

    private Integer questionCount;

    private Boolean canOperate;

    private List<QuestionCategoryTreeNodeBO> children;

    public List<QuestionCategoryTreeNodeBO> getTree(List<TQuestionCategory> poList, List<TQuestionCategory> id2CountList) {
        HashMap<Integer, Integer> id2CountMap = new HashMap();
        for(TQuestionCategory tQuestionCategory: id2CountList) {
            id2CountMap.put(tQuestionCategory.getQuestionCategoryId(), tQuestionCategory.getQuestionCount());
        }

        return this.generateTree(0, 0, Integer.MAX_VALUE, poList, id2CountMap);
    }

    public List<QuestionCategoryTreeNodeBO> generateTree(int depth, int lft, int rgt, List<TQuestionCategory> poList, HashMap<Integer, Integer> id2CountMap) {
        List<QuestionCategoryTreeNodeBO> questionCategoryTreeNodeBOList = new ArrayList<>();
        for(TQuestionCategory po:poList) {
            if (po.getDepth() == depth && po.getLft() > lft && po.getRgt() < rgt) {
                QuestionCategoryTreeNodeBO questionCategoryTreeNodeBO = new QuestionCategoryTreeNodeBO();
                BeanUtils.copyProperties(po, questionCategoryTreeNodeBO);
                questionCategoryTreeNodeBO.setQuestionCount(id2CountMap.get(questionCategoryTreeNodeBO.getQuestionCategoryId()));
                List<QuestionCategoryTreeNodeBO> children = generateTree(depth + 1, po.getLft(),po.getRgt(), poList, id2CountMap);
                if (children !=null && !children.isEmpty()) {
                    questionCategoryTreeNodeBO.setChildren(children);
                }
                questionCategoryTreeNodeBOList.add(questionCategoryTreeNodeBO);
            }
        }
        return questionCategoryTreeNodeBOList;
    }
}
