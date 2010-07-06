package org.ssh.app.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class JsonViewUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonViewUtil.class);

    /**
     * Generates modelMap to return in the modelAndView
     * @param contacts
     * @return
     */
    public static ModelAndView getModelMap(List<? extends Object> data){

        Map<String,Object> modelMap = new HashMap<String,Object>(3);
        modelMap.put("total", data.size());
        modelMap.put("data", data);
        modelMap.put("success", true);

        //logger.info("json,getModelMap...");

        return new ModelAndView("jsonView", modelMap);
    }

    /**
     * Generates modelMap to return in the modelAndView in case
     * of exception
     * @param msg message
     * @return
     */
    public static ModelAndView getModelMapError(String msg){

        Map<String,Object> modelMap = new HashMap<String,Object>(2);
        modelMap.put("message", msg);
        modelMap.put("success", false);

        return new ModelAndView("jsonView",modelMap);
    }

    /**
     * 响应生成Grid格式的JSON数据集 <br>
     * 'totalCount':Grid的JSONReader的totalProperty属性必须设置为"totalCount" <br>
     * 'rows':Grid的JSONReader的root属性必须设置为"rows"
     */
    public static void buildJSONDataResponse(HttpServletResponse response,
            String jsonString, Long total) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"totalCount\":" + total + ",\"rows\":");
        sb.append(jsonString);
        sb.append("}");

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
    }

    /**
     * 响应生成Grid格式的JSON数据集 <br>
     * 'totalCount':Grid的JSONReader的totalProperty属性必须设置为"totalCount" <br>
     * 'rows':Grid的JSONReader的root属性必须设置为"rows"
     */
    public static void buildJSONDataResponse(HttpServletResponse response,
            List<? extends Object> data, Long total) throws Exception {
//        JsonConfig cfg = new JsonConfig();
//
//        cfg.setJsonPropertyFilter(new PropertyFilter() {
//            public boolean apply(Object arg0, String arg1, Object arg2) {
//                System.out.println("oo:" + arg1);
//
//                if (arg1.equals("childs") || arg1.equals("parent")) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//
//        cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
//        //cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
//        //cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());

        JSONArray jsonArray = JSONArray.fromObject(data);//, cfg);

        StringBuffer sb = new StringBuffer();
        sb.append("{\"totalCount\":" + total + ",\"rows\":");
        sb.append(jsonArray.toString());
        sb.append("}");

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
    }

    //http://hjg1988.javaeye.com/blog/561368
    //jackson 性能
    public static void buildJacksonDataResponse(HttpServletResponse response,
            List<? extends Object> data, Long total) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        StringBuffer sb = new StringBuffer();
        sb.append("{\"totalCount\":" + total + ",\"rows\":");
        //sb.append(jsonArray.toString());
        sb.append("}");

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
    }


    /**
     * 响应生成Grid格式的JSON数据集 <br>
     * JSON对象的属性可以根据需要自定义设置，通过Map添加键值即可<br>
     * 如: Map<String,Object> map = new HashMap<String,Object>();<br>
     * map.put("totalCount",125);<br>
     * map.put("success",true);<br>
     * map.put("data",list);
     */
    public static void buildCustomJSONDataResponse(
            HttpServletResponse response, Map<String, ? extends Object> data)
            throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(data);
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        logger.info(jsonObject.toString());
        out.write(jsonObject.toString());
    }

    /**
     * 响应非Grid形式的JSON数据集的请求
     */
    public static void buildJSONResponse(HttpServletResponse response,
            List<? extends Object> data) throws Exception {
        JSONArray jsonArray = JSONArray.fromObject(data);
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(jsonArray.toString());
    }

    /**
     * 响应非Grid形式的JSON数据集的请求
     */
    public static void buildJSONObjectResponse(HttpServletResponse response,
            JSONObject data) throws Exception {
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(data.toString());
    }


}
