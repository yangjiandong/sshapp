package org.ssh.app.common.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;
import org.ssh.app.common.entity.Resource;
import org.ssh.app.common.entity.Role;
import org.ssh.app.common.service.ResourceService;
import org.ssh.app.security.OperatorDetails;
import org.ssh.app.util.JsonViewUtil;

@Controller
@RequestMapping("/resource")
public class ResourcesController {
    private static Logger logger = LoggerFactory.getLogger(ResourcesController.class);

    @Autowired
    private ResourceService resourcesService;

    /**
     * 加载子系统的菜单项
     *
     * @throws Exception
     */
    @RequestMapping("/loadSubSystem")
    public void loadSubSystem(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        OperatorDetails u = SpringSecurityUtils.getCurrentUser();
        //String uid = u.getUserId();
        String uLoginName = u.getLoginName();
        List<Role>roles=u.getRoleList();
        List<String>roleIds=new ArrayList<String>();
        for (Role role : roles) {
            roleIds.add(role.getId());
        }
        List<Resource> subSystem = resourcesService.loadGrantedSubSystems(roleIds, uLoginName);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("subSystems", subSystem);
        JsonViewUtil.buildCustomJSONDataResponse(response, map);
    }

    /**
     * 加载子系统的菜单项
     *
     * @throws Exception
     */
    @RequestMapping("/loadMenu")
    public void loadMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Resource> menus = null;
        String module = request.getParameter("module");

        OperatorDetails u = SpringSecurityUtils.getCurrentUser();
        String uid = u.getUserId();
        String uLoginName = u.getLoginName();

        if (StringUtils.isNotEmpty(module)) {
            String resourceId = request.getParameter("resourceId");
            if (!StringUtils.isEmpty(resourceId) && !resourceId.equals("0")) {
                menus = resourcesService.loadGrantedMenus(Long.valueOf(resourceId), uid, uLoginName);
            }else{
                menus = resourcesService.loadGrantedMenus(Long.valueOf(module), uid, uLoginName);
            }
        }

        // JSONResponseUtil.buildJSONResponse(response, menus);
        StringBuffer sb = new StringBuffer();
        sb.append("");

        //text,id,cls,leaf,guiID
        //手工处理
        //输出格式为：{"text" :"资源管理" ,"id" :"102" ,"leaf" :false ,"guiID" :"" }
        if (menus != null && !menus.isEmpty()) {
            //boolean isFirst = true;
            int i = 0;
            //int last = menus.size();
            for (Resource res : menus) {
                if (i == 0) {
                    sb.append("[{\"text\" :\"" + res.getResourceName() + "\" ,\"id\" :\""
                            + res.getId().toString() + "\" ,\"leaf\" :"
                            + res.isLeaf() + " ,\"guiID\" :\""
                            + res.getUrl()
                            + "\" } ");
                } else {
                    sb.append(",{\"text\" :\"" + res.getResourceName()
                            + "\" ,\"id\" :\""
                            + res.getId().toString()
                            + "\" ,\"leaf\" :"
                            + res.isLeaf() + " ,\"guiID\" :\""
                            + res.getUrl()
                            + "\" } ");
                }
                i++;
            }
            if (!sb.equals("")) sb.append("]");
        }

        logger.info(sb.toString());

        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(sb.toString());
    }

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
