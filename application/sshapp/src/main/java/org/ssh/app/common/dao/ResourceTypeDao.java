package org.ssh.app.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.common.entity.ResourceType;

@Repository("resourceTypeDao")
public class ResourceTypeDao extends HibernateDao<ResourceType, Long> {

}
