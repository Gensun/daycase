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

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-16 14:32
 **/
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserService service = new UserServiceImpl();
        User user = service.findUserById(id);
        req.setAttribute("user", user);
        System.out.println(user);
        req.getRequestDispatcher("/update.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
