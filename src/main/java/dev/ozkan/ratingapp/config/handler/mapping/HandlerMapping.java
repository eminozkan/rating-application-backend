package dev.ozkan.ratingapp.config.handler.mapping;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import java.util.EnumSet;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class HandlerMapping {
    @Bean
    static FilterRegistrationBean<Filter> handlerMappingIntrospectorCacheFilter(HandlerMappingIntrospector hmi) {
        Filter cacheFilter = hmi.createCacheFilter();
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>(cacheFilter);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registrationBean;
    }
}
