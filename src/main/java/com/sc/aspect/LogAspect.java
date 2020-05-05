package com.sc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    // 日志记录
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // execution里面内容规定此切面拦截哪些类(com.sc.web包下的所有类的所有方法)
    @Pointcut("execution(* com.sc.web.*.*(..))")
    public void log(){

    }

    // 在log()方法切面之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        // 获取 HttpRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //取参数
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        // 获取类名方法名和请求参数(getDeclaringTypeName 类名    getName 方法名)
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}", requestLog);
    }

    // 在log()方法切面之后执行
    @After("log()")
    public void doAfter(){
        logger.info("--------doAfter-------");
    }

    // 返回内容
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result) {
        logger.info("Result : {}", result);
    }

    // 内部类，用于包装返回的信息
    private class RequestLog{
        private String url;  // 请求url
        private String ip;  // 访问者ip
        private String classMethod;  // 调用方法
        private Object[] args;  // 调用参数

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
