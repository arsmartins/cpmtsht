package pt.cpmt.sht.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Estabelecimento.
 */
@Entity
@Table(name = "estabelecimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estabelecimento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "designacao", nullable = false)
    private String designacao;

    @OneToOne
    @JoinColumn(unique = true)
    private Localizacao localizacao;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "estabelecimento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Funcionario> funcionarios = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("estabelecimentos")
    private Empresa empresa;

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

    public Estabelecimento designacao(String designacao) {
        this.designacao = designacao;
        return this;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public Estabelecimento localizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
        return this;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public Estabelecimento funcionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public Estabelecimento addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setEstabelecimento(this);
        return this;
    }

    public Estabelecimento removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setEstabelecimento(null);
        return this;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Estabelecimento empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        Estabelecimento estabelecimento = (Estabelecimento) o;
        if (estabelecimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estabelecimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estabelecimento{" +
            "id=" + getId() +
            ", designacao='" + getDesignacao() + "'" +
            "}";
    }
}
