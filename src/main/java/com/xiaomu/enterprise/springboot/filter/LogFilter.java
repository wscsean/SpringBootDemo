package com.xiaomu.enterprise.springboot.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LogFilter")
public class LogFilter implements Filter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        //HttpServletResponse httpResponse = (HttpServletResponse) resp;

        LOGGER.info("[LogFilter] URL: {}", httpRequest.getRequestURI());

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException
    {
        LOGGER.info("[LogFilter] init");
    }

    public void destroy()
    {
        LOGGER.info("[LogFilter] destroy");
    }

}
