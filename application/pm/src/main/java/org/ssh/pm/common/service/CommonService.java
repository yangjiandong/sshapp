package org.ssh.pm.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springside.modules.utils.UtilDateTime;
import org.springside.modules.utils.spring.SpringContextHolder;
import org.ssh.pm.common.dao.CommonJdbcDao;
import org.ssh.pm.common.web.UserSession;
import org.ssh.pm.utils.SysConfigData;

@Component
public class CommonService {

	@Autowired
	CommonJdbcDao commonJdbcDao;
	
    public Map<String, String> getApplicationInfos() {
        SysConfigData manager = (SysConfigData) SpringContextHolder.getBean("sysConfigData");

        Map<String, String> alls = new HashMap<String, String>();

        alls.put("version", manager.getVersion());
        alls.put("application_name", manager.getProductName());
        alls.put("run_mode", manager.getRunMode());
        alls.put("vendor", manager.getVendor());
        alls.put("copyright", manager.getYear() + "-" + StringUtils.substring(UtilDateTime.nowDateString("yyyy.MM.dd"),0,4) + " 版权所有 " + manager.getVendor());
        alls.put("website", manager.getWebsite());
        // 当前用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        UserSession u = (UserSession) request.getSession().getAttribute("userSession");
        alls.put("user_name", (u == null) ? "" : u.getAccount().getUserName());

        return alls;
    }
    
	//@Transactional
    //配合 <property name="defaultAutoCommit" value="false" />
    public void insertDemo(){
    	commonJdbcDao.insertDemo();
    }
	
	public void insertDemoInTransaction(){
    	commonJdbcDao.insertDemoInTransaction();
    }
}
