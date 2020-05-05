package com.sc.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// 拦截到达后台管理之前的没有登录的情况
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 判断session是否有用户，没有的话则断定未登录
        if (request.getSession().getAttribute("user") == null) {
            // 重定向到登陆页面
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
/*这只是一张拦截的网，但是拦截什么还不清楚，所以要再定义一个web配置*/
