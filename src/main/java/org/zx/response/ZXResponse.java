package org.zx.response;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.zx.exception.ZXError;
import org.zx.exception.ZXErrorNamespace;
import org.zx.utils.ClassUtils;
import org.zx.utils.ZXJSONUtils;

import java.io.Serializable;

/**
 * @author zhouxin
 * @since 2019/5/30
 */
@Getter
@Setter
public class ZXResponse<T> implements Serializable {

    private static final long serialVersionUID = 6310723258311482993L;

    private String status;
    private String msg;
    private String dataStr;
    private ZXError error;

    private ZXResponse(){}

    @JsonIgnore
    public boolean isOk(){
        return ZXResponseStatus.OK.name().equals(this.status);
    }

    private ZXResponse(ZXResponseStatus status){
        this.status = status.name();
    }

    public static ZXResponse ok(){
        return ZXResponse.ok("成功");
    }

    private static ZXResponse ok(String msg){
        ZXResponse ZXResponse = new ZXResponse(ZXResponseStatus.OK);
        ZXResponse.msg = msg;
        return ZXResponse;
    }

    public static ZXResponse fail(){
        ZXResponse response = new ZXResponse(ZXResponseStatus.FAIL);
        return response;
    }

    public ZXResponse error(ZXError error){
        this.error = error;
        this.msg = error.getMsg();
        return this;
    }

    public ZXResponse error(ZXError error, String msg){
        this.error = error;
        this.msg = msg;
        return this;
    }

    public ZXResponse errorFormat(ZXError error, Object ... params){
        this.error = error;
        this.msg = String.format(error.getMsg(), params);
        return this;
    }

    public ZXResponse dataStr(String dataStr){
        this.dataStr = dataStr;
        return this;
    }

    public ZXResponse msg(String msg){
        this.msg = msg;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public String getErrorMsgWithNamespace(){
        Class<? extends ZXError> targetClass = this.error.getClass();
        boolean annotationPresent = targetClass.isAnnotationPresent(ZXErrorNamespace.class);
        if (annotationPresent){
            ZXErrorNamespace namespace = targetClass.getAnnotation(ZXErrorNamespace.class);
            return namespace + "." + this.msg;
        }else {
            return this.msg;
        }
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public T getData(){
        JSONObject jsonObject = JSONObject.parseObject(this.dataStr);
        String tClassStr = (String) jsonObject.get("tClass");
        Class<T> tClass = ClassUtils.parse(tClassStr);
        T data = ZXJSONUtils.parseObject(this.dataStr, tClass);
        return data;
    }
}
