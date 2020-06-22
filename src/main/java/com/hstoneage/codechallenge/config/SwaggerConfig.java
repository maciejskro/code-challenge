package com.hstoneage.codechallenge.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Value("${spring.project.version")
   private String projectVersion;

   @Bean
   public Docket SwaggerApi() {
      return  new Docket(DocumentationType.SWAGGER_2)
            .groupName("Code-Challenge-Api")
            .apiInfo(getApiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .globalResponseMessage(
                  RequestMethod.GET, Arrays.asList(
                        new ResponseMessageBuilder().code(500).message("error 500 message").responseModel(new ModelRef("Error")).build()
                  )
            );
   }

   private ApiInfo getApiInfo()
   {
      return new ApiInfoBuilder()
            .title("Code Challenge API")
            .license("License MIT")
            .version( projectVersion)
            .build();
   }

}
