/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author rsimar
 */
@Entity
public class Etudiant extends Intervenant{
    
    private String universite;
    private String specialite;

    public Etudiant() {
    }
    
    public Etudiant(String universite, String specialite, String login, String nom, String prenom, Integer niveau_min, Integer niveau_max, String telephone, String mail, String mdp, Boolean disponible, Integer nbInterventions) {
        super(login, nom, prenom, niveau_min, niveau_max, telephone, mail, mdp, disponible, nbInterventions);
        this.universite = universite;
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return super.toString() + " - Type 'Etudiant' : " + "universite=" + universite + ", specialite=" + specialite;
    }

    public String getUniversite() {
        return universite;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

}