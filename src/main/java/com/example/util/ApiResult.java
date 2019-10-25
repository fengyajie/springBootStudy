package com.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiResult{

	private static final long serialVersionUID = 5905715228490291386L;
	
	private Status status;

	private Object message;
	
	private Long total = 0L;
	
	private List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
	
	private Map<String,Object> params;
	
	private int code =2000; //设置默认值  2000

	public ApiResult() {
		super();
	}

	public ApiResult(Status status, Object message) {
		this.status = status;
		this.message = message;
	}
	
	public ApiResult(List<Map<String,Object>> rows, Long total) {
		this.rows = rows;
		this.total = total;
		this.status = Status.OK;
	}

	public ApiResult(Status status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}


	public enum Status {
		OK, ERROR
	}

	public void addOK(Object message) {
		this.message = message;
		this.status = Status.OK;
	}

	public void addError(Object message) {
		this.message = message;
		this.status = Status.ERROR;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public List<Map<String,Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String,Object>> rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String,Object> params) {
		this.params = params;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static ApiResult ok() {
		ApiResult r = new ApiResult();
		r.addOK("操作成功");
		return r;
	}

	public static ApiResult error() {
		ApiResult r = new ApiResult();
		r.addError("操作失败");
		return r;
	}
	
	public static ApiResult error(int code, String message) {
		ApiResult r = new ApiResult();
		r.addError(message);
		r.setCode(code);
		return r;
	}
}
