package org.zx.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouxin
 * @since 2019/6/4
 */
@Configuration
public class SupportMongoConfig {

    @Bean
    public ZXMongoGeneratedValueEventListener listener(){
        return new ZXMongoGeneratedValueEventListener();
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions(){

        List<Object> defaults = new ArrayList<>();
        defaults.addAll(ZXMongoTimeConverters.getConvertersToRegister());

        MongoCustomConversions mongoCustomConversions = new MongoCustomConversions(defaults);
        return mongoCustomConversions;
    }
}
