package org.zx.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.zx.utils.ArrayUtils;
import org.zx.utils.DoExceptionEnvUtils;
import org.zx.utils.EmptyJudgeUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhouxin
 * @since 2019/9/10
 */
@Component
public class ZXRedisUtils {

    private static String applicationName;
    private static final String KEY_PATTERN = "%s:%s";

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        ZXRedisUtils.redisTemplate = redisTemplate;
    }

    @Value("${spring.application.name}")
    public void setApplicationName(String applicationName) {
        ZXRedisUtils.applicationName = Arrays.stream(applicationName.split("-")).skip(1l).collect(Collectors.joining(":"));
    }

    public static Boolean expire(ZXRedisKey redisKey, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.expire(ZXRedisUtils.getKey(redisKey), timeout, unit));
    }

    public static Boolean expireSeconds(ZXRedisKey redisKey, long timeout) {
        return ZXRedisUtils.expire(redisKey, timeout, TimeUnit.SECONDS);
    }

    public static Boolean expireHours(ZXRedisKey redisKey, long timeout) {
        return ZXRedisUtils.expire(redisKey, timeout, TimeUnit.HOURS);
    }

    public static Boolean expireDays(ZXRedisKey redisKey, long timeout) {
        return ZXRedisUtils.expire(redisKey, timeout, TimeUnit.DAYS);
    }

    public static long getExpireSeconds(ZXRedisKey redisKey) {
        return redisTemplate.getExpire(ZXRedisUtils.getKey(redisKey), TimeUnit.SECONDS);
    }

    public static long getExpireHours(ZXRedisKey redisKey) {
        return redisTemplate.getExpire(ZXRedisUtils.getKey(redisKey), TimeUnit.HOURS);
    }

    public static long getExpireDays(ZXRedisKey redisKey) {
        return redisTemplate.getExpire(ZXRedisUtils.getKey(redisKey), TimeUnit.DAYS);
    }

    public static Boolean hasKey(ZXRedisKey redisKey) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.hasKey(ZXRedisUtils.getKey(redisKey)));
    }

    public static void del(ZXRedisKey... redisKeys) {
        if (ArrayUtils.isSizeOne(redisKeys)){
            redisTemplate.delete(ZXRedisUtils.getKey(redisKeys[0]));
        }else {
            redisTemplate.delete(ZXRedisUtils.getKeys(redisKeys));
        }
    }

    public static Boolean put(ZXRedisKey redisKey, Object value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForValue().set(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean put(ZXRedisKey redisKey, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForValue().set(ZXRedisUtils.getKey(redisKey), value, timeout, unit));
    }

    public static Boolean putSeconds(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.put(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean putHours(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.put(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean putDays(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.put(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean putIfPresent(ZXRedisKey redisKey, Object value) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.opsForValue().setIfPresent(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean putIfPresent(ZXRedisKey redisKey, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.opsForValue().setIfPresent(ZXRedisUtils.getKey(redisKey), value, timeout, unit));
    }

    public static Boolean putIfPresentSeconds(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfPresent(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean putIfPresentHours(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfPresent(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean putIfPresentDays(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfPresent(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean putIfAbsent(ZXRedisKey redisKey, Object value) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.opsForValue().setIfAbsent(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean putIfAbsent(ZXRedisKey redisKey, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchBoolean(() -> redisTemplate.opsForValue().setIfAbsent(ZXRedisUtils.getKey(redisKey), value, timeout, unit));
    }

    public static Boolean putIfAbsentSeconds(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfAbsent(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean putIfAbsentHours(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfAbsent(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean putIfAbsentDays(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.putIfAbsent(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static <T> T get(ZXRedisKey redisKey){
        return ZXRedisUtils.get(redisKey, null);
    }

    public static <T> T get(ZXRedisKey redisKey, T defaultValue){
        T redisData = (T) redisTemplate.opsForValue().get(ZXRedisUtils.getKey(redisKey));
        return EmptyJudgeUtils.getOrDefault(redisData, defaultValue);
    }

    /**
     *
     * @param redisKey
     * @return 返回新值
     */
    public static long increment(ZXRedisKey redisKey) {
        return redisTemplate.opsForValue().increment(ZXRedisUtils.getKey(redisKey));
    }

    public static long increment(ZXRedisKey redisKey, long delta) {
        return redisTemplate.opsForValue().increment(ZXRedisUtils.getKey(redisKey), delta);
    }

    /**
     *
     * @param redisKey
     * @return  返回新值
     */
    public static long decrement(ZXRedisKey redisKey) {
        return redisTemplate.opsForValue().decrement(ZXRedisUtils.getKey(redisKey));
    }

    public static long decrement(ZXRedisKey redisKey, long delta) {
        return redisTemplate.opsForValue().decrement(ZXRedisUtils.getKey(redisKey), delta);
    }

    // =======================================map operations begin==============================================

    public static Boolean mapPut(ZXRedisKey redisKey, String item, Object value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForHash().put(ZXRedisUtils.getKey(redisKey), item, value));
    }

    /**
     * 如果已存在的hash表有时间,这里将会替换原有的时间
     * @param redisKey
     * @param item
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public static Boolean mapPut(ZXRedisKey redisKey, String item, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForHash().put(ZXRedisUtils.getKey(redisKey), item, value);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public static Boolean mapPutSeconds(ZXRedisKey redisKey, String item, Object value, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, item, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean mapPutHours(ZXRedisKey redisKey, String item, Object value, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, item, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean mapPutDays(ZXRedisKey redisKey, String item, Object value, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, item, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean mapPutIfAbsent(ZXRedisKey redisKey, String item, Object value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForHash().putIfAbsent(ZXRedisUtils.getKey(redisKey), item, value));
    }

    public static Boolean mapPut(ZXRedisKey redisKey, Map<String, Object> map) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForHash().putAll(ZXRedisUtils.getKey(redisKey), map));
    }

    public static Boolean mapPut(ZXRedisKey redisKey, Map<String, Object> map, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForHash().putAll(ZXRedisUtils.getKey(redisKey), map);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public Boolean mapPutSeconds(ZXRedisKey redisKey, Map<String, Object> map, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, map, timeout, TimeUnit.SECONDS);
    }

    public Boolean mapPutHours(ZXRedisKey redisKey, Map<String, Object> map, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, map, timeout, TimeUnit.HOURS);
    }

    public Boolean mapPutDays(ZXRedisKey redisKey, Map<String, Object> map, long timeout) {
        return ZXRedisUtils.mapPut(redisKey, map, timeout, TimeUnit.DAYS);
    }

    public static <T> T mapGet(ZXRedisKey redisKey, String item) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(ZXRedisUtils.getKey(redisKey), item);
    }

    public static <T> Map<String, T> mapGet(ZXRedisKey redisKey) {
        HashOperations<String, String, T> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(ZXRedisUtils.getKey(redisKey));
    }

    public static void mapDel(ZXRedisKey redisKey, Object... item) {
        redisTemplate.opsForHash().delete(ZXRedisUtils.getKey(redisKey), item);
    }

    public static boolean mapHasKey(ZXRedisKey redisKey, String item) {
        return redisTemplate.opsForHash().hasKey(ZXRedisUtils.getKey(redisKey), item);
    }

    /**
     *
     * @param redisKey
     * @param item
     * @param delta
     * @return  返回新值
     */
    public static double mapIncrement(ZXRedisKey redisKey, String item, double delta) {
        return redisTemplate.opsForHash().increment(ZXRedisUtils.getKey(redisKey), item, delta);
    }

    public static double mapIncrement(ZXRedisKey redisKey, String item, long delta) {
        return redisTemplate.opsForHash().increment(ZXRedisUtils.getKey(redisKey), item, delta);
    }

    public static double mapDecrement(ZXRedisKey redisKey, String item, double delta) {
        return redisTemplate.opsForHash().increment(ZXRedisUtils.getKey(redisKey), item, -delta);
    }

    public static double mapDecrement(ZXRedisKey redisKey, String item, long delta) {
        return redisTemplate.opsForHash().increment(ZXRedisUtils.getKey(redisKey), item, -delta);
    }

    // =======================================map operations end==============================================


    // =======================================set operations begin==============================================

    public static Long setAdd(ZXRedisKey redisKey, Object... values) {
        return DoExceptionEnvUtils.tryCatchLong(() -> redisTemplate.opsForSet().add(ZXRedisUtils.getKey(redisKey), values));
    }

    public static Long setAdd(ZXRedisKey redisKey, long timeout, TimeUnit unit, Object... values) {
        return DoExceptionEnvUtils.tryCatchLong(() -> {
            Long add = redisTemplate.opsForSet().add(ZXRedisUtils.getKey(redisKey), values);
            ZXRedisUtils.expire(redisKey, timeout, unit);
            return add;
        });
    }

    public static Long setAddSeconds(ZXRedisKey redisKey, long timeout, Object... values) {
        return ZXRedisUtils.setAdd(redisKey, timeout, TimeUnit.SECONDS, values);
    }

    public static Long setAddHours(ZXRedisKey redisKey, long timeout, Object... values) {
        return ZXRedisUtils.setAdd(redisKey, timeout, TimeUnit.HOURS, values);
    }

    public static Long setAddDays(ZXRedisKey redisKey, long timeout, Object... values) {
        return ZXRedisUtils.setAdd(redisKey, timeout, TimeUnit.DAYS, values);
    }

    public static <T> Set<T> setGet(ZXRedisKey redisKey) {
        return (Set<T>) redisTemplate.opsForSet().members(ZXRedisUtils.getKey(redisKey));
    }

    public static Boolean setHasKey(ZXRedisKey redisKey, Object value) {
        return redisTemplate.opsForSet().isMember(ZXRedisUtils.getKey(redisKey), value);
    }

    public static Long setSize(ZXRedisKey redisKey) {
        return redisTemplate.opsForSet().size(ZXRedisUtils.getKey(redisKey));
    }

    public static Long setRemove(ZXRedisKey redisKey, Object... values) {
        return DoExceptionEnvUtils.tryCatchLong(() -> redisTemplate.opsForSet().remove(ZXRedisUtils.getKey(redisKey), values));
    }

    // =======================================set operations end==============================================


    // =======================================set operations begin==============================================


    public static Boolean listRightPush(ZXRedisKey redisKey, Object value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForList().rightPush(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean listRightPush(ZXRedisKey redisKey, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForList().rightPush(ZXRedisUtils.getKey(redisKey), value);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public static Boolean listRightPushSeconds(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean listRightPushHours(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean listRightPushDays(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean listLeftPush(ZXRedisKey redisKey, Object value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForList().leftPush(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean listLeftPush(ZXRedisKey redisKey, Object value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForList().leftPush(ZXRedisUtils.getKey(redisKey), value);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public static Boolean listLeftPushSeconds(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean listLeftPushHours(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean listLeftPushDays(ZXRedisKey redisKey, Object value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean listRightPush(ZXRedisKey redisKey, List<Object> value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForList().rightPushAll(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean listRightPush(ZXRedisKey redisKey, List<Object> value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForList().rightPushAll(ZXRedisUtils.getKey(redisKey), value);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public static Boolean listRightPushSeconds(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean listRightPushHours(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean listRightPushDays(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listRightPush(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static Boolean listLeftPush(ZXRedisKey redisKey, List<Object> value) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForList().leftPushAll(ZXRedisUtils.getKey(redisKey), value));
    }

    public static Boolean listLeftPush(ZXRedisKey redisKey, List<Object> value, long timeout, TimeUnit unit) {
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> {
            redisTemplate.opsForList().leftPushAll(ZXRedisUtils.getKey(redisKey), value);
            ZXRedisUtils.expire(redisKey, timeout, unit);
        });
    }

    public static Boolean listLeftPushSeconds(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.SECONDS);
    }

    public static Boolean listLeftPushHours(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.HOURS);
    }

    public static Boolean listLeftPushDays(ZXRedisKey redisKey, List<Object> value, long timeout) {
        return ZXRedisUtils.listLeftPush(redisKey, value, timeout, TimeUnit.DAYS);
    }

    public static <T> Optional<T> listRightPop(ZXRedisKey redisKey) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatch(() -> stringObjectListOperations.rightPop(ZXRedisUtils.getKey(redisKey)));
    }

    public static <T> Optional<T> listRightPop(ZXRedisKey redisKey, long timeout, TimeUnit unit) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatch(() -> stringObjectListOperations.rightPop(ZXRedisUtils.getKey(redisKey), timeout, unit));
    }

    public static <T> Optional<T> listRightPopSeconds(ZXRedisKey redisKey, long timeout) {
        return ZXRedisUtils.listRightPop(redisKey, timeout, TimeUnit.SECONDS);
    }

    public static <T> Optional<T> listLeftPop(ZXRedisKey redisKey) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatch(() -> stringObjectListOperations.leftPop(ZXRedisUtils.getKey(redisKey)));
    }

    public static <T> Optional<T> listLeftPop(ZXRedisKey redisKey, long timeout, TimeUnit unit) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatch(() -> stringObjectListOperations.leftPop(ZXRedisUtils.getKey(redisKey), timeout, unit));
    }

    public static <T> Optional<T> listLeftPopSeconds(ZXRedisKey redisKey, long timeout) {
        return ZXRedisUtils.listLeftPop(redisKey, timeout, TimeUnit.SECONDS);
    }


    public static <T> List<T> listRange(ZXRedisKey redisKey, long start, long end) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatchList(() -> stringObjectListOperations.range(ZXRedisUtils.getKey(redisKey), start, end));
    }

    public static Long listSize(ZXRedisKey redisKey) {
        return DoExceptionEnvUtils.tryCatchLong(() -> redisTemplate.opsForList().size(ZXRedisUtils.getKey(redisKey)));
    }

    public static <T> Optional<T> listIndex(ZXRedisKey redisKey, long index) {
        ListOperations<String, T> stringObjectListOperations = (ListOperations<String, T>) redisTemplate.opsForList();
        return DoExceptionEnvUtils.tryCatch(() -> stringObjectListOperations.index(ZXRedisUtils.getKey(redisKey), index));
    }

    public static Boolean listSet(ZXRedisKey redisKey, long index, Object value){
        return DoExceptionEnvUtils.tryCatchReturnTrue(() -> redisTemplate.opsForList().set(ZXRedisUtils.getKey(redisKey), index, value));
    }

    /**
     * 移除count个值为value
     * @param redisKey
     * @param count
     * @param value
     * @return
     */
    public static Long listRemove(ZXRedisKey redisKey, long count, Object value){
        return DoExceptionEnvUtils.tryCatchLong(() -> redisTemplate.opsForList().remove(ZXRedisUtils.getKey(redisKey), count, value));
    }

    private static String getKey(ZXRedisKey redisKey){
        return String.format(KEY_PATTERN, ZXRedisUtils.applicationName, redisKey.getKey());
    }

    private static List<String> getKeys(ZXRedisKey ... redisKeys){
        return Arrays.stream(redisKeys).map(ZXRedisKey::getKey).collect(Collectors.toList());
    }
}
