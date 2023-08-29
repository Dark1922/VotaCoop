package com.br.VotaCoop.openapi;

import com.br.VotaCoop.api.dto.Input.PautaInput;
import com.br.VotaCoop.api.dto.PautaDTO;
import com.br.VotaCoop.core.openapi.PageableParameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pautas")
public interface PautaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista as Pautas com paginação")
     ResponseEntity<Page<PautaDTO>> listarPautasPage(Pageable pageable);

    @Operation(summary = "Busca uma pauta por Id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID da pauta inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema404"))
                    ),
                    @ApiResponse(responseCode = "406", description = "Formato de Resposta Não Suportado",
                            content = @Content(schema = @Schema(ref = "Problema406"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(ref = "Problema500"))
                    )
            })
     ResponseEntity<PautaDTO> findById(@Parameter(description = "ID de uma pauta", example = "1", required = true) Long id);

    @PageableParameter
    @Operation(summary = "Lista as Pautas por tema e com paginação")
     ResponseEntity<Page<PautaDTO>> findByPautaPage(@Parameter(description = "Nome de uma pauta",
            example = "teste") String tema, Pageable pageable);

    @Operation(summary = "Cadastra uma pauta", description = "Cadastro de um pauta, " +
            "necessita de um tema e uma descrição preenchido.")
    ResponseEntity<PautaDTO> savePauta(@Parameter(description = "Representeção de uma nova pauta", required = true)
                                     PautaInput pautaInput);
}
