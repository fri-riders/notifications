package com.fri.rso.fririders.notifications.log;


import org.apache.logging.log4j.CloseableThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Interceptor
@Priority(Interceptor.Priority.PLATFORM_BEFORE)
public class LogContextInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LogContextInterceptor.class);

    private ApplicationContext applicationContext;

    public LogContextInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        log(request, response, requestId);
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        request.setAttribute("requestId", requestId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        long startTime = (Long) request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;
        logger.debug("requestId {}, Handle :{} , request take time: {}", request.getAttribute("requestId"), handler, executeTime);
    }

    private void log(HttpServletRequest request, HttpServletResponse response, String requestId) {
        CloseableThreadContext.put("appName", applicationContext.getId().split(":")[0]);
        CloseableThreadContext.put("env", applicationContext.getId().split(":")[1]);
        CloseableThreadContext.put("version", applicationContext.getEnvironment().getProperty("app.version"));
        CloseableThreadContext.put("uri", request.getRequestURI());
        logger.debug("requestId {}, host {}  HttpMethod: {}, URI : {}", requestId, request.getHeader("host"),
                request.getMethod(), request.getRequestURI());
    }

}