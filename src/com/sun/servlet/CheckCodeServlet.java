package com.sun.servlet;

/**
 * @program: daycase
 * @description:
 * @author: Genie Sun
 * @create: 2020-01-15 10:42
 **/

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setHeader("pragma","no-cache");
        resp.setHeader("cache-control","no-cache");
        resp.setHeader("expires","0");

        int width = 80;
        int height = 30;
        BufferedImage bufferedImage = new BufferedImage(width, height, TYPE_INT_RGB);

        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0, 0, width, height);

        String checkCode = getCheckCode();
        req.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        graphics.setColor(Color.YELLOW);
        graphics.setFont(new Font("黑体", Font.BOLD, 24));
        graphics.drawString(checkCode, 15, 25);

        ImageIO.write(bufferedImage, "PNG", resp.getOutputStream());
    }

    private String getCheckCode() {
        String baseCharacter = "abcdefghijklmnopqrstuvwxyz";
        String base = "0123456789" + baseCharacter + baseCharacter.toUpperCase();

        int size = base.length();
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            int index = random.nextInt(size);
            char c = base.charAt(index);
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
