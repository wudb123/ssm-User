package cn.wdb.controller;

import cn.wdb.domain.Admin;
import cn.wdb.domain.User;
import cn.wdb.service.UserService;
import com.github.pagehelper.PageInfo;
import org.aspectj.weaver.ast.Var;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

/**
 * 用户表现层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/img")
    public void img(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.pink);
        g.fillRect(0,0,width,height);
        g.setColor(Color.blue);
        g.drawRect(0,0,width,height);
        g.setColor(Color.red);
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqstuvwxyz0123456789";
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 5; i++) {
            int index = ran.nextInt(str.length());
            char c = str.charAt(index);
            sb.append(c);
            g.drawString(c+"",width/5*i,height/2);
        }
        String check_session = sb.toString();
        //System.out.println(check_session);
        request.getSession().setAttribute("check_session",check_session);
        g.setColor(Color.yellow);
        for (int i = 0; i < 5; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            g.drawLine(x1,y1,x2,y2);
        }
        //输出
        ImageIO.write(image,"jpg",response.getOutputStream());
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request,Admin admin,String verifycode) throws Exception{
        String check_session = (String) request.getSession().getAttribute("check_session");
        request.getSession().removeAttribute("check_session");//确保验证码一次性
        if (verifycode != null && check_session.equalsIgnoreCase(verifycode)) {
            //验证码正确
            admin = userService.login(admin);
            if (admin != null) {
                //登录成功
                request.getSession().setAttribute("admin", admin);
                //跳转到index.jsp页面
                return "index";
            } else {
                request.setAttribute("login_msg", "用户名或密码错误");
                return "login";
            }
        } else {
            //验证码错误
            request.setAttribute("login_msg", "验证码错误");
            return "login";

        }
    }

    @RequestMapping("/findAll")
    public ModelAndView findAll(User user, @RequestParam(name = "pageSize",defaultValue = "7") Integer pageSize, @RequestParam(name = "page",defaultValue = "1") Integer page) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<User> Users = userService.findAll(page, pageSize,user);
        PageInfo pageInfo = new PageInfo(Users);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("user",user);
        mv.setViewName("list");
        return mv;
    }

    @RequestMapping("/findById")
    public ModelAndView findById(Integer id) throws Exception{
        ModelAndView mv = new ModelAndView();
        User user = userService.findById(id);
        mv.addObject("user",user);
        mv.setViewName("update");
        return  mv;
    }

    @RequestMapping("/updateUser")
    public String updateUser(User user) throws Exception{
        userService.update(user);
        return "redirect:findAll";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(Integer id) throws Exception{
        userService.delUser(id);
        return "redirect:findAll";
    }

    @RequestMapping("/deleteSelect")
    public String deleteSelect(Integer[] ids) throws Exception{
        for (Integer id : ids) {
            userService.delUser(id);
        }
        return "redirect:findAll";
    }

    @RequestMapping("/addUser")
    public String addUser(User user) throws Exception{
        userService.addUser(user);

        return "redirect:findAll";
    }
}
