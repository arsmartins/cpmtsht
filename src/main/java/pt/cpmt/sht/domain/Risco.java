package pt.cpmt.sht.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Task entity.
 * @author The JHipster team.
 */
@ApiModel(description = "Task entity. @author The JHipster team.")
@Entity
@Table(name = "risco")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Risco implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private TipoRisco tipo;

    @ManyToOne
    @JsonIgnoreProperties("riscos")
    private PostoTrabalho postoTrabalho;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Risco title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Risco description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TipoRisco getTipo() {
        return tipo;
    }

    public Risco tipo(TipoRisco tipoRisco) {
        this.tipo = tipoRisco;
        return this;
    }

    public void setTipo(TipoRisco tipoRisco) {
        this.tipo = tipoRisco;
    }

    public PostoTrabalho getPostoTrabalho() {
        return postoTrabalho;
    }

    public Risco postoTrabalho(PostoTrabalho postoTrabalho) {
        this.postoTrabalho = postoTrabalho;
        return this;
    }

    public void setPostoTrabalho(PostoTrabalho postoTrabalho) {
        this.postoTrabalho = postoTrabalho;
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
        Risco risco = (Risco) o;
        if (risco.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), risco.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Risco{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
