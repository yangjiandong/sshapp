package org.ssh.app.example.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.ssh.app.example.entity.Book;
import org.ssh.app.example.service.BookService;
import org.ssh.app.util.JsonViewUtil;

//http://loianegroner.com/tag/json-lib-ext-spring/
@Controller
@RequestMapping("/jsons")
public class JsonController {
    private static Logger logger = LoggerFactory.getLogger(JsonController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/getBooks", method = RequestMethod.GET)
    public void view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("json,list...");

        List<Book> books = bookService.loadBooks(null);

        JsonViewUtil
         .buildJSONDataResponse(response, books, (long) books.size());
    }

    @RequestMapping(value = "/getBook2s", method = RequestMethod.GET)
    public void view2(ModelMap map, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        logger.info("json,list2...");
        // List<Book> books = bookService.getBooks();
        List<String> books = new ArrayList<String>();
        books.add("oen book");
        books.add("two bool");
        JsonViewUtil.buildJSONDataResponse(response, books, (long) books.size());

    }

    @RequestMapping(value = "/getBooks3", method = RequestMethod.GET)
    public ModelAndView view3() throws Exception {
        logger.info("json,list3...");

        List<Book> books = bookService.getBooks();
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", books.size());
        modelMap.put("data", books);
        modelMap.put("success", true);

        // ModelAndView modelAndView = new ModelAndView();
        // modelAndView.setViewName("jsonView");
        // modelAndView.addObject(books);
        return new ModelAndView("jsonView3", modelMap);
    }

    @RequestMapping(value = "/getBooks4", method = RequestMethod.GET)
    public ModelAndView view4() throws Exception {
        logger.info("json,list4...");

        List<Book> books = bookService.getBooks();
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", books.size());
        modelMap.put("data", books);
        modelMap.put("success", true);

        // ModelAndView modelAndView = new ModelAndView();
        // modelAndView.setViewName("jsonView");
        // modelAndView.addObject(books);
        return new ModelAndView("jsonView", modelMap);
    }

    /**
     * Generates modelMap to return in the modelAndView
     *
     * @param contacts
     * @return
     */
    private ModelAndView getModelMap(List<Book> contacts) {

        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", contacts.size());
        modelMap.put("data", contacts);
        modelMap.put("success", true);

        return new ModelAndView("jsonView", modelMap);
    }
}
