package cn.javabook.chapter15.exception;

/**
 * 业务逻辑自定义异常类
 * 
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 8908924539296096355L;

	private String code;

    private String message;

    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
