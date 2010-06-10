package com.ekingsoft.core.orm;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.ekingsoft.core.grid.GridColumnMeta;

/**
 * 与具体ORM实现无关的分页参数及查询结果封装. 注意所有序号从1开始.
 * 
 * @param <T>
 *            Page中记录的类型.
 * 
 * @author calvin
 */
public class Page<T> {
	// 公共变量 //
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	public static final String GRID_PARAM_START = "start";
	public static final String GRID_PARAM_PAGESIZE = "limit";
	public static final String GRID_PARAM_ORDERBY = "sort";
	public static final String GRID_PARAM_ORDERDIR = "dir";

	public static final String GRID_PARAM_EXPORT = "export";
	public static final String GRID_PARAM_EXPORT_FORMAT = "_p_exp_format";
	public static final String GRID_PARAM_FIELD_LIST = "fieldsList";

	// 分页参数 //
	protected int pageNo = 1;
	private int start = 0;
	private int pageSize = 20;

	// 导出参数
	private String orderBy = "";
	private String orderDir = "";
	private boolean isExport = false;
	private String expFormat = "xls";
	private String[] fieldList;
	private HttpServletRequest req;

	private boolean autoCount = true;

	// 返回结果 //
	private List<T> result = Collections.emptyList();
	private String jsonResult = null;
	private long totalCount = -1;

	// 构造函数 //
	public Page() {
		super();
	}

	public Page(final int pageSize) {
		setPageSize(pageSize);
	}

	public Page(final int pageSize, final boolean autoCount) {
		setPageSize(pageSize);
		this.autoCount = autoCount;
	}

	public Page(HttpServletRequest request) {
		this.req = request;

		String exportStr = request.getParameter(GRID_PARAM_EXPORT);
		this.setExport(false);
		if (StringUtils.isNotEmpty(exportStr)
				&& exportStr.equalsIgnoreCase("y")) {
			this.setExport(true);

			String formatStr = request.getParameter(GRID_PARAM_EXPORT_FORMAT);
			if (StringUtils.isNotEmpty(formatStr)) {
				this.setExpFormat(formatStr);
			}

			String fieldListStr = request.getParameter(GRID_PARAM_FIELD_LIST);
			if (StringUtils.isNotEmpty(fieldListStr)) {
				this.setFieldList(fieldListStr.split("-"));
			}

		}

		String start = request.getParameter(GRID_PARAM_START);
		this.start = StringUtils.isEmpty(start) ? 0 : Integer.valueOf(start);

		if (this.start == 0) {
			this.pageNo = 1;
		} else {
			this.pageNo = this.start / this.pageSize + 1;
		}

		String limit = request.getParameter(GRID_PARAM_PAGESIZE);
		if (StringUtils.isEmpty(limit)) {
			if (this.isExport)
				this.pageSize = 1000;
			else
				this.pageSize = 20;
		} else {
			this.pageSize = Integer.valueOf(limit);
		}

		String orderStr = request.getParameter(GRID_PARAM_ORDERBY);
		if (StringUtils.isNotEmpty(orderStr)) {
			this.orderBy = orderStr;
		}

		String orderDirStr = request.getParameter(GRID_PARAM_ORDERDIR);
		if (StringUtils.isNotEmpty(orderDirStr)) {
			this.orderDir = orderDirStr;
		}

	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得当前页的第一条记录序号，从０开始,默认为０.
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
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 20;
		}
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

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return StringUtils.isNotBlank(orderBy);
	}

	/**
	 * 设置排序方向(升序or降序)
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * 获取排序方向
	 */
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}

	/**
	 * 查询对象时是否自动另外执行count查询获取总记录数, 默认为true.
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

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得页内的记录列表(JSON String).
	 */
	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

	/**
	 * 取得总记录数, 默认值为-1.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalCount < 0)
			return -1;

		long count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean hasPrePage() {
		return (pageNo - 1 >= 1);
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
}
