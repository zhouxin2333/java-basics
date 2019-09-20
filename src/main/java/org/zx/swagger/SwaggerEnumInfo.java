package org.zx.swagger;

import com.fasterxml.jackson.annotation.JsonValue;

public interface SwaggerEnumInfo {

    String getMsg();

    String name();

    /**
     * 需要在swagger上展示的错误码信息
     * @return
     */
    @JsonValue
    default String showInfo(){
        return this.name() + "(" + this.getMsg() + ")";
    }
}