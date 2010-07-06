package org.ssh.app.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.common.ResourceTypeEnum;
import org.ssh.app.common.dao.ResourceTypeDao;
import org.ssh.app.common.entity.ResourceType;
import org.ssh.app.orm.hibernate.EntityService;

@Service("resourceTypeService")
@Transactional
public class ResourceTypeService extends EntityService<ResourceType, Long> {
    private ResourceTypeDao resourceTypeDao;

    @Autowired
    public void setResourceTypeDao(ResourceTypeDao resourceTypeDao) {
        this.resourceTypeDao = resourceTypeDao;
    }

    @Override
    protected ResourceTypeDao getEntityDao() {
        return this.resourceTypeDao;
    }

    public void initData() {
        Long l = resourceTypeDao.findUnique(" select count(u) from " + ResourceType.class.getName()
                + " u");

        if (l == null || l.longValue() == 0) {

            ResourceType resource = new ResourceType();
            resource.setOid(new Long(1));
            resource.setTypeName(ResourceTypeEnum.SUBSYSTEM.getValue());
            resourceTypeDao.save(resource);

            resource = new ResourceType();
            resource.setOid(new Long(2));
            resource.setTypeName(ResourceTypeEnum.SUBMENU.getValue());
            resourceTypeDao.save(resource);
        }
    }
}
