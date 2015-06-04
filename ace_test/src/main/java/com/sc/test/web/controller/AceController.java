package com.sc.test.web.controller;

import com.mongodb.DuplicateKeyException;
import com.sc.test.bean.*;
import com.sc.test.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class AceController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = {"/index", "/"},
            method = RequestMethod.GET)
    public String toIndexPage() {
        return "forward:/html/index.html";
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.GET)
    public String toLoginPage() {
        return "forward:/html/login.html";
    }

    @ResponseBody
    @RequestMapping(value = "/login",
            produces = "application/json",
            method = RequestMethod.POST)
    public ResponseMessage login(User user, HttpSession session) {
        User dbUser = userService.login(user);
        if (dbUser == null) {
            return ResponseMessage.error("Username or password is wrong!");
        }
        session.setAttribute("currUser", dbUser);
        return ResponseMessage.success(dbUser);
    }

    @ResponseBody
    @RequestMapping(value = "/logout")
    public ResponseMessage logout(HttpSession session) {
        session.removeAttribute("currUser");
        return ResponseMessage.success();
    }

    @ResponseBody
    @RequestMapping(value = "/user",
            produces = "application/json",
            method = RequestMethod.POST)
    public ResponseMessage create(User user) {
        try {
            userService.saveUser(user);
            return ResponseMessage.success();
        } catch (DuplicateKeyException e) {
            return ResponseMessage.error(ResponseMessage.ERR_DUPLICATE);
        } catch (Exception e) {
            return ResponseMessage.error(ResponseMessage.ERR_UNKNOWN);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user/{username}",
            produces = "application/json",
            method = RequestMethod.PUT)
    public ResponseMessage edit(@PathVariable String username, User user) {
        try {
            user.setUsername(username);
            userService.update(user);
            return ResponseMessage.success();
        } catch (Exception e) {
            return ResponseMessage.error(ResponseMessage.ERR_UNKNOWN, "Update failed!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/user",
            produces = "application/json",
            method = RequestMethod.GET)
    public Object findUser(User user, DataTableParam param) {

        PageResult result = userService.find(user, param.getiDisplayStart(),
                param.getiDisplayLength(), param.getSortComboForMorphia());

        DataTableResponse response = new DataTableResponse();
        response.setRecordsTotal(result.getResult().size());
        response.setRecordsFiltered(result.getTotal());
        response.setData(result.getResult());
        return ResponseMessage.success(response);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{username}",
            produces = "application/json",
            method = RequestMethod.GET)
    public ResponseMessage get(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return ResponseMessage.success(user);
        }
        return ResponseMessage.error(ResponseMessage.ERR_NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/user/{username}",
            produces = "application/json",
            method = RequestMethod.DELETE)
    public ResponseMessage deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseMessage.success();
    }

    @ResponseBody
    @RequestMapping(value = "/user",
            produces = "application/json",
            method = RequestMethod.DELETE)
    public ResponseMessage batchDelete(String[] array) {
        for (String username : array) {
            userService.deleteUser(username);
        }
        return ResponseMessage.success();
    }

}
