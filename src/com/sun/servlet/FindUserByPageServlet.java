package com.sun.servlet;

import com.sun.domain.PageBean;
import com.sun.domain.User;
import com.sun.server.UserService;
import com.sun.server.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 15:32
 **/

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        HttpSession session = req.getSession();
        Object attribute = session.getAttribute("user");
        System.out.println(attribute);
        if (attribute == null || Objects.isNull(attribute)){
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        String currentPage = req.getParameter("currentPage");
        String rows = req.getParameter("rows");
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }

        if (rows == null || "".equals(rows)) {
            rows = "5";
        }
        Map<String, String[]> map = req.getParameterMap();

        UserService service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage, rows, map);
        System.out.println(pb);

        req.setAttribute("users", pb.getList());
        req.setAttribute("pb", pb);
        req.setAttribute("condition", map);
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }
}
