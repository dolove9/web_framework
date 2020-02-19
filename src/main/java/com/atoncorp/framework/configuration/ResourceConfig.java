package com.atoncorp.framework.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    /**
     * <pre>
     * 뷰템플릿에서 리소스의 버전관리 기능을 사용하기 위함
     *
     * 버전관리 기능이 필요한 리소스에 대해 mvcResourceUrlProvider.getForLookupPath 를 사용
     * 사용예)
     *     &lt;script src="../../static/assets/common.js" th:src="${&#064;mvcResourceUrlProvider.getForLookupPath('/assets/common.js')}"&gt;&lt;/script&gt;
     *
     * 출력예)
     *     /assets/common-{리소스 파일의 해시값}.js
     *     /assets/common-DF7F0E2B3BAB90485A86CDB2A70D9673.js
     * </code>
     * </pre>
     * @param registry
     * @see WebMvcConfigurationSupport#mvcResourceUrlProvider()
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

        registry
                .addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS))
//                .resourceChain(true)
                .resourceChain(false)
                .addResolver(new VersionResourceResolver()
                        .addVersionStrategy(new ContentVersionStrategy(), "/**"));

    }
}
