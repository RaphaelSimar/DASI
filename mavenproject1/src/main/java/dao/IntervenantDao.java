/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.Eleve;
import metier.Intervenant;

/**
 *
 * @author rsimar
 */
public class IntervenantDao {

    public void create(Intervenant i){
        JpaUtil.obtenirContextePersistance().persist(i);
    }
    
    public void delete(Intervenant i){
        JpaUtil.obtenirContextePersistance().remove(i);
    }
    
    public Intervenant update(Intervenant i){
        return JpaUtil.obtenirContextePersistance().merge(i);
    }
    
    public Intervenant findIntervenantById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Intervenant.class, id);
    }
    
    public Intervenant authenticateIntervenantMail(String mail, String mdp) {
        String s = "SELECT i FROM Intervenant i WHERE i.mdp = :mdp AND i.mail = :mail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        return (Intervenant)query.getSingleResult();
    }
    
    public Intervenant authenticateIntervenantId(Long id, String mdp) {
        String s = "SELECT i FROM Intervenant i WHERE i.mdp = :mdp AND i.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("id", id);
        query.setParameter("mdp", mdp);
        return (Intervenant)query.getSingleResult();
    }
    
    public Intervenant authenticateIntervenantLogin(String login, String mdp) {
        String s = "SELECT i FROM Intervenant i WHERE i.mdp = :mdp AND i.login = :login";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("login", login);
        query.setParameter("mdp", mdp);
        return (Intervenant)query.getSingleResult();
    }
    
    public Intervenant findIntervenantSoutien(Eleve e) {
        Integer niveau = e.getNiveau();
        String s = "SELECT i FROM Intervenant i WHERE i.niveau_min >= :niveau AND i.niveau_max <= :niveau AND i.disponible = true ORDER BY i.nbInterventions asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("niveau", niveau);
        List<Intervenant> l = query.getResultList();
        System.out.println("INTERVENANT : " + l);
        return (Intervenant)query.getResultList().get(0);
    }
    
    public List<Intervenant> listAllIntervenants() {
        String s = "SELECT i FROM Intervenant i ORDER BY i.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        return query.getResultList();
    }
    
}
