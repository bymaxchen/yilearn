package com.zhuiyi.ms.learn.auth;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.DynamicDataSourceCreator;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.zhuiyi.ms.learn.dto.SessionContextHolder;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.dto.response.ResponseVO;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import com.zhuiyi.ms.learn.exception.CustomException;
import com.zhuiyi.ms.learn.service.DataBaseInfoServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kloazhang
 * @date 2018/7/9
 */
@Component
@Order(1)
public class AuthHandlerInterceptor  implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthHandlerInterceptor.class);

    private static final String BUS_ID = "busId";


    private DataBaseInfoServiceImpl dataBaseInfoService;

    @Resource
    private DataSource dataSource;

    private Set<String> dataSourceNames = ConcurrentHashMap.newKeySet();

    private Environment environment;



    public AuthHandlerInterceptor(DataBaseInfoServiceImpl dataBaseInfoService, Environment environment, DataSource dataSource){
        this.dataBaseInfoService =dataBaseInfoService;
        this.environment = environment;
        this.dataSource = dataSource;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("trace_uuid", UUID.randomUUID().toString());


        String busId = null;
        Cookie[] cookies = request.getCookies();
        String requestUrl = request.getRequestURL().toString();
        if (requestUrl.contains("/dataConfiguration/createBusinessDb")
                || requestUrl.contains("/odal") || requestUrl.contains("/static")) {
            return true;
        }
        LOGGER.info(" ---->cookie:  + " + request.getCookies());

        if (null == cookies) {
            LOGGER.info("-----> no cookies");
        } else {
            for (Cookie cookie : cookies) {
                if (BUS_ID.equalsIgnoreCase(cookie.getName())) {
                    busId = cookie.getValue();
                    LOGGER.info(" ---->busId+ " + busId);
                    break;
                }
            }
        }



        // 如果url里面传了busId，就用url里面的busId
        String busIdOfQuery = request.getParameter(BUS_ID);
        if (busIdOfQuery != null && !StringUtils.isEmpty(busIdOfQuery)) {
            busId = busIdOfQuery;
        }

        MDC.put("bus_id", busId);

        if (StringUtils.isEmpty(busId)) {
            LOGGER.info(request.getRequestURI() + "---->learn业务库查询未传入busId");
            throw new CustomException(MsgCodeEnum.BUSINESS_ID_NOT_FOUND);
        }



        String dbName = dataBaseInfoService.getDbNameByBusId(busId);
        if (StringUtils.isEmpty(dbName)) {
            LOGGER.info(request.getRequestURI() + "---->未创建busId对应业务库");
            throw new CustomException(MsgCodeEnum.BUSINESS_DB_NOT_FOUND);
        } else if (!dataSourceNames.contains(dbName)) {
            LOGGER.info("dbName: " + dbName);
            //创建数据源
            DataSourceProperty DSProperty = new DataSourceProperty();
            String prefix = "spring.datasource.dynamic.datasource.master.";
            //通过默认连接的DB来替换成新DB连接串(Warning!: 默认的库为yiteach,后面会替换掉)
            DSProperty.setUrl((environment.getProperty(prefix + "url")).replace("ds_learn_business", dbName));
            DSProperty.setDriverClassName(environment.getProperty(prefix + "driverClassName"));
            //后面会整改成 用户名/密码 加密
            DSProperty.setUsername(environment.getProperty(prefix + "username"));
            DSProperty.setPassword(environment.getProperty(prefix + "password"));

            DataSource dataSourceCarrier = new DynamicDataSourceCreator().createBasicDataSource(DSProperty);
            DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) dataSource;
            dynamicRoutingDataSource.addDataSource(dbName, dataSourceCarrier);
            dataSourceNames.add(dbName);
        }

        // 使用mybatis，通过变量赋值数据库字段
        SessionContextHolder.setDbName(dbName);
        // 通过使用mybatis的动态数据源方法，适用于新表走mybatis的方法
        request.getSession().setAttribute("dataSourceName", dbName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    private void returnJson(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            LOGGER.error("response error", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static String ReadAsChars(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }



}
