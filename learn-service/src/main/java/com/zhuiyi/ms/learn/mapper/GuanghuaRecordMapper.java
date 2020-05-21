package com.zhuiyi.ms.learn.mapper;

import com.zhuiyi.ms.learn.dto.request.GuangHuaRecordDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * @author rodbate
 * @Title: GuanghuaRecordMapper
 * @ProjectName learn
 * @Description: TODO
 * @date 2019/5/1714:43
 */
@Mapper
public interface GuanghuaRecordMapper {

    /**
      * @Description: 保存推送失败的光华数据记录
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/17 16:21
    */
    int saveRecord(GuangHuaRecordDto guangHuaRecordDto);

    /**
      * @Description: 获取推送失败的光华数据记录
    　* @param ${tags}
    　* @return ${return_type}
    　* @throws
    　* @author ${USER}
    　* @date 2019/5/17 16:22
    */
    List<GuangHuaRecordDto> getFailRecord();

    int deleteRecord(@Param(value = "id") Integer id);

    int updateRecord(GuangHuaRecordDto guangHuaRecordDto);

    int updateRecordStatus(Map<String,Object> params);

}
