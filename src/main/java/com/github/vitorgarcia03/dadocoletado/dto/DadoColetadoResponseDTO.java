package com.github.vitorgarcia03.dadocoletado.dto;

import com.github.vitorgarcia03.dadocoletado.entity.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DadoColetadoResponseDTO {

    private Long dadoId;

    private String sondaNome;

    private String agenciaEspacial;

    private LocalDate dataColeta;

    private TipoDado tipoDado;

    private BigDecimal valorMedido;

    private String unidadeMedida;

    public DadoColetadoResponseDTO() {
    }

    public DadoColetadoResponseDTO(Long dadoId, String sondaNome, String agenciaEspacial, LocalDate dataColeta, TipoDado tipoDado, BigDecimal valorMedido, String unidadeMedida) {

        this.dadoId = dadoId;
        this.sondaNome = sondaNome;
        this.agenciaEspacial = agenciaEspacial;
        this.dataColeta = dataColeta;
        this.tipoDado = tipoDado;
        this.valorMedido = valorMedido;
        this.unidadeMedida = unidadeMedida;
    }

    public Long getDadoId() {

        return dadoId;
    }

    public void setDadoId(Long dadoId) {

        this.dadoId = dadoId;
    }

    public String getSondaNome() {

        return sondaNome;
    }

    public void setSondaNome(String sondaNome) {

        this.sondaNome = sondaNome;
    }

    public String getAgenciaEspacial() {

        return agenciaEspacial;
    }

    public void setAgenciaEspacial(String agenciaEspacial) {

        this.agenciaEspacial = agenciaEspacial;
    }

    public LocalDate getDataColeta() {

        return dataColeta;
    }

    public void setDataColeta(LocalDate dataColeta) {

        this.dataColeta = dataColeta;
    }

    public TipoDado getTipoDado() {

        return tipoDado;
    }

    public void setTipoDado(TipoDado tipoDado) {

        this.tipoDado = tipoDado;
    }

    public BigDecimal getValorMedido() {

        return valorMedido;
    }

    public void setValorMedido(BigDecimal valorMedido) {

        this.valorMedido = valorMedido;
    }

    public String getUnidadeMedida() {

        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {

        this.unidadeMedida = unidadeMedida;
    }

    public static DadoColetadoResponseDTO fromEntity(DadoColetado dado) {

        return new DadoColetadoResponseDTO(dado.getDadoId(), dado.getSondaNome(), dado.getAgenciaEspacial(),
            dado.getDataColeta(), dado.getTipoDado(), dado.getValorMedido(), dado.getUnidadeMedida());
    }
}
