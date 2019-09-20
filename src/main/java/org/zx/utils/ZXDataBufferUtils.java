package org.zx.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import java.util.List;

/**
 * @author zhouxin
 * @since 2019/8/1
 */
public class ZXDataBufferUtils {

    public static byte[] merge(List<? extends DataBuffer> dataBuffers){
        byte[] allContent = null;
        for (DataBuffer dataBuffer : dataBuffers) {
            byte[] content = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(content);
            DataBufferUtils.release(dataBuffer);
            if (EmptyJudgeUtils.isNull(allContent)) {
                allContent = content;
            } else {
                allContent = ByteArrayUtils.mergeByteArray(allContent, content);
            }
        }
        return allContent;
    }
}
