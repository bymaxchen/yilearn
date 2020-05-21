package com.zhuiyi.ms.learn.service;

import com.zhuiyi.ms.learn.common.DatabaseUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rodbate
 * @Title: DataConfigurationServiceImpl
 * @ProjectName learn
 * @Description: TODO
 * @date 2018/10/2316:23
 */
@Service
public class DataConfigurationServiceImpl {

    public Map<String,Object> getTableInfo(){

        List<String> tableNames = DatabaseUtil.getTableNames();
        List<String> responseTableNames = new ArrayList<>();
        String flag = "java_";
        if(null != tableNames && tableNames.size()>0){
            for(String tableName : tableNames){
                tableName = flag.concat(tableName);
                responseTableNames.add(tableName);
            }
        }
        Map<String,Object> data = new HashMap<>();
        data.put("tableNames", responseTableNames);
        return data;
    }


}
