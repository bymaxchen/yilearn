package com.zhuiyi.ms.learn.entity.bo;

import com.zhuiyi.ms.learn.entity.TPaperCategory;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class PaperCategoryTreeNodeBO {
    private Integer paperCategoryId;

    private String paperCategoryName;

    private Integer paperCount;

    private Boolean canOperate;

    private List<PaperCategoryTreeNodeBO> children;

    public List<PaperCategoryTreeNodeBO> getTree(List<TPaperCategory> poList, List<TPaperCategory> id2CountList) {
        HashMap<Integer, Integer> id2CountMap = new HashMap();
        for(TPaperCategory tPaperCategory: id2CountList) {
            id2CountMap.put(tPaperCategory.getPaperCategoryId(), tPaperCategory.getPaperCount());
        }

        return this.generateTree(0, 0, Integer.MAX_VALUE, poList, id2CountMap);
    }

    public List<PaperCategoryTreeNodeBO> generateTree(int depth, int lft, int rgt, List<TPaperCategory> poList, HashMap<Integer, Integer> id2CountMap) {
        List<PaperCategoryTreeNodeBO> paperCategoryTreeNodeBOList = new ArrayList<>();
        for(TPaperCategory po:poList) {
            if (po.getDepth() == depth && po.getLft() > lft && po.getRgt() < rgt) {
                PaperCategoryTreeNodeBO paperCategoryTreeNodeBO = new PaperCategoryTreeNodeBO();
                BeanUtils.copyProperties(po, paperCategoryTreeNodeBO);
                paperCategoryTreeNodeBO.setPaperCount(id2CountMap.get(paperCategoryTreeNodeBO.getPaperCategoryId()));
                List<PaperCategoryTreeNodeBO> children = generateTree(depth + 1, po.getLft(),po.getRgt(), poList, id2CountMap);
                if (children !=null && !children.isEmpty()) {
                    paperCategoryTreeNodeBO.setChildren(children);
                }
                paperCategoryTreeNodeBOList.add(paperCategoryTreeNodeBO);
            }
        }
        return paperCategoryTreeNodeBOList;
    }
}
