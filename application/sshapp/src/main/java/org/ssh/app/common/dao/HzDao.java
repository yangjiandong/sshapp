package org.ssh.app.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.common.entity.Hz;

@Repository("hzDao")
public class HzDao extends HibernateDao<Hz, Long> {
    private static final String COUNTS = "select count(u) from Hz u";
    final int batchSize = 1000;

    public Long getHzCount() {
        return findUnique(COUNTS);
    }

    public int batchCreate(final List<Hz> entityList) {
        // in the DAO
        //Session session = getSession();
        //Transaction tx = session.beginTransaction();

        int insertedCount = 0;
        for (int i = 0; i < entityList.size(); ++i) {
            save(entityList.get(i));
            if (++insertedCount % batchSize == 0) {
                flushAndClear();
                //session.flush();
                //session.clear();
            }
        }
        flushAndClear();

        //tx.commit();
        //session.clear();
        return insertedCount;
    }

    protected void flushAndClear() {
        if (getSession().isDirty()) {
            getSession().flush();
            getSession().clear();
        }
    }

    public Map<String, String> getMemo(String hzStr) {
        Map<String, String> map = new HashMap<String, String>();
        String oneS = null;
        StringBuffer bfWb = new StringBuffer();
        StringBuffer bfPy = new StringBuffer();
        Hz hz = null;
        for (int i = 0; i < hzStr.length(); i++) {
            oneS = hzStr.substring(i, i + 1);
            hz = this.findOne("from " + Hz.class.getName()
                    + " where hz=?", oneS);
            if (hz != null) {
                bfWb.append(hz.getWb().substring(0, 1));
                bfPy.append(hz.getPy().substring(0, 1));
            }
        }

        String py = bfPy.toString();
        String wb = bfWb.toString();

        if (py.length() > 10) {
            py = py.substring(0, 10);
        }

        if (wb.length() > 10) {
            wb = wb.substring(0, 10);
        }

        map.put("py", py);
        map.put("wb", wb);

        return map;
    }
}
