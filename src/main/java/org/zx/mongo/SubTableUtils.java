package org.zx.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouxin
 * @since 2019/8/2
 */
public class SubTableUtils {

    public static <T> String getCollectionNameYearMonth4LastMonth(Class<T> tClass){
        Document annotation = tClass.getAnnotation(Document.class);
        String collectionName = annotation.collection() + "_" + SubTableUtils.getCurrentYearMonth4LastMonth();
        return collectionName;
    }

    public static <T> String getCollectionNameYearMonth(Class<T> tClass){
        Document annotation = tClass.getAnnotation(Document.class);
        String collectionName = annotation.collection() + "_" + SubTableUtils.getCurrentYearMonth();
        return collectionName;
    }

    private static String getCurrentYearMonth(){
        String yearMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return yearMonth;
    }

    private static String getCurrentYearMonth4LastMonth(){
        String yearMonth = YearMonth.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"));
        return yearMonth;
    }
}
