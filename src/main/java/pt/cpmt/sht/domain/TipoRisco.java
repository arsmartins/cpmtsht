package pt.cpmt.sht.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TipoRisco.
 */
@Entity
@Table(name = "tipo_risco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoRisco implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designacao")
    private String designacao;

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

    public TipoRisco designacao(String designacao) {
        this.designacao = designacao;
        return this;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
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
        TipoRisco tipoRisco = (TipoRisco) o;
        if (tipoRisco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoRisco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoRisco{" +
            "id=" + getId() +
            ", designacao='" + getDesignacao() + "'" +
            "}";
    }
}
