package com.github.vitorgarcia03.dadocoletado.controller;

import com.github.vitorgarcia03.dadocoletado.dto.*;
import com.github.vitorgarcia03.dadocoletado.service.DadoColetadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/dados-coletados")
@Tag(name = "Dados coletados", description = "Medições de sondas espaciais")
public class DadoColetadoController {

    private final DadoColetadoService service;

    public DadoColetadoController(DadoColetadoService service) {

        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar dados coletados")
    public List<DadoColetadoResponseDTO> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dado coletado pelo ID")
    public DadoColetadoResponseDTO buscar(@PathVariable Long id) {

        return service.buscar(id);
    }

    @PostMapping
    @Operation(summary = "Cadastrar dado coletado")
    public ResponseEntity<DadoColetadoResponseDTO> criar(@Valid @RequestBody DadoColetadoRequestDTO dto) {

        var response = service.criar(dto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getDadoId())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar todos os campos de um dado coletado")
    public DadoColetadoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody DadoColetadoRequestDTO dto) {

        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir dado coletado")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
