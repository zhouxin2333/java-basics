package org.zx.cloud.gateway.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zx.exception.ZXError;
import org.zx.exception.ZXErrorNamespace;

/**
 * @author zhouxin
 * @since 2019/7/30
 */
@ZXErrorNamespace("gateway")
@AllArgsConstructor
@Getter
public enum GatewayError implements ZXError {

    UNKNOWN("未知错误: %s"),
    RESPONSE_STATUS_ERROR("响应状态异常: %s"),
    SERVICE_NOT_FOUND("服务找不到"),

    ;


    private String msg;
}
