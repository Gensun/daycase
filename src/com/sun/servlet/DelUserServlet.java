package com.sun.servlet;

import com.sun.server.UserService;
import com.sun.server.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-16 15:55
 **/
@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UserService userService = new UserServiceImpl();
        userService.delete(id);

        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");
    }
}
