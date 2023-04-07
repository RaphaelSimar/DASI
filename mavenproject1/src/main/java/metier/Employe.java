package metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rsimar
 */
@Entity
public class Employe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String tel_pro;
    private String login;
    private String mdp;
    
    public Employe(){
    }
    
    public Employe (String nom, String prenom, String tel_pro, String login){
        this.nom = nom;
        this.prenom = prenom;
        this.tel_pro = tel_pro;
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel_pro() {
        return tel_pro;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel_pro(String tel_pro) {
        this.tel_pro = tel_pro;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return "Employe:" + "id=" + id + "; nom=" + nom + "; prenom=" + prenom + "; tel_pro=" + tel_pro + "; login=" + login + "; mdp=" + mdp;
    }
    
    
    
}
