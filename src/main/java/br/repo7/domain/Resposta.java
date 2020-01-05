package br.repo7.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import br.repo7.domain.enumeration.TipoResposta;

import br.repo7.domain.enumeration.TipoVF;

/**
 * A Resposta.
 */
@Entity
@Table(name = "resposta")
public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoResposta tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "valor_vf")
    private TipoVF valorVF;

    @Column(name = "valor_texto")
    private String valorTexto;

    @Column(name = "valor_n")
    private Long valorN;

    @Column(name = "valor_z")
    private Long valorZ;

    @Column(name = "valor_q", precision = 21, scale = 2)
    private BigDecimal valorQ;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoResposta getTipo() {
        return tipo;
    }

    public Resposta tipo(TipoResposta tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoResposta tipo) {
        this.tipo = tipo;
    }

    public TipoVF getValorVF() {
        return valorVF;
    }

    public Resposta valorVF(TipoVF valorVF) {
        this.valorVF = valorVF;
        return this;
    }

    public void setValorVF(TipoVF valorVF) {
        this.valorVF = valorVF;
    }

    public String getValorTexto() {
        return valorTexto;
    }

    public Resposta valorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
        return this;
    }

    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    public Long getValorN() {
        return valorN;
    }

    public Resposta valorN(Long valorN) {
        this.valorN = valorN;
        return this;
    }

    public void setValorN(Long valorN) {
        this.valorN = valorN;
    }

    public Long getValorZ() {
        return valorZ;
    }

    public Resposta valorZ(Long valorZ) {
        this.valorZ = valorZ;
        return this;
    }

    public void setValorZ(Long valorZ) {
        this.valorZ = valorZ;
    }

    public BigDecimal getValorQ() {
        return valorQ;
    }

    public Resposta valorQ(BigDecimal valorQ) {
        this.valorQ = valorQ;
        return this;
    }

    public void setValorQ(BigDecimal valorQ) {
        this.valorQ = valorQ;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resposta)) {
            return false;
        }
        return id != null && id.equals(((Resposta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resposta{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", valorVF='" + getValorVF() + "'" +
            ", valorTexto='" + getValorTexto() + "'" +
            ", valorN=" + getValorN() +
            ", valorZ=" + getValorZ() +
            ", valorQ=" + getValorQ() +
            "}";
    }
}
