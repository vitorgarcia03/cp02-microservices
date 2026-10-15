package com.github.vitorgarcia03.dadocoletado.dto;

import com.github.vitorgarcia03.dadocoletado.entity.TipoDado;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DadoColetadoRequestDTO {

    @NotBlank(message = "Nome da sonda é obrigatório")
    @Size(min = 3, max = 100, message = "Nome da sonda deve ter entre 3 e 100 caracteres")
    private String sondaNome;
    @NotBlank(message = "Agência espacial é obrigatória")
    @Size(min = 3, max = 100, message = "Agência espacial deve ter entre 3 e 100 caracteres")
    private String agenciaEspacial;
    @NotNull(message = "Data da coleta é obrigatória")
    @PastOrPresent(message = "Data da coleta não pode estar no futuro")
    private LocalDate dataColeta;
    @NotNull(message = "Tipo de dado é obrigatório")
    private TipoDado tipoDado;
    @NotNull(message = "Valor medido é obrigatório")
    private BigDecimal valorMedido;
    @NotBlank(message = "Unidade de medida é obrigatória")
    @Size(min = 3, max = 100, message = "Unidade de medida deve ter entre 3 e 100 caracteres")
    private String unidadeMedida;

    public DadoColetadoRequestDTO() {
    }

    public DadoColetadoRequestDTO(String sondaNome, String agenciaEspacial, LocalDate dataColeta, TipoDado tipoDado, BigDecimal valorMedido, String unidadeMedida) {

        this.sondaNome = sondaNome;
        this.agenciaEspacial = agenciaEspacial;
        this.dataColeta = dataColeta;
        this.tipoDado = tipoDado;
        this.valorMedido = valorMedido;
        this.unidadeMedida = unidadeMedida;
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
}
