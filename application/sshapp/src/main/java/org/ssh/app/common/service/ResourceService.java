package org.ssh.app.common.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import org.ssh.app.common.ResourceTypeEnum;
import org.ssh.app.common.dao.ResourceDao;
import org.ssh.app.common.dao.ResourceTypeDao;
import org.ssh.app.common.entity.Resource;
import org.ssh.app.common.entity.ResourceType;
import org.ssh.app.orm.hibernate.EntityService;

@Service("resourcesService")
@Transactional
public class ResourceService extends EntityService<Resource, Long> {
    private ResourceTypeDao resourceTypeDao;
    private ResourceDao resourcesDao;

    @Autowired
    public void setResourcesDao(ResourceDao resourceDao) {
        this.resourcesDao = resourceDao;
    }

    @Autowired
    public void setResourceTypeDao(ResourceTypeDao resourceTypeDao) {
        this.resourceTypeDao = resourceTypeDao;
    }

    @Override
    protected ResourceDao getEntityDao() {
        return this.resourcesDao;
    }

    /**
     * 获取当前用户授权角色对应的子系统资源
     */
    @Transactional(readOnly = true)
    public List<Resource> loadGrantedSubSystemsByRole(List<String> roleId) {
        String hql = "";
        Map<String, Object> values = new HashMap<String, Object>();

        hql = "select a from Resources a,RoleResource b "
            + "where a.parentId =0 and b.roleId in (:roleId) and "
            + "b.resourceId = a.oid and a.resourceType.typeName = :typeName "
            + "order by a.orderNo";
        values.put("typeName", ResourceTypeEnum.SUBSYSTEM.getValue());
        values.put("roleId", roleId);

        return resourcesDao.find(hql, values);
    }

    /**
     * 获取当前用户授权的子系统资源
     */
    @Transactional(readOnly = true)
    public List<Resource> loadGrantedSubSystems(List<String> roleIds,
        String loginName) {
        String hql = "";
        Map<String, Object> values = new HashMap<String, Object>();

        if (loginName.equals("admin")) {
            // 系统管理员
            hql = "from Resource where resourceType.typeName = :typeName and parentId =0 order by orderNo";
            values.put("typeName", ResourceTypeEnum.SUBSYSTEM.getValue());
        } else {
            hql = "select a from Resource a,RoleResource b "
                + "where a.parentId =0 and  b.roleId in (:roleIds) and "
                + "b.resourceId = a.oid and "
                + "a.resourceType.typeName = :typeName "
                + "order by a.orderNo";
            values.put("typeName", ResourceTypeEnum.SUBSYSTEM.getValue());
            values.put("roleIds", roleIds);
        }

        return resourcesDao.find(hql, values);
    }

    /**
     * 获取当前用户授权的菜单系统资源
     */
    @Transactional(readOnly = true)
    public List<Resource> loadGrantedMenus(Long parentId, String userId,
        String loginName) {
        String hql = "";
        Map<String, Object> values = new HashMap<String, Object>();

        if (loginName.equals("admin")) {
            hql = "from Resource where parentId = :parentId order by orderNo";
            values.put("parentId", parentId);
        } else {
            hql = "select a from Resource a,RoleResource b,UserRole c "
                + "where c.userId = :userId and "
                + "c.roleId = b.roleId and b.resourceId = a.oid and "
                + "a.parentId = :parentId order by a.orderNo";
            values.put("parentId", parentId);
            values.put("userId", userId);
        }

        return resourcesDao.find(hql, values);
    }

    /**
     * 获取类型为子系统的系统资源
     */
    @Transactional(readOnly = true)
    public List<Resource> loadSubSystems() {
        return resourcesDao.find("from Resource where parentId =0 and resourceType.typeName = ? order by orderNo",
            ResourceTypeEnum.SUBSYSTEM.getValue());
    }

    /**
     * 获取指定oid的子资源信息
     */
    @Transactional(readOnly = true)
    public List<Resource> getChildrenResource(Long parentId) {
        return resourcesDao.find("from Resource where parentId = ? order by orderNo",
            parentId);
    }

    public void initData() {
        logger.debug("开始装载资源初始数据");

        File resourcetxt = new File(this.getClass()
                                        .getResource("/data/resource.txt")
                                        .getFile());

        try {
            FileInputStream fis = new FileInputStream(resourcetxt);
            String thisLine;

            DataInputStream myInput = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                        myInput, "UTF-8"));

            String s0;
            Resource u;
            String id;
            String parentId;

            int line = 1;

            while ((thisLine = br.readLine()) != null) {
                if (line == 1) {
                    line++;

                    continue;
                }

                // 空行
                if (thisLine.trim().equals("")) {
                    continue;
                }

                String[] star = thisLine.split(",");
                s0 = star[1].trim();

                if (s0.equals("")) {
                    continue;
                }

                parentId = star[4].trim(); // "parentId"

                u = this.resourcesDao.findUnique("from "
                        + Resource.class.getName()
                        + " where resourceName=? and parentId =? ", s0,
                        Long.valueOf(parentId));

                if (u == null) {
                    u = new Resource();
                    id = star[0].trim(); // "resourceId"

                    u.setId(new Long(id));
                    u.setResourceName(s0);
                    u.setUrl(star[2].trim());
                    u.setOrderNo(new Long(star[6].trim()));
                    u.setLeaf(!star[3].trim().equals("0"));

                    ResourceType type = resourceTypeDao.get(new Long(
                                star[5].trim()));
                    u.setResourceType(type);
                    u.setParentId(new Long(parentId));

                    resourcesDao.save(u);
                } else {
                    u.setUrl(star[2].trim());
                    u.setOrderNo(new Long(star[6].trim()));
                    resourcesDao.save(u);
                }
            }
        } catch (Exception e) {
            logger.error("初始资源数据出错:" + e);
            throw new ServiceException("初始资源数据时，服务器发生异常");
        } finally {
        }
    }
}
