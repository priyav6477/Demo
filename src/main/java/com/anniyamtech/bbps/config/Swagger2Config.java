package com.anniyamtech.bbps.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration

@EnableScheduling
public class Swagger2Config {
	
	@Bean
	public ApiInfo apiInfo() {
	    return new ApiInfo(
	      "BBPS Api", 
	      "Some custom description of API.", 
	      "1.0 ", 
	      "Terms of service", 
	      new Contact("Priya ", "www.example.com", "priya.v@anniyam.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
	
	
	
	  private RequestParameter authorizationParameter() {
	  RequestParameterBuilder tokenBuilder = new RequestParameterBuilder();
	  
	  tokenBuilder.name("X-Authorization")
	  .description("access_token")
	  .required(false)
	  .in("header")
	//  .accepts(Collections.)
	  .build(); 
	  return tokenBuilder.build();
	  }
	 
	 

	@Bean
	public Docket swaggerConfig() {

		
		  List<RequestParameter> aParameters = new ArrayList<>();
		  aParameters.add(authorizationParameter());
		 
		 
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.anniyamtech.bbps.controller"))
				.build()
				.apiInfo(apiInfo())
				.globalRequestParameters(aParameters);
	}
}



/*
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration;
 * 
 * import springfox.documentation.builders.ParameterBuilder; import
 * springfox.documentation.builders.PathSelectors; import
 * springfox.documentation.builders.RequestHandlerSelectors; import
 * springfox.documentation.schema.ModelRef; import
 * springfox.documentation.service.ApiInfo; import
 * springfox.documentation.service.Contact; import
 * springfox.documentation.spi.DocumentationType; import
 * springfox.documentation.spring.web.plugins.Docket; import
 * springfox.documentation.swagger2.annotations.EnableSwagger2; import static
 * springfox.documentation.builders.PathSelectors.regex;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * 
 * 
 * @Configuration
 * 
 * @EnableSwagger2 public class SwaggerConfig {
 * 
 * @Bean public Docket api() { ParameterBuilder aParameterBuilder = new
 * ParameterBuilder(); aParameterBuilder.name("X-Authorization") // name of
 * header .modelRef(new ModelRef("string")) .parameterType("header") // type -
 * header .defaultValue("") // based64 of - zone:mypassword .required(false)
 * //for compulsory .build();
 * 
 * List<springfox.documentation.service.Parameter> aParameters = new
 * ArrayList<>(); aParameters.add(aParameterBuilder.build());
 * 
 * return new Docket(DocumentationType.SWAGGER_2) .select()
 * .apis(RequestHandlerSelectors.basePackage("com.oasys"))
 * .paths(PathSelectors.any()) .build() .apiInfo(metaData())
 * .globalOperationParameters(aParameters);
 * 
 * }
 * 
 * private ApiInfo metaData() { ApiInfo apiInfo = new ApiInfo("Aeps",
 * "Anniyam API", "1.0", "Terms of service", new Contact("",
 * "http://www.anniyam.co/index.html", ""), "", ""); return apiInfo; } }
 */
 