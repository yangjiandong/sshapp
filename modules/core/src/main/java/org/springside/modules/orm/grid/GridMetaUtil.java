package org.springside.modules.orm.grid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GridMetaUtil {

    /**
     * 获取Grid的原数据,返回JSON格式的数据
     */

    public static List<GridColumnMeta> getGridMeta(String className) {
        List<GridColumnMeta> meta = new ArrayList<GridColumnMeta>();

        ViewField vf = null;
        GridColumnMeta col = null;

        try {
            Class<?> c = Class.forName(className);
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(ViewField.class)) {
                    vf = field.getAnnotation(ViewField.class);
                    col = new GridColumnMeta();
                    col.setName(field.getName());
                    col.setHeader(vf.header());
                    col.setHidden(vf.hidden());
                    col.setType(vf.type());
                    col.setSortable(vf.sortable());
                    col.setFixed(vf.fixed());
                    col.setWidth(vf.width());
                    col.setAlign(vf.align());
                    col.setSortByField(vf.sortByField());
                    col.setIsMoney(vf.isMoney());
                    meta.add(col);
                }
            }

            Class<?> superClass = c.getSuperclass();
            while (superClass != Object.class) {
                Field[] fields2 = superClass.getDeclaredFields();
                //Field[] fields = c.getFields();
                for (Field field : fields2) {
                    if (field.isAnnotationPresent(ViewField.class)) {
                        vf = field.getAnnotation(ViewField.class);
                        col = new GridColumnMeta();
                        col.setName(field.getName());
                        col.setHeader(vf.header());
                        col.setHidden(vf.hidden());
                        col.setType(vf.type());
                        col.setSortable(vf.sortable());
                        col.setFixed(vf.fixed());
                        col.setWidth(vf.width());
                        col.setAlign(vf.align());
                        col.setSortByField(vf.sortByField());
                        col.setIsMoney(vf.isMoney());
                        meta.add(col);
                    }
                }
                superClass = superClass.getSuperclass();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        return meta;

    }

    /**
     * 从DBObject类中获取网格元数据信息，返回GridColumnMeta数组
     */
    public static GridColumnMeta[] getGridColumnMeta(String className) {
        Class<?> c;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        Field[] fields = c.getDeclaredFields();
        int count = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(ViewField.class)) {
                count++;
            }
        }
        if (count == 0)
            return null;

        GridColumnMeta[] meta = new GridColumnMeta[count];
        int m = 0;
        for (int i = 0; i < fields.length; i++) {
            Field fld = fields[i];
            if (fld.isAnnotationPresent(ViewField.class)) {
                ViewField vf = fld.getAnnotation(ViewField.class);
                GridColumnMeta col = new GridColumnMeta();
                col.setName(fld.getName());
                col.setHeader(vf.header());
                col.setHidden(vf.hidden());
                col.setType(vf.type());
                col.setSortable(vf.sortable());
                col.setFixed(vf.fixed());
                col.setWidth(vf.width());
                col.setAlign(vf.align());
                col.setSortByField(vf.sortByField());
                col.setIsMoney(vf.isMoney());

                meta[m++] = col;
            }
        }
        return meta;
    }
}
