package org.ssh.app.example.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.ssh.app.cache.CacheUtil;
import org.ssh.app.example.entity.Book;
import org.ssh.app.example.service.BookService;
import org.ssh.app.util.JsonViewUtil;

@Controller
@RequestMapping("/book")
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @RequestMapping(value="/getBooks2", method = RequestMethod.GET)
    public @ResponseBody Map<String,? extends Object> showBooks2(){
        logger.info("list...");

        List<Book> books = bookService.getBooksOnMethodCache();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("books", books);
        logger.info(modelMap.toString());

        return modelMap;
    }

    //方法缓存
    @RequestMapping(value="/getBooks", method = RequestMethod.GET)
    public ModelAndView showBooks(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("method cache list...");
        long start = System.currentTimeMillis();

        List<Book> books = bookService.getBooksOnMethodCache();
        logger.info(" method cache 执行共计:" + (System.currentTimeMillis() - start) + " ms");
        return JsonViewUtil.getModelMap(books);
    }

    //表查询缓存
    @RequestMapping(value="/getBooks3", method = RequestMethod.GET)
    public ModelAndView showBooks3(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("table cache list...");
        long start = System.currentTimeMillis();

        List<Book> books = bookService.getBooks2();
        logger.info(" table cache 执行共计:" + (System.currentTimeMillis() - start) + " ms");
        return JsonViewUtil.getModelMap(books);
    }

    //手工缓存
    @RequestMapping(value="/getBooks4", method = RequestMethod.GET)
    public ModelAndView showBooks4(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("man hand cache list...");
        long start = System.currentTimeMillis();

        List<Book> books = (ArrayList<Book>)CacheUtil.getCache("bookController", "books");
        if (books == null) {
            books = bookService.getBooks3();
            CacheUtil.setCache("bookController", "books", books);
        }
        logger.info(" man hand 执行共计:" + (System.currentTimeMillis() - start) + " ms");
        return JsonViewUtil.getModelMap(books);
    }

    @RequestMapping(value = "/getBookByTitle")
    public String getBookByTitle(HttpServletRequest request, HttpServletResponse response, Book book) {

        List<Book> allBooks = this.bookService.getBooksByTile(book.getTitle());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Book title is: <b>"+jsonArray.toString()+"</b>");
        return  "showBook" ;
    }

    @RequestMapping(value = "/getBooksBySql")
    public String getBooksBySql(HttpServletRequest request, HttpServletResponse response, Book book) {

        List<Book> allBooks = this.bookService.getBooksBySql(book.getTitle());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Book title is: <b>"+jsonArray.toString()+"</b>");
        return  "showBook" ;
    }

}
