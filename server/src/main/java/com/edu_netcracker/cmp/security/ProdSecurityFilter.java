package com.edu_netcracker.cmp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.Objects;

//TODO: implement security
@Order(1)
@Slf4j
@Profile("prod")
public class ProdSecurityFilter extends HttpFilter {

    @Value("${security.trusted-application-code}")
    private String trustedApplication;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Security profile is enabled. Use \"Authorization: {}\"", trustedApplication);
        super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Validate types
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException(request + " not HttpServletRequest");
        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException(request + " not HttpServletResponse");
        }

        // Validate header
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (HttpMethod.OPTIONS.equalsIgnoreCase(httpServletRequest.getMethod()) || !Objects.equals(authorization, trustedApplication)){
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        // Next call
        chain.doFilter(request, response);
    }
}
