package com.zhuiyi.ms.learn.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zhuiyi.ms.learn.dto.response.BaseResponse;
import com.zhuiyi.ms.learn.enums.MsgCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

/**
 * 统一异常处理类
 *
 * @author crazyhu@wezhuiyi.com
 * @date 2018-07-17 16:00
 **/
@ControllerAdvice
public class DefaultExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(value = Exception.class)
//    public BaseResponse errorHandler(Exception e) {
//        BaseResponse response = new BaseResponse();
//        response.setCode(MsgCodeEnum.SYSTEM_ERROR.getCode());
//        response.setMsg(MsgCodeEnum.SYSTEM_ERROR.getMsg());
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        request.setAttribute("responseData", JSON.toJSONString(response));
//        logger.error("未知异常：", e);
//        return response;
//    }

    /**
     * 处理业务异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public BaseResponse errorHandler(BaseException e) {
        logger.info("业务异常：", e);
        BaseResponse response = new BaseResponse();
        response.setCode(e.getCode());
        response.setMsg(e.getMessage());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        request.setAttribute("responseData", JSON.toJSONString(response));
        return response;
    }


//    /**
//     * 处理参数无效异常
//     */
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public BaseResponse processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        logger.warn(e.getMessage());
//        String message = "";
//        BindingResult result = e.getBindingResult();
//        for (Object object : result.getAllErrors()) {
//            if (object instanceof FieldError) {
//                FieldError fieldError = (FieldError) object;
//                message = fieldError.getDefaultMessage();
//            }
//        }
//        BaseResponse responseData = BaseResponse.successResponse();
//        responseData.setCode(MsgCodeEnum.PARAM_INVALID.getCode());
//        responseData.setMsg(message);
//        return responseData;
//    }
//
//    /**
//     * 处理参数错误异常
//     */
//    @ExceptionHandler({ValidationException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public BaseResponse processMethodArgumentNotValidException(ValidationException e) {
//        logger.error("参数错误", e);
//        BaseResponse responseData = new BaseResponse(MsgCodeEnum.PARAM_INVALID);
//        return responseData;
//    }

//    /**
//     * 处理非法JSON请求转化异常
//     */
//    @ExceptionHandler({JSONException.class, HttpMessageNotReadableException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public BaseResponse processJSONException(Exception e) {
//        logger.error("");
//        BaseResponse responseData = new BaseResponse();
//        responseData.setCode(MsgCodeEnum.PARAM_INVALID.getCode());
//        responseData.setMsg("缺少请求内容、或请求参数类型错误或请求的内容不是合法的JSON格式");
//        return responseData;
//    }

}
