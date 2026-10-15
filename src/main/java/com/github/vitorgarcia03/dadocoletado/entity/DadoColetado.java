package com.github.vitorgarcia03.dadocoletado.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "dado_coletado")
public class DadoColetado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dado_id")
    private Long dadoId;
    @Column(name = "sonda_nome", nullable = false, length = 100)
    private String sondaNome;
    @Column(name = "agencia_espacial", nullable = false, length = 100)
    private String agenciaEspacial;
    @Column(name = "data_coleta", nullable = false)
    private LocalDate dataColeta;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dado", nullable = false, length = 30)
    private TipoDado tipoDado;
    @Column(name = "valor_medido", nullable = false, precision = 19, scale = 4)
    private BigDecimal valorMedido;
    @Column(name = "unidade_medida", nullable = false, length = 100)
    private String unidadeMedida;

    public DadoColetado() {
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
}
