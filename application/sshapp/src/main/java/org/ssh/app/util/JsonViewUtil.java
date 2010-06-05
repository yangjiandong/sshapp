package org.ssh.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class JsonViewUtil {
    /**
     * Generates modelMap to return in the modelAndView
     * @param contacts
     * @return
     */
    public static ModelAndView getModelMap(List<? extends Object> data){

        Map<String,Object> modelMap = new HashMap<String,Object>(3);
        modelMap.put("总计", data.size());
        modelMap.put("data", data);
        modelMap.put("success", true);

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
}
