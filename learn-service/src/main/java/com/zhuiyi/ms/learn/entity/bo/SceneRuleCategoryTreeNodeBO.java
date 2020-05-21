package com.zhuiyi.ms.learn.entity.bo;

import com.zhuiyi.ms.learn.entity.TSceneRuleCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Data
public class SceneRuleCategoryTreeNodeBO {
    private Integer sceneRuleCategoryId;

    private String sceneRuleCategoryName;

    private Integer sceneRuleCount;

    private List<SceneRuleCategoryTreeNodeBO> children;

    private Boolean canOperate;

    public List<SceneRuleCategoryTreeNodeBO> getTree(List<TSceneRuleCategory> poList, List<TSceneRuleCategory> id2CountList) {
        HashMap<Integer, Integer> id2CountMap = new HashMap();
        for(TSceneRuleCategory tSceneRuleCategory: id2CountList) {
            id2CountMap.put(tSceneRuleCategory.getSceneRuleCategoryId(), tSceneRuleCategory.getSceneRuleCount());
        }

        List<SceneRuleCategoryTreeNodeBO> list = this.generateTree(0, 0, Integer.MAX_VALUE, poList, id2CountMap);

        // 流程规则需要在第一个元素
        Collections.swap(list.get(0).getChildren(), 0, 1);

        return list;
    }

    public List<SceneRuleCategoryTreeNodeBO> generateTree(int depth, int lft, int rgt, List<TSceneRuleCategory> poList, HashMap<Integer, Integer> id2CountMap) {
        List<SceneRuleCategoryTreeNodeBO> sceneCategoryTreeNodeBOList = new ArrayList<>();
        for(TSceneRuleCategory po:poList) {
            if (po.getDepth() == depth && po.getLft() > lft && po.getRgt() < rgt) {
                SceneRuleCategoryTreeNodeBO sceneRuleCategoryTreeNodeBO = new SceneRuleCategoryTreeNodeBO();
                BeanUtils.copyProperties(po, sceneRuleCategoryTreeNodeBO);
                sceneRuleCategoryTreeNodeBO.setSceneRuleCount(id2CountMap.get(sceneRuleCategoryTreeNodeBO.getSceneRuleCategoryId()));
                List<SceneRuleCategoryTreeNodeBO> children = generateTree(depth + 1, po.getLft(),po.getRgt(), poList, id2CountMap);
                if (children !=null && !children.isEmpty()) {
                    sceneRuleCategoryTreeNodeBO.setChildren(children);
                }
                sceneCategoryTreeNodeBOList.add(sceneRuleCategoryTreeNodeBO);
            }
        }
        return sceneCategoryTreeNodeBOList;
    }    
}
