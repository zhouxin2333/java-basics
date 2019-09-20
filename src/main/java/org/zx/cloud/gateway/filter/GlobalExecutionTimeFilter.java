package org.zx.cloud.gateway.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.zx.cloud.gateway.constants.GatewayConstants;
import reactor.core.publisher.Mono;

/**
 * 请求耗时计算filter
 * @author zhouxin
 * @since 2019/8/5
 */
@Component
@Log4j2
public class GlobalExecutionTimeFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then( Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                String uniqueId = GatewayConstants.getUniqueIdFun.apply(exchange);
                Long executionTime = (System.currentTimeMillis() - startTime);
                log.info("the {} request execute {} ms", uniqueId,  executionTime);
            }
        }));
    }
}
