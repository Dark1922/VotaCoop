package com.br.VotaCoop.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@Schema(name = "Problema406")
public class Problem406 {

    @Schema(example = "406")
    private Integer status;

    @Schema(example = "2023-01-03T16:39:13Z")
    private OffsetDateTime timestamp;

    @Schema(example = "https://localhost:8080/mensagem-imcompreensivel")
    private String type;

    @Schema(example = "Mensagem incompreensível")
    private String title;

    @Schema(example = "Recurso não possui representação que poderia ser aceita pelo consumidor")
    private String detail;

    @Schema(example = "Recurso não possui representação que poderia ser aceita pelo consumidor")
    private String userMessage;

    @Schema(example = "Objetos ou campos que geraram o erro")
    private List<Object> objects;


    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "preco")
        private String name;

        @Schema(example = "Opreço é obrigatório")
        private String userMessage;
    }
}