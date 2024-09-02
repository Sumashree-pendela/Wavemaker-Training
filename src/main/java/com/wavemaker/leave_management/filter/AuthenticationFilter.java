package com.wavemaker.leave_management.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter({"/leave/*", "/index.html"})
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean cookieValidation = cookieValidation(httpServletRequest, httpServletResponse);

        if (cookieValidation) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.sendRedirect("login.html");
        }
    }

    private static boolean cookieValidation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        Cookie[] cookies = httpServletRequest.getCookies();

        HttpSession session = httpServletRequest.getSession(true);
        Object cookieValue = session.getAttribute("loginCookie");
        boolean validated = false;
        if (cookieValue != null) {
            logger.debug("Cookie value" + cookieValue.toString());

            for (Cookie cookie : cookies) {
                logger.debug("From Request.." + cookie.getValue());
                if (cookie.getName().equals("loginCookie") && cookie.getValue().equals(cookieValue.toString())) {
                    logger.debug("Validated successfully");
                    validated = true;
                    break;
                }
            }
        }
        return validated;
    }
}
