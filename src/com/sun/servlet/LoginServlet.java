package com.sun.servlet;

import com.sun.domain.User;
import com.sun.server.UserService;
import com.sun.server.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 10:42
 **/

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String verifycode = req.getParameter("verifycode");

        HttpSession session = req.getSession();
        String checkcodeServer = (String) session.getAttribute("CHECKCODE_SERVER");

        if (checkcodeServer == null || !checkcodeServer.equalsIgnoreCase(verifycode)) {
            req.setAttribute("login_msg", "验证码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);

            return;
        }

        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        UserService userService = new UserServiceImpl();
        User loginUser = userService.login(user);
        if (loginUser == null){
            req.setAttribute("login_msg","登录失败,用户名或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }else {
            session.setAttribute("user",loginUser);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
