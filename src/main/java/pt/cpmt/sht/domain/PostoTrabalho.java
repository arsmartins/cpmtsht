package pt.cpmt.sht.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PostoTrabalho.
 */
@Entity
@Table(name = "posto_trabalho")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PostoTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designacao")
    private String designacao;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_fim")
    private Instant dataFim;

    @ManyToOne
    @JsonIgnoreProperties("postoTrabalhos")
    private Funcionario funcionario;

    @OneToMany(mappedBy = "postoTrabalho")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Risco> riscos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public PostoTrabalho designacao(String designacao) {
        this.designacao = designacao;
        return this;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public PostoTrabalho dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFim() {
        return dataFim;
    }

    public PostoTrabalho dataFim(Instant dataFim) {
        this.dataFim = dataFim;
        return this;
    }

    public void setDataFim(Instant dataFim) {
        this.dataFim = dataFim;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public PostoTrabalho funcionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        return this;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Set<Risco> getRiscos() {
        return riscos;
    }

    public PostoTrabalho riscos(Set<Risco> riscos) {
        this.riscos = riscos;
        return this;
    }

    public PostoTrabalho addRisco(Risco risco) {
        this.riscos.add(risco);
        risco.setPostoTrabalho(this);
        return this;
    }

    public PostoTrabalho removeRisco(Risco risco) {
        this.riscos.remove(risco);
        risco.setPostoTrabalho(null);
        return this;
    }

    public void setRiscos(Set<Risco> riscos) {
        this.riscos = riscos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostoTrabalho postoTrabalho = (PostoTrabalho) o;
        if (postoTrabalho.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postoTrabalho.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostoTrabalho{" +
            "id=" + getId() +
            ", designacao='" + getDesignacao() + "'" +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFim='" + getDataFim() + "'" +
            "}";
    }
}
