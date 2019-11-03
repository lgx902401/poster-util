package com.lam.poster.exception.errorcode;

/**
 * 错误码的基础接口
 *
 * @author Roger_Luo
 * @version V1.0
 * @date 2018年3月1日 下午4:32:27
 */
public interface ErrorCode {
    /**
     * 获取错误码
     *
     * @return
     */
    int getCode();

    /**
     * 基础的错误码
     *
     * @author Roger_Luo
     * @version V1.0
     * @date 2018年3月1日 下午4:30:13
     */
    public enum BasicCode implements ErrorCode {
        // 请求成功
        SUCCESS(200),
        // 有问题的请求
        BAD_REQUEST(400),
        // 参数有误
        PARAMETER_ERROR(400),
        // 未授权
        UNAUTHORIZED(401),
        // 权限不够
        FORBIDDEN(403),
        // 请求超时
        SERVICE_TIMEOUT(408),
        // 服务器错误
        INTERNAL_SERVER_ERROR(500);

        private final int code;

        private BasicCode(int code) {
            this.code = code;
        }

        @Override
        public int getCode() {
            return this.code;
        }
    }
}
