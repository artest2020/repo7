package br.repo7.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ResolucaoQuestao.
 */
@Entity
@Table(name = "resolucao_questao")
public class ResolucaoQuestao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data_hora_inicio")
    private Instant dataHoraInicio;

    @Column(name = "data_hora_fim")
    private Instant dataHoraFim;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHoraInicio() {
        return dataHoraInicio;
    }

    public ResolucaoQuestao dataHoraInicio(Instant dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
        return this;
    }

    public void setDataHoraInicio(Instant dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Instant getDataHoraFim() {
        return dataHoraFim;
    }

    public ResolucaoQuestao dataHoraFim(Instant dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
        return this;
    }

    public void setDataHoraFim(Instant dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResolucaoQuestao)) {
            return false;
        }
        return id != null && id.equals(((ResolucaoQuestao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResolucaoQuestao{" +
            "id=" + getId() +
            ", dataHoraInicio='" + getDataHoraInicio() + "'" +
            ", dataHoraFim='" + getDataHoraFim() + "'" +
            "}";
    }
}
