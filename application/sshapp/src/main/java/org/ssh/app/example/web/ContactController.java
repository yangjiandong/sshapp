package org.ssh.app.example.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.ssh.app.common.service.HzService;
import org.ssh.app.example.entity.Contact;
import org.ssh.app.example.service.ContactService;
import org.ssh.app.util.JsonViewUtil;

@Controller
@RequestMapping("/book")
public class ContactController extends MultiActionController {
    private static Logger logger = LoggerFactory
            .getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private HzService hzService;

    @RequestMapping(value = "/getContacts", method = RequestMethod.GET)
    public ModelAndView view(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        logger.info("json,contacts list...");

        try {
            List<Contact> books = contactService.getAlls();
            return JsonViewUtil.getModelMap(books);

        } catch (Exception e) {
            return JsonViewUtil
                    .getModelMapError("Error trying to retrieve contacts.");
        }
    }

    @RequestMapping(value = "/create")
    public ModelAndView create(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        try {
            Object data = request.getParameter("data");
            List<Contact> contacts = contactService.create(data);
            return JsonViewUtil.getModelMap(contacts);

        } catch (Exception e) {
            return JsonViewUtil
                    .getModelMapError("Error trying to create contact.");
        }
    }

    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            Object data = request.getParameter("data");
            List<Contact> contacts = contactService.update(data);
            return JsonViewUtil.getModelMap(contacts);

        } catch (Exception e) {
            return JsonViewUtil
                    .getModelMapError("Error trying to update contact.");
        }
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        try {
            String data = request.getParameter("data");
            contactService.delete(data);

            Map<String, Object> modelMap = new HashMap<String, Object>(3);
            modelMap.put("success", true);

            return new ModelAndView("jsonView", modelMap);

        } catch (Exception e) {

            return JsonViewUtil
                    .getModelMapError("Error trying to delete contact.");
        }
    }

    @RequestMapping(value = "/getContactByProjections")
    public String getContactByProjections(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List<Contact> allBooks = this.contactService.getContactByProjections(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Contact title is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }

    @RequestMapping(value = "/getContactByDetachedCriteria")
    public String getContactByDetachedCriteria(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List<Contact> allBooks = this.contactService.getContactByDetachedCriteria(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Contact title is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }

    @RequestMapping(value = "/getContactByDetachedCriteria2")
    public String getContactByDetachedCriteria2(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List<Contact> allBooks = this.contactService.getContactByDetachedCriteria2(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Contact title is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }

    @RequestMapping(value = "/getContactByNaturalId")
    public String getContactByNaturalId(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List<Contact> allBooks = this.contactService.getContactByNaturalId(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Contact title is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }

    @RequestMapping(value = "/getContactBySql")
    public String getContactBySql(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List<Contact> allBooks = this.contactService.getContactBySql(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "You Input Contact title is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }

    @RequestMapping(value = "/getHzMemeo")
    public String getHzMemo(HttpServletRequest request, HttpServletResponse response, Contact book) {

        Map<String, String> allBooks = this.hzService.getMemo(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "汉字助记符 is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }
}
