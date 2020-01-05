package br.repo7.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import br.repo7.domain.enumeration.TipoVF;

/**
 * A Frase.
 */
@Entity
@Table(name = "frase")
public class Frase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "verdadeira")
    private TipoVF verdadeira;

    @ManyToOne
    @JsonIgnoreProperties("listaFrases")
    private Alternativa alternativa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Frase descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoVF getVerdadeira() {
        return verdadeira;
    }

    public Frase verdadeira(TipoVF verdadeira) {
        this.verdadeira = verdadeira;
        return this;
    }

    public void setVerdadeira(TipoVF verdadeira) {
        this.verdadeira = verdadeira;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public Frase alternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
        return this;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Frase)) {
            return false;
        }
        return id != null && id.equals(((Frase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Frase{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", verdadeira='" + getVerdadeira() + "'" +
            "}";
    }
}
