/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tetablate file, choose Tools | Tetablates
 * and open the tetablate in the editor.
 */
package dao;

import metier.Etablissement;

/**
 *
 * @authors Raphaël SIMAR & Lina Borg : B3129 
 * Dominque DROUILLY TORRES : Étudiante d'échange
 */
public class EtablissementDao {
    
    public void create(Etablissement etab){
        JpaUtil.obtenirContextePersistance().persist(etab);
    }
    
    public void delete(Etablissement etab){
        JpaUtil.obtenirContextePersistance().remove(etab);
    }
    
    public Etablissement update(Etablissement etab){
        return JpaUtil.obtenirContextePersistance().merge(etab);
    }
    
    public Etablissement findEtablissementById(String uai){
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class, uai);
    }
    
}
