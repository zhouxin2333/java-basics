package org.zx.redis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxin
 * @since 2019/9/12
 */
public class ZXRedisParamKey implements ZXRedisKey {

    private ZXRedisKey redisKey;
    private List<Object> params = new ArrayList<>();

    public static ZXRedisParamKey init(ZXRedisKey redisKey){
        ZXRedisParamKey redisParamKey = new ZXRedisParamKey();
        redisParamKey.redisKey = redisKey;
        return redisParamKey;
    }

    public ZXRedisParamKey addParam(Object param){
        this.params.add(param);
        return this;
    }

    private ZXRedisParamKey() {
    }

    @Override
    public String getKey() {
        return String.format(this.redisKey.getKey(), params.stream().toArray());
    }
}
