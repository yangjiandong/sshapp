package com.ekingsoft.example.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ekingsoft.example.entity.Book;
import com.ekingsoft.example.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/getBooks", method = RequestMethod.GET)
    public @ResponseBody Map<String,? extends Object> showBooks(){
        logger.info("list...");

        List<Book> books = bookService.getAll();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("books", books);
        logger.info(modelMap.toString());

        return modelMap;
    }

    @RequestMapping(value = "/getBooks4", method = RequestMethod.GET)
    public ModelAndView view4() throws Exception {
        logger.info("json,list4...");

        List<Book> books = bookService.getAll();
        Map<String, Object> modelMap = new HashMap<String, Object>(3);
        modelMap.put("total", books.size());
        modelMap.put("data", books);
        modelMap.put("success", true);

        return new ModelAndView("jsonView", modelMap);
    }
}
