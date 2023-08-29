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
@Schema(name = "Problema404")
public class Problem404 {

    @Schema(example = "404")
    private Integer status;

    @Schema(example = "2023-01-04T20:28:22-03:00")
    private OffsetDateTime timestamp;

    @Schema(example = "https://localhost:8080/recurso-nao-encontrado")
    private String type;

    @Schema(example = "Recurso não encontrado")
    private String title;

    @Schema(example = "O recurso x, que você tentou acessar, é inexistente.")
    private String detail;

    @Schema(example = "O recurso x, que você tentou acessar, é inexistente.")
    private String userMessage;

    @Schema(example = "Objetos ou campos que geraram o erro")
    private List<Object> objects;


    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "cpf")
        private String name;

        @Schema(example = " cpf é obrigatório")
        private String userMessage;
    }
}