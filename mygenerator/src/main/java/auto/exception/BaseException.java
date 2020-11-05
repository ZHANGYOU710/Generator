package auto.exception;

/**
 * 基础异常
 * @author ZhangYou
 * 日 期: 创建时间: 2020/11/05
 * 版 本: v1.0
 * */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
