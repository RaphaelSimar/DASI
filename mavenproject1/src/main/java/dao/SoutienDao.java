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
    
    public List<Soutien> listSoutiensByEleve(Eleve e) {
        String s = "SELECT s FROM Soutien s WHERE s.eleve = :e ORDER BY s.debutSoutien desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Soutien.class);
        query.setParameter("e", e);
        return query.getResultList();
    }
    
    public List<Soutien> listSoutiensByIntervenant(Intervenant i) {
        String s = "SELECT s FROM Soutien s WHERE s.intervenant = :i ORDER BY s.debutSoutien desc";
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
    
    public List<Soutien> listAllSoutiens() {
        String s = "SELECT s FROM Soutien s ORDER BY s.emissionDemande";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Soutien.class);
        return query.getResultList();
    }
   
    public Double getMeanIntervenant(Intervenant i){
        String s = "SELECT AVG(s.note) FROM Soutien s WHERE s.intervenant = :i";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("i", i);
        return (Double) query.getSingleResult();
    }

    public Integer[] getLevelsRepartition(Intervenant i){
        Integer repartition[] = {-1,-1,-1,-1,-1,-1,-1};

        String s = "SELECT COUNT(s) FROM Soutien s JOIN s.eleve e WHERE s.intervenant = :i AND e.niveau = :n";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Intervenant.class);
        query.setParameter("i", i);
        int n;
        for(n=6; n>=0; --n){
            query.setParameter("n", n);
            Long longValue = (Long) query.getSingleResult();
            repartition[6-n] = longValue.intValue();
            int indice = 6-n;
            System.out.println("indice i = " + indice);
        }

        return repartition;
    }

}