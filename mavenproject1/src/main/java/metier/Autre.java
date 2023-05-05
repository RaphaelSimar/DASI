/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import javax.persistence.Entity;

/**
 *
 * @authors Raphaël SIMAR & Lina Borg : B3129 
 * Dominque DROUILLY TORRES : Étudiante d'échange
 */
@Entity
public class Autre extends Intervenant {
    
    private String activite;

    public Autre() {
    }

    public Autre(String activite, String login, String nom, String prenom, Integer niveau_min, Integer niveau_max, String telephone, String mail, String mdp, Boolean disponible, Integer nbInterventions) {
        super(login, nom, prenom, niveau_min, niveau_max, telephone, mail, mdp, disponible, nbInterventions);
        this.activite = activite;
    }

    @Override
    public String toString() {
        return super.toString() + " - Type 'Autre' : " + "activite=" + activite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
    
}