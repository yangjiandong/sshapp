package org.ssh.app.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ssh.app.common.entity.Resource;
import org.ssh.app.common.service.ResourceService;
import org.ssh.app.util.JsonViewUtil;

@Controller
@RequestMapping("/resource")
public class ResourcesController {
    @Autowired
    private ResourceService resourcesService;

    @RequestMapping(value = "/query")
    public void queryOperations(ModelMap map, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<Resource> resources = null;
        String oid = request.getParameter("id");
        if (StringUtils.isNotEmpty(oid) && !oid.equals("0")) {
            resources = resourcesService.getChildrenResource(Long.valueOf(oid));
        } else {
            resources = resourcesService.loadSubSystems();
        }

        JsonViewUtil.buildJSONResponse(response, resources);
    }

    @RequestMapping(value = "/delete")
    public void deleteOperations(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String oid = request.getParameter("oid");
            resourcesService.delete(Long.valueOf(oid));
            map.put("success", true);
            map.put("message", "");
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "删除时服务器端发生异常，删除失败！");
        }

        JsonViewUtil.buildCustomJSONDataResponse(response, map);
    }

    // @RequestMapping(value="/save")
    // public void saveOperations(HttpServletRequest request,
    // HttpServletResponse response) throws Exception {
    // Map<String, Object> map = new HashMap<String, Object>();
    // String entity = request.getParameter("entity");
    //
    // try {
    // Resource resource = resourcesService.save(entity);
    // map.put("success", true);
    // map.put("data", resource);
    // map.put("message", "");
    // } catch (Exception e) {
    // map.put("success", false);
    // map.put("message", "保存时服务器端发生异常，保存失败！");
    // }
    //
    // JsonViewUtil.buildCustomJSONDataResponse(response, map);
    // }

}
