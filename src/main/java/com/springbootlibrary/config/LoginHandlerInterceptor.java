package com.springbootlibrary.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");

        if (user == null){ //如果session该字段为空，表示未登录
            request.setAttribute("error","请登录后操作"); //返回提示消息
            request.getRequestDispatcher("/").forward(request,response);
            return false; //不放行
        }

        return true;
    }
}
