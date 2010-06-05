package org.ssh.app.example.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.ssh.app.example.entity.Book;
import org.ssh.app.example.service.BookService;
import org.ssh.app.util.JsonViewUtil;

//http://loianegroner.com/tag/json-lib-ext-spring/
@Controller
@RequestMapping("/jsons")
public class JsonController extends MultiActionController {
    private static Logger logger = LoggerFactory
            .getLogger(JsonController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getBooks", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("json,list...");

        try {
            List<Book> books = bookService.getBooks();
            return JsonViewUtil.getModelMap(books);

        } catch (Exception e) {

            return JsonViewUtil
                    .getModelMapError("Error trying to retrieve contacts.");
        }
    }

//    public ModelAndView create(HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//
//        try{
//
//            Object data = request.getParameter("data");
//
//            List<Book> contacts = contactService.create(data);
//
//            return getModelMap(contacts);
//
//        } catch (Exception e) {
//
//            return JsonViewUtil.getModelMapError("Error trying to create contact.");
//        }
//    }
}
