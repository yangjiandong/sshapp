package org.ssh.app.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.common.entity.Resource;

@Repository("resourcesDao")
public class ResourceDao extends HibernateDao<Resource, Long> {

}
