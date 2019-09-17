package cn.wdb.controller;

import cn.wdb.dao.UserDao;
import cn.wdb.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 切面
 */
@Component
@Aspect
public class AopController {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(* cn.wdb.controller.*.*(..))")
    public void pj(){};

    @Before("pj()")
    public void doBefore() throws Exception{
        String uri = request.getRequestURI();
        if(uri.contains("/login") ||uri.contains("/img") ||uri.contains("/callBack") ||uri.contains("/css/") ||uri.contains("/js/") ||uri.contains("/fonts/")){

        }else{
            Object admin = request.getSession().getAttribute("admin");
            if(admin != null){

            }else {
                request.setAttribute("login_msg","您还没登陆");
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                request.getRequestDispatcher("/pages/login.jsp").forward(request,response);
            }
        }
    }
}
