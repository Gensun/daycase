package com.sun.filter;

import com.sun.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-17 11:40
 **/

@WebFilter("/*")
public class SensitiveWordsFilter implements Filter {

    private List<String> list = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        ServletContext servletContext = filterConfig.getServletContext();
        String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(realPath));

            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            System.out.println(list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = (ServletRequest) Proxy.newProxyInstance(servletRequest.getClass().getClassLoader(), servletRequest.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                if (method.getName().equals("getParameterMap")) {
//                    String invoke = (String) method.invoke(servletRequest, args);
//                    if (invoke != null) {
//                        for (String str :
//                                list) {
//                            if (invoke.contains(str)) {
//                                invoke.replaceAll(str, "***");
//                            }
//                        }
//                    }

                    Map<String, String> map = (Map<String, String>) method.invoke(servletRequest, args);
                    User user = new User();
                    try {
                        BeanUtils.populate(user, map);
                        for (String s : list) {
                            if (user.getName() == null) return map;
                            if (user.getName().contains(s)) {
                                user.getName().replaceAll(s, "***");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return map;
                }
                return method.invoke(servletRequest, args);
            }
        });

        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
