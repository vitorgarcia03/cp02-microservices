package com.github.vitorgarcia03.dadocoletado.service;

import com.github.vitorgarcia03.dadocoletado.dto.*;
import com.github.vitorgarcia03.dadocoletado.entity.DadoColetado;
import com.github.vitorgarcia03.dadocoletado.repository.DadoColetadoRepository;
import com.github.vitorgarcia03.dadocoletado.exception.DadoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DadoColetadoService {

    private final DadoColetadoRepository repository;

    public DadoColetadoService(DadoColetadoRepository repository) {

        this.repository = repository;
    }

    public List<DadoColetadoResponseDTO> listar() {

        return repository.findAll(Sort.by("dadoId")).stream().map(DadoColetadoResponseDTO::fromEntity).toList();
    }

    public DadoColetadoResponseDTO buscar(Long id) {

        return DadoColetadoResponseDTO.fromEntity(obter(id));
    }

    @Transactional
    public DadoColetadoResponseDTO criar(DadoColetadoRequestDTO dto) {

        var dado = new DadoColetado();
        preencher(dado, dto);
        return DadoColetadoResponseDTO.fromEntity(repository.save(dado));
    }

    @Transactional
    public DadoColetadoResponseDTO atualizar(Long id, DadoColetadoRequestDTO dto) {

        var dado = obter(id);
        preencher(dado, dto);
        return DadoColetadoResponseDTO.fromEntity(repository.save(dado));
    }

    @Transactional
    public void excluir(Long id) {

        repository.delete(obter(id));
    }

    private DadoColetado obter(Long id) {

        return repository.findById(id).orElseThrow(() -> new DadoNaoEncontradoException(id));
    }

    private void preencher(DadoColetado dado, DadoColetadoRequestDTO dto) {

        dado.setSondaNome(dto.getSondaNome());
        dado.setAgenciaEspacial(dto.getAgenciaEspacial());
        dado.setDataColeta(dto.getDataColeta());
        dado.setTipoDado(dto.getTipoDado());
        dado.setValorMedido(dto.getValorMedido());
        dado.setUnidadeMedida(dto.getUnidadeMedida());
    }
}
