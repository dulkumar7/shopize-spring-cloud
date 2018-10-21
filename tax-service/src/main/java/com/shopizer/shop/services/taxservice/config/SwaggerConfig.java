package com.shopizer.shop.services.taxservice.config;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.shopizer.shop.services.taxservice.constants.TaxServiceConstants.SWAGGER_TITLE;
import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shopizer.shop.services.taxservice.restcontroller"))
                .paths(regex("/rest.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                TaxServiceConstants.SWAGGER_TITLE,
                TaxServiceConstants.SWAGGER_DESCRIPTION,
                TaxServiceConstants.SWAGGER_APP_VERSION,
                TaxServiceConstants.SWAGGER_TERMS_SERVICE_URL,
                new Contact(TaxServiceConstants.SWAGGER_DEV_TEAM_NAME_LIST, TaxServiceConstants.SWAGGER_PROJECT_GIT_REPO,
                        TaxServiceConstants.SWAGGER_TEAM_EMAIL_LIST), TaxServiceConstants.SWAGGER_APP_LICENCE,
                        TaxServiceConstants.SWAGGER_APP_LICENCE_URL);
    }
}
