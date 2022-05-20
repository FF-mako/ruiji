package com.itheima.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    //向下转型
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        //1.获得URI
        String uri = req.getRequestURI();

        //2.获得可以放行的资源
        String[] urls = {"/employee/login","/employee/logout","/backend/","/front/"};

        for (String url : urls) {
            if(uri.contains(url)){
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        //3.判断是否登录
        Object employee = req.getSession().getAttribute("employee");
        if (employee != null){
            //成功
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //4.未登录
        R r = R.error("NOLOGIN");
        String json = JSON.toJSONString(r);

        servletResponse.getWriter().write(json);
    }
}
