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
 * @create: 2020-01-16 15:53
 **/
@WebServlet("/delSeletedServlet")
public class DelSeletedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uids = req.getParameterValues("uid");
        UserService userService = new UserServiceImpl();
        userService.deletes(uids);

        resp.sendRedirect(req.getContextPath() + "/findUserByPageServlet");
    }
}
