package org.ssh.app.example.web;

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
import org.ssh.app.example.entity.Book;
import org.ssh.app.example.service.BookService;

@Controller
@RequestMapping("/books")
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/getBooks", method = RequestMethod.GET)
    public @ResponseBody Map<String,? extends Object> showBooks(){
        logger.info("list...");

        List<Book> books = bookService.getBooks();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("books", books);
        logger.info(modelMap.toString());

        return modelMap;
    }


}
