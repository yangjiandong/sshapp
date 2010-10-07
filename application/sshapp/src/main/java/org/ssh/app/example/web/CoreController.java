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
import org.ssh.app.example.service.BookService;
import org.ssh.app.example.service.ContactService;

//http://stsmedia.net/spring-finance-part-2-spring-mvc-spring-30-rest-integration/
@Controller("example_coreController")
public class CoreController {
    private static Logger logger = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/init/exampleData")
    public ModelAndView initData(ModelMap modelMap, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("开始初始化Example数据...");

        this.contactService.initData();
        this.bookService.initData();

        return new ModelAndView("redirect:/index2.jsp");

    }
}