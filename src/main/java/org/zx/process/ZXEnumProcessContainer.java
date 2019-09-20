package org.zx.process;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhouxin
 * @since 2019/9/18
 */
public class ZXEnumProcessContainer {

    private static final Map<String, ZXEnumProcess> map = new HashMap<>();

    public static void register(ZXEnumProcess process) {
        String key = ZXEnumProcessContainer.buildEnumKey(process.enumType());
        map.put(key, process);
    }

    public static <T extends Enum, R, Q> Q getResult(T anEnum, R data){
        String key = ZXEnumProcessContainer.buildEnumKey(anEnum);
        ZXEnumProcess<T, R, Q> process = map.get(key);
        Objects.requireNonNull(process);

        Q result = process.process(data);
        return result;
    }

    private static String buildEnumKey(Enum anEnum){
        return anEnum.getClass().getSimpleName() + "-" + anEnum.name();
    }
}
