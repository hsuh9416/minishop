package com.conf;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilter;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilterWrapper;

/*
 * lucy XSS Filter 설정 클래스
 */
public class XssEscapeServletFilter extends OncePerRequestFilter implements Ordered {
    private XssEscapeFilter xssEscapeFilter = XssEscapeFilter.getInstance();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new XssEscapeServletFilterWrapper(request, xssEscapeFilter), response);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.endsWith(".do") && !path.endsWith(".jsp");
    }

}
