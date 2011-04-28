package org.springside.modules.orm.grid;

public class GridColumnMeta {

    private String name;

    private String header;

    private boolean hidden;

    private boolean sortable;

    private String type;

    private String align;

    // string
    // int
    // float
    // bool
    // date //type: 'date', dateFormat: 'Y.m.d'

    private boolean fixed;

    private int width;

    public void setType(String obj) {
        this.type = obj;
    }

    public String getType() {
        return type;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setHeader(String obj) {
        this.header = obj;
    }

    public String getHeader() {
        return header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

} // EOP
