package pt.cpmt.sht.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "num_cc")
    private String numCC;

    @Column(name = "num_if")
    private String numIF;

    @Column(name = "num_ss")
    private String numSS;

    @Column(name = "data_entrada")
    private Instant dataEntrada;

    @Column(name = "data_saida")
    private Instant dataSaida;

    @ManyToOne
    @JsonIgnoreProperties("funcionarios")
    private Estabelecimento estabelecimento;

    @OneToMany(mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PostoTrabalho> postoTrabalhos = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("funcionarios")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Funcionario fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Funcionario firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Funcionario lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Funcionario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Funcionario telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNumCC() {
        return numCC;
    }

    public Funcionario numCC(String numCC) {
        this.numCC = numCC;
        return this;
    }

    public void setNumCC(String numCC) {
        this.numCC = numCC;
    }

    public String getNumIF() {
        return numIF;
    }

    public Funcionario numIF(String numIF) {
        this.numIF = numIF;
        return this;
    }

    public void setNumIF(String numIF) {
        this.numIF = numIF;
    }

    public String getNumSS() {
        return numSS;
    }

    public Funcionario numSS(String numSS) {
        this.numSS = numSS;
        return this;
    }

    public void setNumSS(String numSS) {
        this.numSS = numSS;
    }

    public Instant getDataEntrada() {
        return dataEntrada;
    }

    public Funcionario dataEntrada(Instant dataEntrada) {
        this.dataEntrada = dataEntrada;
        return this;
    }

    public void setDataEntrada(Instant dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Instant getDataSaida() {
        return dataSaida;
    }

    public Funcionario dataSaida(Instant dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public void setDataSaida(Instant dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public Funcionario estabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
        return this;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public Set<PostoTrabalho> getPostoTrabalhos() {
        return postoTrabalhos;
    }

    public Funcionario postoTrabalhos(Set<PostoTrabalho> postoTrabalhos) {
        this.postoTrabalhos = postoTrabalhos;
        return this;
    }

    public Funcionario addPostoTrabalho(PostoTrabalho postoTrabalho) {
        this.postoTrabalhos.add(postoTrabalho);
        postoTrabalho.setFuncionario(this);
        return this;
    }

    public Funcionario removePostoTrabalho(PostoTrabalho postoTrabalho) {
        this.postoTrabalhos.remove(postoTrabalho);
        postoTrabalho.setFuncionario(null);
        return this;
    }

    public void setPostoTrabalhos(Set<PostoTrabalho> postoTrabalhos) {
        this.postoTrabalhos = postoTrabalhos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Funcionario empresa(Empresa empresa) {
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
        Funcionario funcionario = (Funcionario) o;
        if (funcionario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", numCC='" + getNumCC() + "'" +
            ", numIF='" + getNumIF() + "'" +
            ", numSS='" + getNumSS() + "'" +
            ", dataEntrada='" + getDataEntrada() + "'" +
            ", dataSaida='" + getDataSaida() + "'" +
            "}";
    }
}
