package com.br.VotaCoop.openapi;

import com.br.VotaCoop.api.dto.Input.VotoInput;
import com.br.VotaCoop.api.dto.VotoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Votos")
public interface VotoControllerOpenApi {

    @Operation(summary = "Busca um voto por Id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID do voto inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Voto não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema404"))
                    ),
                    @ApiResponse(responseCode = "406", description = "Formato de Resposta Não Suportado",
                            content = @Content(schema = @Schema(ref = "Problema406"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(ref = "Problema500"))
                    )
            })
    ResponseEntity<VotoDTO> findById(@Parameter(description = "ID de um voto", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra um voto", description = "Cadastro de um voto, " +
            "necessita de um valor válido e uma sessão de votação.")
    ResponseEntity<VotoDTO> saveVoto(@Parameter( description = "Representeção de uma novo voto.", required = true)
                                               VotoInput votoInput);
}
