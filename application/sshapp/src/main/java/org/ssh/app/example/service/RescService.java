package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.RescDao;
import org.ssh.app.example.entity.Resc;

@Component
@Transactional
public class RescService {

    private static Logger logger = LoggerFactory.getLogger(RescService.class);

    @Autowired
    private RescDao rescDao;

    public void initData() {
        if (this.rescDao.getBookCount().longValue() != 0) {
            return;
        }

        Resc b = new Resc();
        b.setName("");
        b.setResType("URL");
        b.setResString("/book/admin.jsp");
        b.setPriority(1);
        b.setDescn("");
        rescDao.save(b);

        b = new Resc();
        b.setName("");
        b.setResType("URL");
        b.setResString("/book/**");
        b.setPriority(2);
        b.setDescn("");
        rescDao.save(b);
    }
}
