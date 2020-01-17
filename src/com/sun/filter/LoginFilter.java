package com.sun.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-17 11:14
 **/

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String url = httpServletRequest.getRequestURI();

        if (url.contains("/loginServlet") ||
                url.contains("/login.jsp") ||
                url.contains("/css/") ||
                url.contains("/fonts/") ||
                url.contains("/js/") ||
                url.contains("/checkCodeServlet")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = httpServletRequest.getSession();
            if (session.getAttribute("user") == null) {
                httpServletRequest.setAttribute("login_msg", "你尚未登录，请登录");
                httpServletRequest.getRequestDispatcher("/login.jsp").forward(servletRequest, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
