package org.ssh.app.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ssh.app.common.service.AccountManager;

//http://stsmedia.net/spring-finance-part-2-spring-mvc-spring-30-rest-integration/
@Controller
@RequestMapping("/person/**")
public class UserController {
    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value="person/{id}", method=RequestMethod.GET)
    public String show(@PathVariable("id") String id, ModelMap modelMap) {
        Assert.notNull(id, "Identifiler must be privided.");
        modelMap.addAttribute("person", accountManager.getLoadedUser(id));
        return "person/show";
    }
}
