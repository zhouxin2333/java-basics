package org.zx.exception;

import lombok.Getter;

/**
 * @author zhouxin
 * @since 2019/9/20
 */
@Getter
public class ZXException extends RuntimeException {
    private ZXError error;

    public ZXException(ZXError error) {
        super(error.getMsg());
        this.error = error;
    }

    public ZXException(ZXError error, String msg){
        super(msg);
        this.error = error;
    }

    public ZXException(ZXError error, Enum e){
        super(e.name());
        this.error = error;
    }

    public ZXException(ZXError error, Object ... params) {
        super(String.format(error.getMsg(), params));
        this.error = error;
    }
}
