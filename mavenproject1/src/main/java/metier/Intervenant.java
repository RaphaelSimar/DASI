/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @authors Raphaël SIMAR & Lina Borg : B3129 
 * Dominque DROUILLY TORRES : Étudiante d'échange
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Intervenant implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String login;
    private String nom;
    private String prenom;
    private Integer niveau_min;
    private Integer niveau_max;
    private String telephone;
    private String mail;
    private String mdp;
    private Boolean disponible;
    private Integer nbInterventions;

    public Intervenant() {
    }
    
    public Intervenant(String login, String nom, String prenom, Integer niveau_min, Integer niveau_max, String telephone, String mail, String mdp, Boolean disponible, Integer nbInterventions) {
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.niveau_min = niveau_min;
        this.niveau_max = niveau_max;
        this.telephone = telephone;
        this.mail = mail;
        this.mdp = mdp;
        this.disponible = disponible;
        this.nbInterventions = nbInterventions;
    }
    
    public void incrementerNbInterventions() {
        this.nbInterventions++;
    }

    public Long getId() {
        return id;
    }
    
    

    public String getLogin() {
        return login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Integer getNiveau_min() {
        return niveau_min;
    }

    public Integer getNiveau_max() {
        return niveau_max;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public Integer getNbInterventions() {
        return nbInterventions;
    }

    public String getNiveau_minString() {
        String niv = null;
        if (this.niveau_min > 2) {
            niv = this.niveau_min + "ème";
        } else {
            switch (niveau_min) {
                case 2:
                    niv = "Seconde";
                    break;
                case 1:
                    niv = "Première";
                    break;
                case 0:
                    niv = "Terminale";
                    break;
            }
        }
        return niv;
    }
    
    public String getNiveau_maxString() {
        String niv = null;
        if (this.niveau_max > 2) {
            niv = this.niveau_max + "ème";
        } else {
            switch (niveau_max) {
                case 2:
                    niv = "Seconde";
                    break;
                case 1:
                    niv = "Première";
                    break;
                case 0:
                    niv = "Terminale";
                    break;
            }
        }
        return niv;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNiveau_min(Integer niveau_min) {
        this.niveau_min = niveau_min;
    }

    public void setNiveau_max(Integer niveau_max) {
        this.niveau_max = niveau_max;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void setNbInterventions(Integer nbInterventions) {
        this.nbInterventions = nbInterventions;
    }

    @Override
    public String toString() {
        return "Intervenant : " + "id=" + id + ", login=" + login + ", nom=" + nom + ", prenom=" + prenom + ", niveau_min=" + niveau_min + ", niveau_max=" + niveau_max + ", telephone=" + telephone + ", mail=" + mail + ", mdp=" + mdp + ", disponible=" + disponible + ", nbInterventions=" + nbInterventions;
    }
    
    

    
}
