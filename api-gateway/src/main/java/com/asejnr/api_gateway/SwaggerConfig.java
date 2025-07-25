package com.asejnr.api_gateway;

import java.util.HashSet;
import java.util.Set;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {
    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        locator.getRouteDefinitions()
                .collectList()
                .doOnNext(definitions -> {
                    Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
                    definitions.stream()
                            .filter(routeDefinition -> routeDefinition.getId().endsWith("-service"))
                            .forEach(routeDefinition -> {
                                String name = routeDefinition.getId().replace("-service", "");
                                AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                                        new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                                name, "/v3/api-docs/" + name, null);
                                urls.add(swaggerUrl);
                            });
                    swaggerUiConfigProperties.setUrls(urls);
                })
                .subscribe();
    }
}

// package com.asejnr.api_gateway;
//
// import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;
//
// import jakarta.annotation.PostConstruct;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;
// import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
// import org.springdoc.core.properties.SwaggerUiConfigProperties;
// import org.springframework.cloud.gateway.route.RouteDefinition;
// import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// class SwaggerConfig {
//    private final RouteDefinitionLocator locator;
//    private final SwaggerUiConfigProperties swaggerUiConfigProperties;
//
//    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
//        this.locator = locator;
//        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
//    }
//
//    @PostConstruct
//    public void init() {
//        List<RouteDefinition> definitions =
//                locator.getRouteDefinitions().collectList().block();
//        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
//        definitions.stream()
//                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
//                .forEach(routeDefinition -> {
//                    String name = routeDefinition.getId().replaceAll("-service", "");
//                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
//                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
//                                    name, DEFAULT_API_DOCS_URL + "/" + name, null);
//                    urls.add(swaggerUrl);
//                });
//        swaggerUiConfigProperties.setUrls(urls);
//    }
// }
