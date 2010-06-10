package com.ekingsoft.core.orm;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ekingsoft.core.utils.UtilDateTime;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 *
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 *
 * @author calvin
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener {

    private static Logger logger = LoggerFactory.getLogger(AuditListener.class);

    public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
        Object object = event.getObject();

        //如果对象是AuditableEntity子类,添加审计信息.
        if (object instanceof AuditableEntity) {
            AuditableEntity entity = (AuditableEntity) object;
            //String loginName = SpringSecurityUtils.getCurrentUserName();
            String loginName = "tst";

            if (entity.getId() == null) {
                //创建新对象
                entity.setCreateTime(UtilDateTime.nowDateString());
                entity.setCreateBy(loginName);
            } else {
                //修改旧对象
                entity.setLastModifyTime(UtilDateTime.nowDateString());
                entity.setLastModifyBy(loginName);

                logger.info("{}对象(ID:{}) 被 {} 在 {} 修改", new Object[] { event.getEntityName(), entity.getId(),
                        loginName, new Date() });
            }
        }
    }
}
