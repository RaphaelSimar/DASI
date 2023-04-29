/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author rsimar
 */
@Entity
public class Etablissement {
    
    @Id
    private String uai;
    
    private String nom;
    private String secteur;
    private String commune;
    private String ips;
    private String academie;
    private String departement;
    private String codeDepartement;
    private String insee;

    public Etablissement() {
    }

    public Etablissement(String uai, String nom, String secteur, String commune, String ips, String academie, String departement, String codeDepartement, String insee) {
        this.uai = uai;
        this.nom = nom;
        this.secteur = secteur;
        this.commune = commune;
        this.ips = ips;
        this.academie = academie;
        this.departement = departement;
        this.codeDepartement = codeDepartement;
        this.insee = insee;
    }

    public String getUai() {
        return uai;
    }

    public String getNom() {
        return nom;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getCommune() {
        return commune;
    }

    public String getIps() {
        return ips;
    }

    public String getAcademie() {
        return academie;
    }

    public String getDepartement() {
        return departement;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public String getInsee() {
        return insee;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public void setAcademie(String academie) {
        this.academie = academie;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public void setInsee(String insee) {
        this.insee = insee;
    }

    @Override
    public String toString() {
        return "Etablissement{" + "uai=" + uai + ", nom=" + nom + ", secteur=" + secteur + ", commune=" + commune + ", ips=" + ips + ", academie=" + academie + ", departement=" + departement + ", codeDepartement=" + codeDepartement + ", insee=" + insee + '}';
    }
    
}
