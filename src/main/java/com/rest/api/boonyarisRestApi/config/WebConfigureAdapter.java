package com.rest.api.boonyarisRestApi.config;

import com.rest.api.boonyarisRestApi.environment.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfigureAdapter implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public WebConfigureAdapter(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathPatterns = new ArrayList<>(Arrays.asList(Constant.PATH_EXCLUDE_INTERCEPTOR));
        registry.addInterceptor(this.authenticationInterceptor).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
    }
}
