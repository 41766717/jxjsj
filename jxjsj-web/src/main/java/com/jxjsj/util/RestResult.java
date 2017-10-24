package com.jxjsj.util;

/**
 * rest返回结果类
 * @author 肖斯斯  ADD ON 2017/08/11
 *
 */
public class RestResult<T> {

	/** 错误code*/
	private Integer resultCode;
	
	/** 错误说明 */
	private String resultMsg = "";
	
	/** 数据实体 */
	private T resultData;

	public RestResult(T resultData) {
		this.resultCode = 200;
		this.resultMsg = "";
		this.resultData = resultData;
	}

	public RestResult() {
	}

	public RestResult(Integer code, String resultMsg) {
		this.resultCode = code;
		this.resultMsg = resultMsg;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public T getResultData() {
		return resultData;
	}

	public void setResultData(T resultData) {
		this.resultData = resultData;
	}

	public static <T> RestResult<T> createSuccessfull(T t){
		return new RestResult<T>(t);
	}

}
