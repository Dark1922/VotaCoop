package com.br.VotaCoop.core.openapi;

import com.br.VotaCoop.api.exceptionhandler.Problem;
import com.br.VotaCoop.api.exceptionhandler.Problem404;
import com.br.VotaCoop.api.exceptionhandler.Problem406;
import com.br.VotaCoop.api.exceptionhandler.Problem500;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {

    private static final String badRequestResponse = "BadRequestResponse";
    private static final String notFoundResponse = "NotFoundResponse";
    private static final String notAcceptableResponse = "NotAcceptableResponse";
    private static final String internalServerErrorResponse = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPIv1() {
        return new OpenAPI()
                .info(new Info()
                        .title("Crysten API")
                        .version("v1")
                        .description("REST API da Biblioteca Crysten")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org/")
                        )
                        .contact(new Contact()
                                .name("João Victor Martins de Matos")
                                .email("jvdematos004@gmail.com")
                                .url("https://github.com/Dark1922/VotaCoop"))
                )
                .tags(Arrays.asList(
                        new Tag().name("Associados").description("Gerencia os Associados"),
                        new Tag().name("Votos").description("Gerencia os votos"),
                        new Tag().name("Pautas").description("Gerencia as pautas"),
                        new Tag().name("SessaoVotacao").description("Gerencia as sessão de votação")
                )).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                switch (httpMethod) {
                                    case GET:
                                        responses.addApiResponse("406", new ApiResponse().$ref(notAcceptableResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    case POST, PUT:
                                        responses.addApiResponse("400", new ApiResponse().$ref(badRequestResponse));
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                    default:
                                        responses.addApiResponse("500", new ApiResponse().$ref(internalServerErrorResponse));
                                        break;
                                }
                            })
                    );
        };
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemSchema404 = ModelConverters.getInstance().read(Problem404.class);
        Map<String, Schema> problemSchema406 = ModelConverters.getInstance().read(Problem406.class);
        Map<String, Schema> problemSchema500 = ModelConverters.getInstance().read(Problem500.class);

        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemSchema404);
        schemaMap.putAll(problemSchema406);
        schemaMap.putAll(problemSchema500);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        Content content404 = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem404>().$ref("Problema404")));
        Content content406 = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem406>().$ref("Problema406")));
        Content content500 = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<Problem500>().$ref("Problema500")));

        apiResponseMap.put(badRequestResponse, new ApiResponse()
                .description("Requisição inválida")
                .content(content));

        apiResponseMap.put(notFoundResponse, new ApiResponse()
                .description("Recurso não encontrado")
                .content(content404));

        apiResponseMap.put(notAcceptableResponse, new ApiResponse()
                .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                .content(content406));

        apiResponseMap.put(internalServerErrorResponse, new ApiResponse()
                .description("Erro interno no servidor")
                .content(content500));

        return apiResponseMap;
    }

}