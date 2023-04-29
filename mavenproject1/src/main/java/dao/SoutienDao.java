/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.Eleve;
import metier.Intervenant;
import metier.Soutien;

/**
 *
 * @author rsimar
 */
public class SoutienDao {

    public void create(Soutien s){
        JpaUtil.obtenirContextePersistance().persist(s);
    }
    
    public void delete(Soutien s){
        JpaUtil.obtenirContextePersistance().remove(s);
    }
    
    public Soutien update(Soutien s){
        return JpaUtil.obtenirContextePersistance().merge(s);
    }
    
    public Soutien findSoutienById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Soutien.class, id);
    }
    
    public List<Soutien> listSoutienByEleve(Eleve e) {
        String s = "SELECT s FROM Soutien s WHERE s.eleve = :e";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Soutien.class);
        query.setParameter("e", e);
        return query.getResultList();
    }
    
    public List<Soutien> listSoutienByIntervenants(Intervenant i) {
        String s = "SELECT s FROM Soutien s WHERE s.intervenant = :i";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Soutien.class);
        query.setParameter("i", i);
        return query.getResultList();
    }
    
    public List<Soutien> listSoutienByDate(Date d) {
        String s = "SELECT s FROM Soutien s WHERE s.date = :d";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Soutien.class);
        query.setParameter("d", d);
        return query.getResultList();
    }
    
}
