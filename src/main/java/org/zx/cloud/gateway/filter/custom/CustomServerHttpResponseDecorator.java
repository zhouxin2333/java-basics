package org.zx.cloud.gateway.filter.custom;

import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.zx.cloud.gateway.constants.GatewayConstants;
import org.zx.response.ZXResponse;
import org.zx.utils.GZIPUtils;
import org.zx.utils.ZXDataBufferUtils;
import org.zx.utils.ZXJSONUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author zhouxin
 * @since 2019/5/31
 */
@Log4j2
public class CustomServerHttpResponseDecorator extends ServerHttpResponseDecorator {

    private ServerWebExchange exchange;

    public CustomServerHttpResponseDecorator(ServerWebExchange exchange) {
        super(exchange.getResponse());
        this.exchange = exchange;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
//            return super.writeWith(fluxBody.map(dataBuffer -> {
//                byte[] content = new byte[dataBuffer.readableByteCount()];
//                dataBuffer.read(content);
//                //释放掉内存
//                DataBufferUtils.release(dataBuffer);
//                String result = null;
//                // 若是需要zip先解压缩
//                boolean isGzipAccepted = isGzipAccepted(this.getDelegate());
//                if (isGzipAccepted){
//                    result = GZIPUtils.unCompressToString(content);
//                }else {
//                    result = new String(content, StandardCharsets.UTF_8);
//                }
//
//                log.info("the {} request response body: {}", this.exchange.getAttributes().get(GatewayConstants.CACHED_CURRENT_REQUEST_ID), result);
//                STResponse data = STResponse.ok().dataStr(result);
//                String finalDataStr = STJSONUtils.toJSONString(data);
//                // 若是之前已经解压缩的，则需要再次压缩
//                if (isGzipAccepted){
//                    byte[] compress = GZIPUtils.compress(finalDataStr);
//                    return exchange.getResponse().bufferFactory().wrap(compress);
//                }else {
//                    byte[] uppedContent = finalDataStr.getBytes(StandardCharsets.UTF_8);
//                    return exchange.getResponse().bufferFactory().wrap(uppedContent);
//                }
//
//            }).doOnNext(dataBuffer -> {
//                HttpHeaders headers = this.getDelegate().getHeaders();
//                headers.setContentLength(dataBuffer.readableByteCount());
//            }));


            return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                // 防止响应body被截断
                byte[] allContent = ZXDataBufferUtils.merge(dataBuffers);

                String result = null;
                // 若是需要zip先解压缩
                boolean isGzipAccepted = isGzipAccepted(this.getDelegate());
                if (isGzipAccepted){
                    result = GZIPUtils.unCompressToString(allContent);
                }else {
                    result = new String(allContent, StandardCharsets.UTF_8);
                }

                String uniqueId = GatewayConstants.getUniqueIdFun.apply(exchange);
                log.info("the {} request response body: {}", uniqueId, result);
                ZXResponse data = ZXResponse.ok().dataStr(result);
                String finalDataStr = ZXJSONUtils.toJSONString(data);
                // 若是之前已经解压缩的，则需要再次压缩
                if (isGzipAccepted){
                    byte[] compress = GZIPUtils.compress(finalDataStr);
                    return exchange.getResponse().bufferFactory().wrap(compress);
                }else {
                    byte[] uppedContent = finalDataStr.getBytes(StandardCharsets.UTF_8);
                    return exchange.getResponse().bufferFactory().wrap(uppedContent);
                }
            }).doOnNext(dataBuffer -> {
                HttpHeaders headers = this.getDelegate().getHeaders();
                headers.setContentLength(dataBuffer.readableByteCount());
            }));
        }
        return super.writeWith(body);
    }

    @Override
    public Mono<Void> writeAndFlushWith(
            Publisher<? extends Publisher<? extends DataBuffer>> body) {
        return writeWith(Flux.from(body).flatMapSequential(p -> p));
    }

    private static boolean isGzipAccepted(ServerHttpResponse serverHttpResponse) {
        String value = serverHttpResponse.getHeaders().getFirst(HttpHeaders.CONTENT_ENCODING);
        return (value != null && value.toLowerCase().contains("gzip"));
    }
}
