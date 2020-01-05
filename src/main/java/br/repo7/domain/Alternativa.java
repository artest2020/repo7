package br.repo7.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Alternativa.
 */
@Entity
@Table(name = "alternativa")
public class Alternativa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ordem")
    private Integer ordem;

    @OneToMany(mappedBy = "alternativa")
    private Set<Frase> listaFrases = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("listaAlternativas")
    private Questao questao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public Alternativa ordem(Integer ordem) {
        this.ordem = ordem;
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Set<Frase> getListaFrases() {
        return listaFrases;
    }

    public Alternativa listaFrases(Set<Frase> frases) {
        this.listaFrases = frases;
        return this;
    }

    public Alternativa addListaFrase(Frase frase) {
        this.listaFrases.add(frase);
        frase.setAlternativa(this);
        return this;
    }

    public Alternativa removeListaFrase(Frase frase) {
        this.listaFrases.remove(frase);
        frase.setAlternativa(null);
        return this;
    }

    public void setListaFrases(Set<Frase> frases) {
        this.listaFrases = frases;
    }

    public Questao getQuestao() {
        return questao;
    }

    public Alternativa questao(Questao questao) {
        this.questao = questao;
        return this;
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alternativa)) {
            return false;
        }
        return id != null && id.equals(((Alternativa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Alternativa{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            "}";
    }
}
