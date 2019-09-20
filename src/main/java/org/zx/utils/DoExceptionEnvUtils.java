package org.zx.utils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DoExceptionEnvUtils {

    public static <T> Optional<T> tryCatch(Supplier<T> supplier){
        Supplier<Optional<T>> optionalSupplier = () -> Optional.ofNullable(supplier.get());
        return DoExceptionEnvUtils.tryCatch(optionalSupplier, Optional.empty());
    }

    public static <T> T tryCatch(Supplier<T> supplier, T defaultValue){
        try {
            return supplier.get();
        }catch (Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static <T> T tryCatch(Supplier<T> supplier, Supplier<T> exceptionSupplier){
        try {
            return supplier.get();
        }catch (Exception e){
            return exceptionSupplier.get();
        }
    }

    public static Boolean tryCatchReturnTrue(Runnable runnable){
        try {
            runnable.run();
        }catch (Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static Boolean tryCatchBoolean(Supplier<Boolean> supplier){
        return DoExceptionEnvUtils.tryCatch(supplier, false);
    }

    public static Long tryCatchLong(Supplier<Long> supplier){
        return DoExceptionEnvUtils.tryCatch(supplier, Long.valueOf(0));
    }

    public static List tryCatchList(Supplier<List> supplier){
        return DoExceptionEnvUtils.tryCatch(supplier, Collections.EMPTY_LIST);
    }
}