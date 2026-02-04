package com.Caching.Weather_Service.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        System.out.println("Request URL: " + httpServletRequest.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse); // now the request is passed to the next filter if available else it will go to the Dispatcher Servlet.
    }
}
