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
@Schema(name = "Problema500")
public class Problem500 {

    @Schema(example = "500")
    private Integer status;

    @Schema(example = "2023-01-03T16:39:13Z")
    private OffsetDateTime timestamp;

    @Schema(example = "https://localhost:8080/erro-de-sistema")
    private String type;

    @Schema(example = "Erro de sistema")
    private String title;

    @Schema(example = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato " + "com o administrador do sistema.")
    private String detail;

    @Schema(example = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato " + "com o administrador do sistema.")
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