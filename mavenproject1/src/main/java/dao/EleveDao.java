/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.Eleve;

/**
 *
 * @author rsimar
 */
public class EleveDao {
    
    public void create(Eleve e){
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    
    public void delete(Eleve e){
        JpaUtil.obtenirContextePersistance().remove(e);
    }
    
    public Eleve update(Eleve e){
        return JpaUtil.obtenirContextePersistance().merge(e);
    }
    
    public Eleve findEleveById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, id);
    }
    
    public Eleve authenticateEleveMail(String mail, String mdp) {
        String s = "SELECT e FROM Eleve e WHERE e.mdp = :mdp AND e.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Eleve.class);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        return (Eleve)query.getSingleResult();
    }
    
    public Eleve authenticateEleveId(Long id, String mdp) {
        String s = "SELECT e FROM Eleve e WHERE e.mdp = :mdp AND e.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Eleve.class);
        query.setParameter("id", id);
        query.setParameter("mdp", mdp);
        return (Eleve)query.getSingleResult();
    }    
    
}
