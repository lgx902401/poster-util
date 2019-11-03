package com.lam.poster.exception;

import com.lam.poster.exception.errorcode.ErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 异常
 *
 * @author Roger_Luo
 * @version V1.0
 * @date 2018年3月1日 下午3:31:36
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = -8854366843004521443L;

  private ErrorCode errorCode;
  private final Map<String, Object> properties = new TreeMap<String, Object>();

  public static BusinessException wrap(ErrorCode errorCode, Throwable exception) {
    if (exception instanceof BusinessException) {
      BusinessException se = (BusinessException) exception;
      if (errorCode != null && errorCode != se.getErrorCode()) {
        return new BusinessException(errorCode, exception, exception.getMessage());
      }
      return se;
    } else {
      return new BusinessException(errorCode, exception, exception.getMessage());
    }
  }

  public static BusinessException wrap(Throwable exception) {
    return wrap(null, exception);
  }

  public BusinessException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, Throwable cause) {
    super(cause);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, Throwable cause, String message) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public BusinessException setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  public Map<String, Object> getProperties() {
    return properties;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String name) {
    return (T) properties.get(name);
  }

  public BusinessException set(String name, Object value) {
    properties.put(name, value);
    return this;
  }

  @Override
  public void printStackTrace(PrintStream s) {
    synchronized (s) {
      printStackTrace(new PrintWriter(s));
    }
  }

  @Override
  public void printStackTrace(PrintWriter s) {
    synchronized (s) {
      s.println(this);
      s.println("\t-------------------------------");
      if (errorCode != null) {
        s.println("\t" + errorCode + ":" + errorCode.getClass().getName());
      }
      for (String key : properties.keySet()) {
        s.println("\t" + key + "=[" + properties.get(key) + "]");
      }
      s.println("\t-------------------------------");
      StackTraceElement[] trace = getStackTrace();
      for (int i = 0; i < trace.length; i++) {
        s.println("\tat " + trace[i]);
      }

      Throwable ourCause = getCause();
      if (ourCause != null) {
        ourCause.printStackTrace(s);
      }
      s.flush();
    }
  }
}
