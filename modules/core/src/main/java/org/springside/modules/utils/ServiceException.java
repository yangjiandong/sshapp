package org.springside.modules.utils;

//自定义异常
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7474233844430711110L;

    /**
     * Creates a new instance of <code>ServiceException</code> without detail
     * message.
     */
    public ServiceException() {
    }

    /**
     * Constructs an instance of <code>ServiceException</code> with the
     * specified detail message.
     *
     * @param msg
     *            the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}