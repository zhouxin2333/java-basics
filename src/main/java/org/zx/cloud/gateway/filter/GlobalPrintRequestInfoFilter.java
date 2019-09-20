package org.zx.cloud.gateway.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.zx.cloud.gateway.constants.GatewayConstants;
import org.zx.cloud.gateway.filter.custom.CustomServerHttpRequestDecorator;
import org.zx.utils.EmptyJudgeUtils;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhouxin
 * @since 2019/7/30
 */
@Component
@Log4j2
public class GlobalPrintRequestInfoFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String method = serverHttpRequest.getMethodValue();
        URI uri = serverHttpRequest.getURI();
        String uniqueId = UUID.randomUUID().toString();
        GatewayConstants.addUniqueIdFun.accept(exchange, uniqueId);
        log.info("get a {} request({}) from {}: {}", method, uniqueId, uri.getHost(), uri);
        log.info("the {} request header: {}", uniqueId, serverHttpRequest.getHeaders());
        if (HttpMethod.POST.name().equals(method)) {
            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            CustomServerHttpRequestDecorator requestDecorator = new CustomServerHttpRequestDecorator(exchange);
            //封装request，传给下一级
            return chain.filter(exchange.mutate().request(requestDecorator).build());
        } else if (HttpMethod.GET.name().equals(method)) {
            Map requestQueryParams = serverHttpRequest.getQueryParams();
            log.info("the {} request params: {}", uniqueId, EmptyJudgeUtils.isEmpty(requestQueryParams) ? "" : requestQueryParams);

            return chain.filter(exchange);
        }
        return chain.filter(exchange);
    }
}
