/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import javax.persistence.Entity;

/**
 *
 * @author rsimar
 */
@Entity
public class Enseignant extends Intervenant{
    
    private String typeEtablissement;

    public Enseignant() {
    }

    public Enseignant(String typeEtablissement, String login, String nom, String prenom, Integer niveau_min, Integer niveau_max, String telephone, String mail, String mdp, Boolean disponible, Integer nbInterventions) {
        super(login, nom, prenom, niveau_min, niveau_max, telephone, mail, mdp, disponible, nbInterventions);
        this.typeEtablissement = typeEtablissement;
    }

    @Override
    public String toString() {
        return super.toString() + " - Type 'Enseignant' : " + "typeEtablissement=" + typeEtablissement;
    }

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

}