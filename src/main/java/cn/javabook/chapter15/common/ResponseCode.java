package cn.javabook.chapter15.common;

/**
 * 响应状态码
 * 
 */
public enum ResponseCode {
	SUCCESS(0, "操作成功"),
	ERROR(1, "操作失败"),

	;
	// 订单模块 4xxxx
	ResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
