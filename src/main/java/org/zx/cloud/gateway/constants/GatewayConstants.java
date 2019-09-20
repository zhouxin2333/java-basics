package org.zx.cloud.gateway.constants;

import org.springframework.web.server.ServerWebExchange;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author zhouxin
 * @since 2019/7/30
 */
public interface GatewayConstants {

    String CACHED_CURRENT_REQUEST_ID = "cachedCurrentRequestUUUId";

    Function<ServerWebExchange, String> getUniqueIdFun = exchange -> (String) exchange.getAttributes().get(CACHED_CURRENT_REQUEST_ID);
    BiConsumer<ServerWebExchange, String> addUniqueIdFun = (exchange, uniqueId) -> exchange.getAttributes().put(CACHED_CURRENT_REQUEST_ID, uniqueId);
}
