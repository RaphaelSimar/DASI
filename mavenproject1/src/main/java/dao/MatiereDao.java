/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.Matiere;

/**
 *
 * @author rsimar
 */
public class MatiereDao {

    public void create(Matiere i){
        JpaUtil.obtenirContextePersistance().persist(i);
    }
    
    public void delete(Matiere i){
        JpaUtil.obtenirContextePersistance().remove(i);
    }
    
    public Matiere update(Matiere i){
        return JpaUtil.obtenirContextePersistance().merge(i);
    }
    
    public Matiere findMatiereById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Matiere.class, id);
    }
    
    public List<Matiere> getAllMatieres(){
        String jpql = "SELECT m FROM Matiere m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(jpql,Matiere.class);
        List<Matiere> res = query.getResultList();
        
        return res;
    }
    
}
