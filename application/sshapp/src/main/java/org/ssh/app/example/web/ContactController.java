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
import org.ssh.app.common.entity.User;
import org.ssh.app.common.service.AccountManager;
import org.ssh.app.common.service.HzService;
import org.ssh.app.example.entity.Contact;
import org.ssh.app.example.entity.UserVO;
import org.ssh.app.example.service.ContactService;
import org.ssh.app.util.JsonViewUtil;
import org.ssh.app.util.leona.JsonUtils;
import org.ssh.app.util.leona.JsonUtils.Bean;

@Controller
@RequestMapping("/book")
public class ContactController extends MultiActionController {
    private static Logger logger = LoggerFactory
            .getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private HzService hzService;

    @Autowired
    private AccountManager accountManager;

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

//    public PrintWriter getWriter() throws IOException {
//        return ServletActionContext.getResponse().getWriter();
//    }

    @RequestMapping(value = "/getContactBySql")
    public String getContactBySql(HttpServletRequest request, HttpServletResponse response, Contact book) {

        List allBooks = this.contactService.getContactBySql(book.getName());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("subSystems", allBooks);
        try{
        JsonViewUtil.buildCustomJSONDataResponse(response, map);
        }catch (Exception e) {
            // TODO: handle exception
        }

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

    @RequestMapping(value = "/getUserVO")
    public String getUserVO(HttpServletRequest request, HttpServletResponse response, Contact book) {

      List<User>list = accountManager.getAllUserWithRole();
      try{
      Bean bean = new Bean(true, "已经登陆", list);
      JsonUtils.write(bean, response.getWriter(),
          new String[] {
              "parent", "roleList", "hibernateLazyInitializer",
              "handler", "checked"
          }, "yyyy.MM.dd");

      //DEBUG: org.ssh.app.util.leona.JsonUtils - "{"info":[{"createBy":"初始化","createTime":"2010.08.14 22:41:04","email":"ad
      //in@gmail.com","id":"00012a710bfb8a02","lastModifyBy":"","lastModifyTime":"","loginName":"admin","name":"管理员","plainP
      //ssword":"123","roleNames":"admin","shaPassword":"40bd001563085fc35165329ea1ff5c5ecbdbbeef","status":"enabled","version"
      //0}],"msg":"已经登陆","success":true}"

      JsonUtils.write(bean, response.getWriter(),
              new String[] {
                  "handler", "checked"
              }, "yyyy.MM.dd");
      //DEBUG: org.ssh.app.util.leona.JsonUtils - "{"info":[{"createBy":"初始化","createTime":"2010.08.14 22:41:04","email":"adm
      //in@gmail.com","id":"00012a710bfb8a02","lastModifyBy":"","lastModifyTime":"","loginName":"admin","name":"管理员","plainPa
      //ssword":"123","roleList":[{"desc":"系统管理员角色","id":"00012a710bfb6b01","name":"admin"}],"roleNames":"admin","shaPass
      //word":"40bd001563085fc35165329ea1ff5c5ecbdbbeef","status":"enabled","version":0}],"msg":"已经登陆","success":true}"

      }catch (Exception e) {
          // TODO: handle exception
      }

      UserVO allBooks = this.contactService.getUserVO(book.getName());
        JSONArray jsonArray = JSONArray.fromObject(allBooks);
        request.setAttribute("message", "userVO is: <b>"+jsonArray.toString()+"</b>");
        return  "showContact" ;
    }


}
