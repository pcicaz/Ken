package com.sc.test.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sc.test.bean.User;
import com.sc.test.service.IUserService;
import com.sc.test.util.ScSessionContext;

@Controller
public class SampleController {
    
    @Resource(name = "userService")
    private IUserService userService;
    
    @RequestMapping(value = "/login/toLogin")
    public String toLoginPage(HttpServletRequest request, HttpSession session) {
        String url = request.getHeader("Referer");
        request.setAttribute("url", url);
        request.setAttribute("JSESSIONID", session.getId());
        return "login";
    }
    
    @RequestMapping(value = "/login/login")
    public String login(String userName, String password, String url,
        @ModelAttribute(value = "JSESSIONID") String JSESSIONID) {
        HttpSession session = ScSessionContext.getInstance().getSession(JSESSIONID);
        session.setAttribute("session_url", url);
        User user = getUserService().login(userName, password);
        if (user == null) {
            user = getUserService().saveUser(userName, password);
        }
        session.setAttribute("user", user);
        return "redirect:" + url + "index";
//         return "redirect:/index";
    }
    
    @RequestMapping(value = "/index1")
    public String toIndexPage(String JSESSIONID, HttpServletRequest request) {
        if (JSESSIONID != null && !JSESSIONID.isEmpty()) {
            HttpSession session = ScSessionContext.getInstance().getSession(JSESSIONID);
            String url = (String)session.getAttribute("session_url");
            String sessionId = session.getId();
            User user = (User)session.getAttribute("user");
            session.invalidate();
            request.setAttribute("url", url);
            request.setAttribute("sessionId", sessionId);
            request.getSession().setAttribute("user", user);
        }
        return "test/test1";
    }
    
    @RequestMapping(value = "/loginOut")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "index";
    }
    
    public IUserService getUserService() {
        return userService;
    }
    
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
}