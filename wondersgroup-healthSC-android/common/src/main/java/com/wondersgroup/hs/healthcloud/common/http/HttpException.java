package com.wondersgroup.hs.healthcloud.common.http;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/7/19 11:51
 */
public class HttpException extends Exception{
    private static final long serialVersionUID = 1L;
    public static final int CODE_CANCEL = -1000001;
    public static final int CUSTOM_CODE = -1000002;

    private int exceptionCode = -1;

    public HttpException() {
    }

    public HttpException(String detailMessage) {
        super(detailMessage);
    }

    public HttpException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public HttpException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     */
    public HttpException(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     */
    public HttpException(int exceptionCode, String detailMessage) {
        super(detailMessage);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param detailMessage
     * @param throwable
     */
    public HttpException(int exceptionCode, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @param exceptionCode The http response status code, 0 if the http request error and has no response.
     * @param throwable
     */
    public HttpException(int exceptionCode, Throwable throwable) {
        super(throwable);
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return The http response status code, 0 if the http request error and has no response.
     */
    public int getExceptionCode() {
        return exceptionCode;
    }

    public boolean isLogicException() {
        return exceptionCode > 0 && (exceptionCode < 200 || exceptionCode > 599);
    }
}
