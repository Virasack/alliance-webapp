package com.webapp.alliance.model;

/**
 * Created by Nikos on 21/07/2018.
 */

import javax.persistence.*;

@Entity
@Table(name = "biens")
public class Bien extends Audit {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "ville")
    private String ville;

    @Column(name = "surface")
    private Long surface;

    @Column(name = "prix")
    private Long prix;

    public Bien() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Long getSurface() {
        return surface;
    }

    public void setSurface(Long surface) {
        this.surface = surface;
    }

    public Long getPrix() {
        return prix;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }
}
