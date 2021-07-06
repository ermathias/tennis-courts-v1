package com.tenniscourts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class SwaggerConfig {

    private final ResponseMessage m201 = customMessage201();
    private final ResponseMessage m204put = simpleMessage(204, "Update concluded");
    private final ResponseMessage m204del = simpleMessage(204, "Delete concluded");
    private final ResponseMessage m403 = simpleMessage(403, "Not Authorized");
    private final ResponseMessage m404 = simpleMessage(404, "Not Found");
    private final ResponseMessage m422 = simpleMessage(422, "Validation Error");
    private final ResponseMessage m500 = simpleMessage(500, "Unexpected Error");

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m403, m422, m500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m403, m404, m422, m500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500))
                .select().apis(RequestHandlerSelectors.basePackage("com.tenniscourts"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Tennis court documentation",
                "The documentation for the tennis court application",
                "1.0", "www.fictionalterms.com",
                new Contact("Tiago Veri", "www.tenniscourtAPI.com", "contact@tenniscourt.com"),
                "Allowed only by permission", "www.tenniscourtAPI.com/terms", Collections.emptyList());
    }

    private ResponseMessage simpleMessage(int code, String msg) {
        return new ResponseMessageBuilder().code(code).message(msg).build();
    }

    private ResponseMessage customMessage201() {
        Map<String, Header> map = new HashMap<>();
        map.put("location", new Header("location", "URI of the new resource", new ModelRef("string")));
        return new ResponseMessageBuilder()
                .code(201)
                .message("Resource created")
                .headersWithDescription(map)
                .build();
    }
}
