package com.sun.servlet;

import com.sun.domain.User;
import com.sun.server.UserService;
import com.sun.server.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 15:36
 **/

@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        List<User> users = userService.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }
}
