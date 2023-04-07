/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rsimar
 */
@Entity
public class Eleve {

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nomEtablissement;
    private String niveau;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String mail;
    private String mdp;
    private String adressePostale;

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public Long getId() {
        return id;
    }

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public String getNiveau() {
        return niveau;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    @Override
    public String toString() {
        return "Eleve{" + "dateNaissance=" + dateNaissance + ", id=" + id + ", nomEtablissement=" + nomEtablissement + ", niveau=" + niveau + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", mdp=" + mdp + ", adressePostale=" + adressePostale + '}';
    }

    public Eleve() {
    }

    public Eleve(Date dateNaissance, String nomEtablissement, String niveau, String nom, String prenom, String mail, String mdp, String adressePostale) {
        this.dateNaissance = dateNaissance;
        this.nomEtablissement = nomEtablissement;
        this.niveau = niveau;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.adressePostale = adressePostale;
    }

}
