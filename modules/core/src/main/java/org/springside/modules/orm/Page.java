/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * $Id: Page.java 838 2010-01-06 13:47:36Z calvinxiu $
 */
package org.springside.modules.orm;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springside.modules.orm.grid.GridColumnMeta;

import com.google.common.collect.Lists;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装.
 * 注意所有序号从1开始.
 *
 * @param <T> Page中记录的类型.
 *
 * @author calvin
 */
public class Page<T> {
    //-- 公共变量 --//
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public static final String GRID_PARAM_START = "start";
    public static final String GRID_PARAM_PAGESIZE = "limit";
    public static final String GRID_PARAM_ORDERBY = "sort";
    public static final String GRID_PARAM_ORDERDIR = "dir";

    public static final String GRID_PARAM_EXPORT = "export";
    public static final String GRID_PARAM_EXPORT_FORMAT = "_p_exp_format";
    public static final String GRID_PARAM_FIELD_LIST = "fieldsList";

    //-- 分页参数 --//
    protected int pageNo = 1;
    private int start = 0;
    private int pageSize = 20;

    protected String orderBy = null;
    protected String order = null;
    private String orderDir = "";

    private boolean isExport = false;

    private String expFormat = "xls";
    private String[] fieldList;

    private HttpServletRequest req;

    protected boolean autoCount = true;

    //-- 返回结果 --//
    protected List<T> result = Lists.newArrayList();
    protected long totalCount = -1;

    //-- 构造函数 --//
    public Page() {
        super();
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(HttpServletRequest request) {
        this.req = request;

        String exportStr = request.getParameter(GRID_PARAM_EXPORT);
        this.setExport(false);
        if (!StringUtils.isEmpty(exportStr) && exportStr.equalsIgnoreCase("y")) {
            this.setExport(true);

            String formatStr = request.getParameter(GRID_PARAM_EXPORT_FORMAT);
            if (!StringUtils.isEmpty(formatStr)) {
                this.setExpFormat(formatStr);
            }

            String fieldListStr = request.getParameter(GRID_PARAM_FIELD_LIST);
            if (!StringUtils.isEmpty(fieldListStr)) {
                this.setFieldList(fieldListStr.split("-"));
            }

        }

        String start = request.getParameter(GRID_PARAM_START);
        this.start = StringUtils.isEmpty(start) ? 0 : Integer.valueOf(start);

        String limit = request.getParameter(GRID_PARAM_PAGESIZE);
        if (StringUtils.isEmpty(limit)) {
            limit = (String)request.getAttribute(GRID_PARAM_PAGESIZE);
        }

        if (StringUtils.isEmpty(limit)) {
            if (this.isExport)
                this.pageSize = 1000;
            else
                this.pageSize = 20;
        } else {
            this.pageSize = Integer.valueOf(limit);
        }

        if (this.start == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = this.start / this.pageSize + 1;
        }

        String orderStr = request.getParameter(GRID_PARAM_ORDERBY);
        if (!StringUtils.isEmpty(orderStr)) {
            this.orderBy = orderStr;
        }

        String orderDirStr = request.getParameter(GRID_PARAM_ORDERDIR);
        if (!StringUtils.isEmpty(orderDirStr)) {
            this.orderDir = orderDirStr;
        }

    }

    //-- 访问查询参数函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    public Page<T> pageNo(final int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    /**
     * 获得当前页的第一条记录序号，从0开始,默认为0.
     */
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 获得每页的记录数量,默认为20.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为20.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;

        if (pageSize < 1) {
            this.pageSize = 20;
        }
    }

    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 获得排序字段,无默认值.多个排序字段时用','分隔.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序字段,多个排序字段时用','分隔.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public Page<T> orderBy(final String theOrderBy) {
        setOrderBy(theOrderBy);
        return this;
    }

    /**
     * 获得排序方向.
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方式向.
     *
     * @param order 可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setOrder(final String order) {
        //检查order字符串的合法值
        String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }

        this.order = StringUtils.lowerCase(order);
    }

    public Page<T> order(final String theOrder) {
        setOrder(theOrder);
        return this;
    }

    /**
     * 是否已设置排序字段,无默认值.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    //-- 访问查询结果函数 --//

    /**
     * 取得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    public boolean isExport() {
        return isExport;
    }

    public void setExport(boolean isExport) {
        this.isExport = isExport;
    }

    public String[] getFieldList() {
        return fieldList;
    }

    public void setFieldList(String[] fieldList) {
        this.fieldList = fieldList;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    /**
     * 获取导出格式
     */
    public String getExpFormat() {
        return expFormat;
    }

    /**
     * 设置导出格式
     */
    public void setExpFormat(String expFormat) {
        this.expFormat = expFormat;
    }

    /**
     * 获取当前列表显示的字段
     */
    public String[] getNameList(GridColumnMeta[] meta) {
        String sort = req.getParameter(GRID_PARAM_ORDERBY);
        if (StringUtils.isNotEmpty(sort))
            this.orderBy = meta[Integer.parseInt(sort)].getName();

        String[] flds = new String[fieldList.length];
        for (int i = 0; i < flds.length; i++) {
            flds[i] = meta[Integer.parseInt(fieldList[i])].getName();
        }
        return flds;
    }

    /**
     * 获取当前列表显示的列标题
     */
    public String[] getHeaderList(GridColumnMeta[] meta) {
        String sort = req.getParameter(GRID_PARAM_ORDERBY);
        if (StringUtils.isNotEmpty(sort))
            this.orderBy = meta[Integer.parseInt(sort)].getHeader();

        String[] flds = new String[fieldList.length];
        for (int i = 0; i < flds.length; i++) {
            flds[i] = meta[Integer.parseInt(fieldList[i])].getHeader();
        }
        return flds;
    }

    /**
     * 设置不分页
     *
     * @param all
     *            真表示不分页
     */
    public void queryAll(boolean all) {
        if (all)
            this.pageSize = Integer.MAX_VALUE;
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
     * @param count 需要计算的列表大小
     * @return pageNo列表
     */
    public List<Integer> getSlider(int count) {
        int halfSize = count / 2;
        int totalPage = (int)getTotalPages();

        int startPageNo = Math.max(getPageNo() - halfSize, 1);
        int endPageNo = Math.min(startPageNo + count - 1, totalPage);

        if (endPageNo - startPageNo < count) {
            startPageNo = Math.max(endPageNo - count, 1);
        }

        List<Integer> result = Lists.newArrayList();
        for (int i = startPageNo; i <= endPageNo; i++) {
            result.add(i);
        }
        return result;
    }
}
