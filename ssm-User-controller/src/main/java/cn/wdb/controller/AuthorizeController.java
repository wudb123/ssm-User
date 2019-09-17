package cn.wdb.controller;
import cn.wdb.domain.GitHubDTO;
import cn.wdb.domain.GitHubUser;
import cn.wdb.utils.AuthorizeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@PropertySource("classpath:application.properties")
public class AuthorizeController {
    @Autowired
    private AuthorizeProvider authorizeProvider;
    @Value("${gitHub.authorize.client_id}")
    private String client_id;
    @Value("${gitHub.authorize.client_secret}")
    private String client_secret;
    @Value("${gitHub.authorize.redirect_uri}")
    private String redirect_uri;


    @RequestMapping("/callBack")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        GitHubDTO gitHubDTO = new GitHubDTO();
        gitHubDTO.setClient_id(client_id);
        gitHubDTO.setClient_secret(client_secret);
        gitHubDTO.setCode(code);
        gitHubDTO.setRedirect_uri(redirect_uri);
        gitHubDTO.setState(state);
        String access_token = authorizeProvider.getAccess_token(gitHubDTO);
        GitHubUser gitHubUser = authorizeProvider.getUser(access_token);
        HttpSession session = request.getSession();
        if (gitHubUser != null){
            //登录成功
            session.setAttribute("admin",gitHubUser);
            return "index";
        }else {
            //登录失败，重新登录
            return "login";
        }

    }
}

