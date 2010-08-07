package org.ssh.app.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ssh.app.common.service.AccountManager;
import org.ssh.app.util.JsonViewUtil;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "person/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, ModelMap modelMap) {
        Assert.notNull(id, "Identifiler must be privided.");
        modelMap.addAttribute("person", accountManager.getLoadedUser(id));
        return "person/show";
    }

    //error=1
    //用户或密码不正确
    @RequestMapping(value="/loginerror1")
    public void login_error1(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        map.put("message", "用户名或密码不正确");

        JsonViewUtil.buildCustomJSONDataResponse(response, map);
    }

    //登录成功
    @RequestMapping(value="/loginsuccess")
    public void login_success(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        map.put("message", "o");

        JsonViewUtil.buildCustomJSONDataResponse(response, map);
    }

    //TODO
    //验证码
    private void validateCaptcha(HttpServletRequest request, BindException errors) {
        String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //String kaptchaReceived = userForm.getKaptcha();
        String kaptchaReceived = request.getParameter("kaptcha");

        if (logger.isDebugEnabled()) {
            logger.debug("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
        }

        if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            logger.error("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
            errors.rejectValue(null, "error.invalidKaptcha", "invalidKaptcha");

        }
    }
}
