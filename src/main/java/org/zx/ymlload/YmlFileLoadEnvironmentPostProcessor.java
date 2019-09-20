package org.zx.ymlload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.zx.utils.EmptyJudgeUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author zhouxin
 * @since 2019/6/11
 */
public class YmlFileLoadEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    private static final ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            Resource[] resources = patternResolver.getResources("classpath:*.yml");
            if (EmptyJudgeUtils.isEmpty(resources)) return;

            Arrays.stream(resources)
                    .filter(resource -> !resource.getFilename().equals("application.yml"))
                    .filter(resource -> resource.getFilename().startsWith("st-"))
                    .map(this::loadYaml)
                    .forEach(propertySource -> environment.getPropertySources().addLast(propertySource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertySource<?> loadYaml(Resource path) {
        if (!path.exists()) throw new IllegalArgumentException("Resource " + path + " does not exist");

        try {
            return this.loader.load(path.getFilename(), path).get(0);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load yaml configuration from " + path, ex);
        }
    }
}
