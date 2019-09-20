package org.zx.cloud.gateway.filter.custom;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.zx.cloud.gateway.constants.GatewayConstants;
import org.zx.utils.ZXDataBufferUtils;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

/**
 * @author zhouxin
 * @since 2019/7/30
 */
@Log4j2
public class CustomServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    private ServerWebExchange exchange;

    public CustomServerHttpRequestDecorator(ServerWebExchange exchange) {
        super(exchange.getRequest());
        this.exchange = exchange;
    }


    @Override
    public Flux<DataBuffer> getBody() {

        Flux<DataBuffer> originalBody = this.getDelegate().getBody();
        Flux<DataBuffer> newBody = originalBody.buffer().map(dataBuffers -> {
            // 防止响应body被截断
            byte[] allContent = ZXDataBufferUtils.merge(dataBuffers);
            String uniqueId = GatewayConstants.getUniqueIdFun.apply(exchange);
            String bodyStr = new String(allContent, StandardCharsets.UTF_8);
            log.info("the {} request params: {}", uniqueId, bodyStr);

            NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
            DataBuffer wrap = nettyDataBufferFactory.wrap(allContent);

            return wrap;
        });
        return newBody;
    }
}
