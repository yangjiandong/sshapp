package org.springside.modules.orm.grid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewField {

    /* Grid的字段标题 */
    String header() default "";

    /* Grid字段类型 */
    // auto
    // string
    // int
    // float
    // boolean
    // date
    String type() default "auto";

    /* Grid的字段是否隐藏 */
    boolean hidden() default false;

    /* Grid的字段是否能排序 */
    boolean sortable() default true;

    /* Grid的字段列默认宽度 */
    int width() default 100;

    /* Grid的字段列的宽度是否固定不变 */
    boolean fixed() default false;

    /* Grid的字段对齐方式 */
    // left
    // right
    // middle
    String align() default "left";

    //虚拟字段指定排序字段
    String sortByField() default "";

    //是否为金额字段,进行前台格式化渲染
    boolean isMoney() default false;
}
