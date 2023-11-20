package ru.otus.config;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Конфигурация swagger open api.
 */
@Configuration

public class OpenApiConfig {

    private static final String NOT_FOUND = "{"+
            "\"type\": \"about:blank\","+
            "\"title\": \"Not Found\","+
            "\"status\": 404,"+
            "\"detail\": \"description\","+
            "\"instance\": \"/api/v1/url\""+
            "}";

    @Bean
    OpenApiCustomizer addDefaultResponses() {
        Content content = new Content()
                .addMediaType(
                        APPLICATION_JSON_VALUE,
                        new MediaType().example(NOT_FOUND));

        ApiResponse notFoundResponse = new ApiResponse()
                .description("Not Found")
                .content(content);

        ApiResponse notAuthorized = new ApiResponse()
                .description("Not Authorized");

        return openApi ->
                openApi.getPaths()
                        .values()
                        .forEach(pathItem ->
                                pathItem.readOperations()
                                        .forEach(operation ->
                                                operation.getResponses()
                                                        .addApiResponse("404", notFoundResponse)
                                                        .addApiResponse("401", notAuthorized)));
    }

}

