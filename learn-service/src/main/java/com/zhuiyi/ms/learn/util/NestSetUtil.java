package com.zhuiyi.ms.learn.util;

import com.zhuiyi.ms.learn.entity.bo.CategoryBO;
import com.zhuiyi.ms.learn.entity.bo.MoveNodeInputBO;

public class NestSetUtil {
    public static MoveNodeInputBO generateMoveNodeInputParams(CategoryBO node, CategoryBO parent) {
        MoveNodeInputBO moveNodeInputBO = new MoveNodeInputBO();

        Integer newPos = parent.getLft() + 1;
        Integer distance = newPos - node.getLft();
        Integer width = node.getRgt() - node.getLft() + 1;
        Integer temPos = node.getLft();

        if (distance < 0) {
            distance -= width;
            temPos += width;
        }

        moveNodeInputBO.setNewPos(newPos);
        moveNodeInputBO.setOldRPos(node.getRgt());
        moveNodeInputBO.setTmpPos(temPos);
        moveNodeInputBO.setDistance(distance);
        moveNodeInputBO.setWidth(width);

        return moveNodeInputBO;
    }
}
