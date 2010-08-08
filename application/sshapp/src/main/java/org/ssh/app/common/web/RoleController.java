package org.ssh.app.common.web;

import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.PropertyFilter.MatchType;

import org.ssh.app.common.entity.Role;
import org.ssh.app.common.service.RoleManager;
import org.ssh.app.util.JsonViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/role")
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(ResourcesController.class);
    @Autowired
    private RoleManager roleManager;

    @RequestMapping("/list")
    public void queryRoles(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.debug("tst..");

        Page<Role> page = new Page<Role>(request);
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

        String name = request.getParameter("name");

        if (!StringUtils.isEmpty(name)) {
            PropertyFilter filter = new PropertyFilter("name", MatchType.LIKE, name);
            filters.add(filter);
        }

        Page<Role> data = this.roleManager.search(page, filters);
        JsonViewUtil.buildJSONDataResponse(response, data.getResult(), (long) data.getTotalCount());
    }
}
