package com.sc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

// 拦截所有有controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 标识这个方法可做异常处理(只要是exception级别的都可处理)
    @ExceptionHandler(Exception.class)
    // ModelAndView，此类返回一个页面以及后台返回前端的一些信息
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        // 记录请求的url和异常信息（{}占位作用）
        logger.error("Requst URL : {}, Exception : {}", request.getRequestURL(),e);

        // 排除资源找不到错误（当状态异常标识状态码时就不做处理）
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        // 返回页面
        ModelAndView mv = new ModelAndView();
        // url
        mv.addObject("url", request.getRequestURL());
        // 异常信息
        mv.addObject("exception",e);

        mv.setViewName("error/error");
        return mv;
    }
}
