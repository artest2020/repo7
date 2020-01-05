package br.repo7.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Questao.
 */
@Entity
@Table(name = "questao")
public class Questao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ordem")
    private Integer ordem;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Enunciado enunciado;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pergunta pergunta;

    @OneToMany(mappedBy = "questao")
    private Set<Alternativa> listaAlternativas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("listaQuestaos")
    private Prova prova;

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

    public Questao ordem(Integer ordem) {
        this.ordem = ordem;
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Enunciado getEnunciado() {
        return enunciado;
    }

    public Questao enunciado(Enunciado enunciado) {
        this.enunciado = enunciado;
        return this;
    }

    public void setEnunciado(Enunciado enunciado) {
        this.enunciado = enunciado;
    }

    public Pergunta getPergunta() {
        return pergunta;
    }

    public Questao pergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
        return this;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public Set<Alternativa> getListaAlternativas() {
        return listaAlternativas;
    }

    public Questao listaAlternativas(Set<Alternativa> alternativas) {
        this.listaAlternativas = alternativas;
        return this;
    }

    public Questao addListaAlternativa(Alternativa alternativa) {
        this.listaAlternativas.add(alternativa);
        alternativa.setQuestao(this);
        return this;
    }

    public Questao removeListaAlternativa(Alternativa alternativa) {
        this.listaAlternativas.remove(alternativa);
        alternativa.setQuestao(null);
        return this;
    }

    public void setListaAlternativas(Set<Alternativa> alternativas) {
        this.listaAlternativas = alternativas;
    }

    public Prova getProva() {
        return prova;
    }

    public Questao prova(Prova prova) {
        this.prova = prova;
        return this;
    }

    public void setProva(Prova prova) {
        this.prova = prova;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questao)) {
            return false;
        }
        return id != null && id.equals(((Questao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Questao{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            "}";
    }
}
