package org.ssh.app.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ssh.app.common.service.AccountManager;
import org.ssh.app.example.service.AuthorityService;
import org.ssh.app.example.service.BookService;
import org.ssh.app.example.service.ContactService;
import org.ssh.app.example.service.RoleService;
import org.ssh.app.example.service.UserRoleService;
import org.ssh.app.example.service.UserService;

//http://stsmedia.net/spring-finance-part-2-spring-mvc-spring-30-rest-integration/
@Controller
public class CoreController {
    private static Logger logger = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private BookService bookService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/initData.do")
    public ModelAndView initData(ModelMap modelMap, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("开始初始化数据...");

        this.accountManager.initData();
        this.bookService.initData();
        this.contactService.initData();

        this.userService.initData();
        //this.authorityService.initData();
        this.roleService.initData();
        this.userRoleService.initData();

        return new ModelAndView("redirect:/index2.jsp");

    }
}