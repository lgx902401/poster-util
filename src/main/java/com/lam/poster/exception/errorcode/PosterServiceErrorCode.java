package com.lam.poster.exception.errorcode;

/**
 *
 * PosterService 的错误码 (格式：1xxxxx，6位数，数字1开头)
 *
 * @author	Lam
 * @version	V1.0
 */
public enum PosterServiceErrorCode implements ErrorCode {
  // 登录逻辑相关的错误码：1000XX
  LOGIN_USERNAME_PASSWORD_ERROR(100001),
  // 注册逻辑相关的错误码：1010XX
  REGISTER_USERNAME_EXIST(101001);
  
  private final int code;
  
  private PosterServiceErrorCode(int code) {
    this.code = code;
  }
  
  @Override
  public int getCode() {
    return code;
  }
}
