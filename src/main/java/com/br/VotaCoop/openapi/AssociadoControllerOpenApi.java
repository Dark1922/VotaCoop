package com.br.VotaCoop.openapi;

import com.br.VotaCoop.api.dto.AssociadoDTO;
import com.br.VotaCoop.api.dto.Input.AssociadoInput;
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

import java.util.List;

@Tag(name = "Associados")
public interface AssociadoControllerOpenApi {

     @Operation(summary = "Lista os Associados")
     ResponseEntity<List<AssociadoDTO>> findAll();

    @PageableParameter
    @Operation(summary = "Lista os associados com paginação")
     ResponseEntity<Page<AssociadoDTO>> listarAssociadosPage(Pageable pageable);

    @Operation(summary = "Busca um associado por Id",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID do associado inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema404"))
                    ),
                    @ApiResponse(responseCode = "406", description = "Formato de Resposta Não Suportado",
                            content = @Content(schema = @Schema(ref = "Problema406"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro interno",
                            content = @Content(schema = @Schema(ref = "Problema500"))
                    )
            })
     ResponseEntity<AssociadoDTO> findById(@Parameter(description = "ID de um associado", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra um associado", description = "Cadastro de um associado, " +
            "necessita de um cpf válido e um nome preenchido.")
     ResponseEntity<AssociadoDTO> saveAssociado(@Parameter( description = "Representeção de um novo associado", required = true)
                                                          AssociadoInput associadoInput);

    @Operation(summary = "Atualiza um associado por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID do associado inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema404"))
                    )
            })
     ResponseEntity<AssociadoDTO> updateAssociado(@Parameter(description = "ID de um associado",
            example = "1", required = true) Long id, AssociadoInput associadoInput);
}
