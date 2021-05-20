package com.tenniscourts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1.0.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(true)
                .apiInfo(this.getAPIInfo());
    }
    
	private ApiInfo getAPIInfo() {
		return new ApiInfoBuilder()
	            .title("Tennis Courts API")
	            .description("Public API's")
	            .version("1.0.0")
	            .license("MIT")
	            .licenseUrl("https://opensource.org/licenses/MIT")
	            .contact(new Contact("Jose Lucas", "https://github.com/zevolution", "contato@zevolution.com.br"))
	            .build();
	}
}
