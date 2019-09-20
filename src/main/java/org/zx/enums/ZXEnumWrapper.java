package org.zx.enums;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author zhouxin
 * @since 2019/8/2
 */
public class ZXEnumWrapper<T, R> {

    private EnumInfo<T, R> enumInfo;

    private ZXEnumWrapper() {
    }

    private ZXEnumWrapper(EnumInfo enumInfo) {
        this.enumInfo = enumInfo;
    }

    public Optional<T> getEnum(){
        T[] enumConstants = enumInfo.getEnumClass().getEnumConstants();
        Optional<T> enumOptional = Stream.of(enumConstants)
                                         .filter(e -> enumInfo.getEnumPropertyFun().apply(e).equals(enumInfo.getEnumPropertyValue()))
                                         .findFirst();
        return enumOptional;
    }

    public static <T, R> ZXEnumWrapper<T, R> enumType(Class<T> enumClass){

        ZXEnumWrapper<T, R> enumWrapper = new ZXEnumWrapper<>();

        EnumInfo<T, R> enumInfo = EnumInfo.enumClass(enumClass);
        enumWrapper.enumInfo = enumInfo;
        return enumWrapper;
    }

    public ZXEnumWrapper<T, R> addPropertyValue(R propertyValue){
        this.enumInfo.setEnumPropertyValue(propertyValue);
        return this;
    }

    public ZXEnumWrapper<T, R> addPropertyFun(Function<T, R> propertyFun){
        this.enumInfo.setEnumPropertyFun(propertyFun);
        return this;
    }

    @Getter
    static class EnumInfo<T,R> {
        private Class<T> enumClass;

        @Setter
        private R enumPropertyValue;
        @Setter
        private Function<T, R> enumPropertyFun;

        private EnumInfo() {
        }

        public static <T, R> EnumInfo enumClass(Class<T> enumClass){
            EnumInfo<T, R> enumInfo = new EnumInfo<>();
            enumInfo.enumClass = enumClass;
            return enumInfo;
        }
    }
}
