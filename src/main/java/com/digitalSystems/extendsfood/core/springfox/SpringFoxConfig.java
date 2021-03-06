package com.digitalSystems.extendsfood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.auth.policy.Resource;
import com.digitalSystems.extendsfood.api.exceptionHandler.Problem;
import com.digitalSystems.extendsfood.api.model.CategoriaProdutoResumoModel;
import com.digitalSystems.extendsfood.api.model.CidadeModel;
import com.digitalSystems.extendsfood.api.model.CozinhaModel;
import com.digitalSystems.extendsfood.api.model.EstadoModel;
import com.digitalSystems.extendsfood.api.model.FormaPagamentoModel;
import com.digitalSystems.extendsfood.api.model.GrupoModel;
import com.digitalSystems.extendsfood.api.model.PedidoResumoModel;
import com.digitalSystems.extendsfood.api.model.PermissaoModel;
import com.digitalSystems.extendsfood.api.model.ProdutoModel;
import com.digitalSystems.extendsfood.api.model.RestauranteBasicoModel;
import com.digitalSystems.extendsfood.api.model.UsuarioModel;
import com.digitalSystems.extendsfood.api.openapi.model.CategoriasProdutoResumoModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.CidadesModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.CozinhasModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.EstadosModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.FormaPagamentoModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.GruposModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.LinksModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.PageableModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.PedidosResumoModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.PermissoesModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.ProdutosModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.RestaurantesBasicoModelOpenApi;
import com.digitalSystems.extendsfood.api.openapi.model.UsuariosModelOpenApi;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //Habilita o suporte ao Swagger 2
@Import(BeanValidatorPluginsConfiguration.class) // S??o beans que v??o interpretar as anota????es de valida????o como,
													// por exempro, @notnull e adicionar ao Json.
public class SpringFoxConfig implements WebMvcConfigurer {

	private TypeResolver typeResolver = new TypeResolver();

	@Bean
	public Docket apiDocket() {

		// Configura????es para gerar o Json da documenta????o
		return new Docket(DocumentationType.SWAGGER_2)

				.select()// Seleciona os endpoints que devem ser expostos na defini????o do json
				.apis(RequestHandlerSelectors.basePackage("com.digitalSystems.extendsfood")) // escaneie tudo que
																								// estiver dentro desse
																								// pacote e adicione no
																								// JSON
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)// Desabilita os Codigos de erros padr??o
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())// Descreve os status de resposta
																						// de erro globalmente nos
																						// m??todos
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())

				.additionalModels(typeResolver.resolve(Problem.class))// Adiciona o Problem no Modelo de representa????o
				
				.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
						Resource.class, File.class, InputStream.class)
				
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)// substitui o Pageable pelo
																					// PageableModelOpenApi
				// Faz a substitui????o do Page<PedidoResumoModel> para
				// PedidosResumoModelOpenApi, ou seja, ao inv??z de retornar um Page
				// com uma lista de pedidos, vai retornar PedidosResumoModelOpenApi com
				// uma lista de cozinha model.
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CategoriaProdutoResumoModel.class),
						CategoriasProdutoResumoModelOpenApi.class))
				
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeModel.class), CidadesModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CozinhaModel.class), CozinhasModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoModel.class), EstadosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
						FormaPagamentoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoModel.class), GruposModelOpenApi.class))
				
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
								PermissoesModelOpenApi.class))
				
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
								PedidosResumoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, ProdutoModel.class), ProdutosModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
						RestaurantesBasicoModelOpenApi.class))
				
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioModel.class), UsuariosModelOpenApi.class))

				.securitySchemes(Arrays.asList(securityScheme()))//Descreve a tecnica de segunran??a usada para proteger a API
				.securityContexts(Arrays.asList(securityContext()))
				
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades"))
				.tags(new Tag("Categorias dos Produtos", "Gerencia as Categorias dos Produtos"))
				.tags(new Tag("Cozinha", "Gerencia as Cozinha"))
				.tags(new Tag("Dias da Semana", "Gerencia os Dias da Semana"))
				.tags(new Tag("Estados", "Gerencia os Estados")).tags(new Tag("Pedidos", "Gerencia os Pedidos"))
				.tags(new Tag("Formas de Pagamento", "Gerencia as Formas de Pagamento"))
				.tags(new Tag("Grupos", "Gerencia os Grupos")).tags(new Tag("Usu??rios", "Gerencia os Usu??rios"))
				.tags(new Tag("Restaurantes", "Gerencia os Restaurantes"))
				.tags(new Tag("Produtos", "Gerencia os Produtos"))
				.tags(new Tag("Estat??sticas", "Estat??sticas do ExtendsFood"));
	}
	
	private SecurityScheme securityScheme() {
		return new OAuthBuilder()
				.name("ExtendsFood")
				.grantTypes(grantTypes())
				.scopes(scopes())
				.build();
	}
	
	private SecurityContext securityContext() {
		var securityReference = SecurityReference.builder()
				.reference("ExtendsFood")
				.scopes(scopes().toArray(new AuthorizationScope[0]))
				.build();
		
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(securityReference))
				.build();
	}
	
	private List<GrantType> grantTypes(){
		return Arrays.asList(new ResourceOwnerPasswordCredentialsGrant("/oauth/token"));
	}
	
	private List<AuthorizationScope> scopes(){
		return Arrays.asList(new AuthorizationScope("READ", "Acesso leitura"),
				new AuthorizationScope("WRITE", "Acesso escrita"));
	}

	// Adiciona os possiveis Status de Erro para o m??todo GET
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
				.build(),
				
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
				.build());
	}

	// Adiciona os possiveis Status de Erro para os m??todos POST e PUT
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
				.build(),
				
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno no servidor")
					.responseModel(new ModelRef("Problema"))
				.build(),
				
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso n??o possui representa????o que poderia ser aceita pelo consumidor")
				.build(),
				
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisi????o recusada porque o corpo est?? em um formato n??o suportado")
					.responseModel(new ModelRef("Problema"))
				.build());
	}

	// Adiciona os possiveis Status de Erro para o m??todo DELETE
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisi????o inv??lida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
				.build(),
				
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
				.build());
	}

	// Modifica informa????es no cabe??alho da documenta????o
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("ExtendsFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(
						new Contact("Digidal Systems", "https://www.digivalSystems.com", "contato@digidal.systems.com"))
				.build();
	}

	// Define onde est??o os arquivos css
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
