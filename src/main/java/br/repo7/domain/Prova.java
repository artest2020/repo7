package br.repo7.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Prova.
 */
@Entity
@Table(name = "prova")
public class Prova implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ano", nullable = false)
    private Long ano;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "data_hora_inicio")
    private Instant dataHoraInicio;

    @Column(name = "data_hora_fim")
    private Instant dataHoraFim;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Instituicao instituicao;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Edital edital;

    @OneToMany(mappedBy = "prova")
    private Set<Questao> listaQuestaos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAno() {
        return ano;
    }

    public Prova ano(Long ano) {
        this.ano = ano;
        return this;
    }

    public void setAno(Long ano) {
        this.ano = ano;
    }

    public String getCidade() {
        return cidade;
    }

    public Prova cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Instant getDataHoraInicio() {
        return dataHoraInicio;
    }

    public Prova dataHoraInicio(Instant dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
        return this;
    }

    public void setDataHoraInicio(Instant dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Instant getDataHoraFim() {
        return dataHoraFim;
    }

    public Prova dataHoraFim(Instant dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
        return this;
    }

    public void setDataHoraFim(Instant dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public Prova instituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
        return this;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public Edital getEdital() {
        return edital;
    }

    public Prova edital(Edital edital) {
        this.edital = edital;
        return this;
    }

    public void setEdital(Edital edital) {
        this.edital = edital;
    }

    public Set<Questao> getListaQuestaos() {
        return listaQuestaos;
    }

    public Prova listaQuestaos(Set<Questao> questaos) {
        this.listaQuestaos = questaos;
        return this;
    }

    public Prova addListaQuestao(Questao questao) {
        this.listaQuestaos.add(questao);
        questao.setProva(this);
        return this;
    }

    public Prova removeListaQuestao(Questao questao) {
        this.listaQuestaos.remove(questao);
        questao.setProva(null);
        return this;
    }

    public void setListaQuestaos(Set<Questao> questaos) {
        this.listaQuestaos = questaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prova)) {
            return false;
        }
        return id != null && id.equals(((Prova) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prova{" +
            "id=" + getId() +
            ", ano=" + getAno() +
            ", cidade='" + getCidade() + "'" +
            ", dataHoraInicio='" + getDataHoraInicio() + "'" +
            ", dataHoraFim='" + getDataHoraFim() + "'" +
            "}";
    }
}
