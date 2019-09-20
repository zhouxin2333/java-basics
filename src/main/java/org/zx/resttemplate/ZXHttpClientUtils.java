package org.zx.resttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.zx.utils.EmptyJudgeUtils;

import java.util.Map;

/**
*
* 类功能说明：httpclient工具类,基于httpclient 4.x
*/
public class ZXHttpClientUtils {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ZXHttpClientUtils.class);
 
    /**
     * post请求
     * @param url
     * @param formParams
     * @return
     */
    public static <T> T doPost(String url, Map<String, String> formParams, Class<T> clazz) {
        try {
            return ZXHttpClientUtils.doPostWithException(url, formParams, clazz);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }
        return null;
    }

    public static <T> T doPostWithException(String url, Map<String, String> formParams, Class<T> clazz) {
        if (EmptyJudgeUtils.isEmpty(formParams)) {
            return doPost(url, clazz);
        }
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        formParams.entrySet().stream().forEach(entry -> requestEntity.add(entry.getKey(), entry.getValue()));
        return ZXRestClient.getClient().postForObject(url, requestEntity, clazz);
    }
 
    /**
     * post请求
     * @param url
     * @return
     */
    public static <T> T doPost(String url, Class<T> clazz) {
        try {
            return ZXHttpClientUtils.doPostWithException(url, clazz);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }
        return null;
    }

    public static <T> T doPostWithException(String url, Class<T> clazz) {
        return ZXRestClient.getClient().postForObject(url, HttpEntity.EMPTY, clazz);
    }
 
    /**
     * get请求
     * @param url
     * @return
     */
    public static <T> T doGet(String url, Class<T> clazz) {
        try {
            return ZXHttpClientUtils.doGetWithException(url, clazz);
        } catch (Exception e) {
            LOGGER.error("GET请求出错：{}", url, e);
        }
        return null;
    }

    public static <T> T doGetWithException(String url, Class<T> clazz) {
        return ZXRestClient.getClient().getForObject(url, clazz);
    }

    public static <T> T doGetWithException(String url, Class<T> clazz, Map<String, ?> uriVariables) {
        return ZXRestClient.getClient().getForObject(url, clazz, uriVariables);
    }
}