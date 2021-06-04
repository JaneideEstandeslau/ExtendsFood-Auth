package com.digitalSystems.extendsfood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.digitalSystems.extendsfood.api.exceptionHandler.Problem;
import com.digitalSystems.extendsfood.api.model.PedidoResumoModel;
import com.digitalSystems.extendsfood.api.openapi.model.PageableModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.PedidoResumoModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class) // São beans que vão interpretar as anotações de validação como, 
												// por exempro, @notnull e adicionar ao Json.
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {

		var typeResolver = new TypeResolver();

		// Configurações para gerar o Json da documentação
		return new Docket(DocumentationType.SWAGGER_2)
				
				.select()// Seleciona os endpoints que devem ser expostos na definição do json
					.apis(RequestHandlerSelectors.basePackage("com.digitalSystems.extendsfood"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)// Desabilita os Codigos de erros padrão
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())// Descreve os status de resposta de erro globalmente nos métodos
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				
				.additionalModels(typeResolver.resolve(Problem.class))// Adiciona o Problem no Modelo de representação
				
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)// substitui o Pageable pelo
																					// PageableModelOpenApi
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(Page.class, PedidoResumoModel.class),
						PedidoResumoModelOpenApi.class)) // Faz a substituição do Page<CozinhaModel> para
														// CozinhasModelOpenApi, ou seja, ao invéz de retornar um Page
														// com
														// uma lista de cozinhas, vai retornar CozinhasModelOpenApi com
														// uma
														// lista de cozinha model.
				.apiInfo(apiInfo()).tags(new Tag("Cidades", "Gerencia as cidades"));
	}
	
	//Adiciona os  possiveis Status de Erro para o método GET
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
			);
	}
	
	//Adiciona os  possiveis Status de Erro para os métodos POST e PUT
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				 new ResponseMessageBuilder()
	                .code(HttpStatus.BAD_REQUEST.value())
	                .message("Requisição inválida (erro do cliente)")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .message("Erro interno no servidor")
	                .responseModel(new ModelRef("Problema"))
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.NOT_ACCEPTABLE.value())
	                .message("Recurso não possui representação que poderia ser aceita pelo consumidor")
	                .build(),
	            new ResponseMessageBuilder()
	                .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	                .message("Requisição recusada porque o corpo está em um formato não suportado")
	                .responseModel(new ModelRef("Problema"))
	                .build()
			);
	}
	
	//Adiciona os  possiveis Status de Erro para o método DELETE
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisição inválida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	// Modifica informações no cabeçalho da documentação
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("ExtendsFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("Digidal Systems", "https://www.digivalSystems.com", "contato@digidal.systems.com"))
				.build();
	}

	// Define onde estão os arquivos css
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	

}
