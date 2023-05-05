/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.Eleve;

/**
 *
 * @authors Raphaël SIMAR & Lina Borg : B3129 
 * Dominque DROUILLY TORRES : Étudiante d'échange
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
    
    public List<Eleve> listAllEleves() {
        String s = "SELECT e FROM Eleve e ORDER BY e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Eleve.class);
        return query.getResultList();
    }
    
}
