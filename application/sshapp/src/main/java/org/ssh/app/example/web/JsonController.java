package org.ssh.app.example.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
            return getModelMap(books);

        } catch (Exception e) {

            return JsonViewUtil
                    .getModelMapError("Error trying to retrieve contacts.");
        }
    }

    @RequestMapping(value = "/getBook2s", method = RequestMethod.GET)
    public void view2(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        logger.info("json,list...");
            List<Book> books = bookService.getBooks();
            JsonViewUtil.buildJSONDataResponse(response, books,
                    (long) books.size());

    }

    /**
     * Generates modelMap to return in the modelAndView
     * @param contacts
     * @return
     */
    private ModelAndView getModelMap(List<Book> contacts){

        Map<String,Object> modelMap = new HashMap<String,Object>(3);
        modelMap.put("total", contacts.size());
        modelMap.put("data", contacts);
        modelMap.put("success", true);

        return new ModelAndView("jsonView", modelMap);
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
