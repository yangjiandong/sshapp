package org.ssh.app.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ssh.app.common.entity.User;
import org.ssh.app.common.service.AccountManager;

//http://stsmedia.net/spring-finance-part-2-spring-mvc-spring-30-rest-integration/
@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "person/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, ModelMap modelMap) {
        Assert.notNull(id, "Identifiler must be privided.");
        modelMap.addAttribute("person", accountManager.getLoadedUser(id));
        return "person/show";
    }

    @RequestMapping(value = "/login")
    //?? (value = "/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response, User userinfo) {
        logger.info("user login..");
        logger.info(userinfo.toString());

        if (userinfo.getLoginName().equals("admin") && userinfo.getPlainPassword().equals("123")) {
            request.setAttribute("user", userinfo);
            return "users/list";//不能用/users/list,否则页面文件指到webapp/user/views/users/list.jsp
        } else {
            return "users/loginerr";
        }

    }

}
