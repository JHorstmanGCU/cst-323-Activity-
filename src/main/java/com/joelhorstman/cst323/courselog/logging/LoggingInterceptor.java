package com.joelhorstman.cst323.courselog.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            logger.info("ENTER class={} method={} httpMethod={} uri={}",
                    handlerMethod.getBeanType().getSimpleName(),
                    handlerMethod.getMethod().getName(),
                    request.getMethod(),
                    request.getRequestURI());
        } else {
            logger.info("ENTER httpMethod={} uri={}", request.getMethod(), request.getRequestURI());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod handlerMethod) {
            if (ex != null) {
                logger.error("EXCEPTION class={} method={} uri={} message={}",
                        handlerMethod.getBeanType().getSimpleName(),
                        handlerMethod.getMethod().getName(),
                        request.getRequestURI(),
                        ex.getMessage(),
                        ex);
            }

            logger.info("EXIT class={} method={} uri={} status={}",
                    handlerMethod.getBeanType().getSimpleName(),
                    handlerMethod.getMethod().getName(),
                    request.getRequestURI(),
                    response.getStatus());
        } else {
            logger.info("EXIT uri={} status={}", request.getRequestURI(), response.getStatus());
        }
    }
}
