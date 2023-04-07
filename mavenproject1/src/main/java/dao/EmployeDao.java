/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import dao.JpaUtil;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.Eleve;
import metier.Employe;

/**
 *
 * @author rsimar
 */
public class EmployeDao {
    public void create(Employe emp){
        JpaUtil.obtenirContextePersistance().persist(emp);
    }
    
    public void delete(Employe emp){
        JpaUtil.obtenirContextePersistance().remove(emp);
    }
    
    public Employe update(Employe emp){
        return JpaUtil.obtenirContextePersistance().merge(emp);
    }
    
    public Employe findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    public List<Employe> listAllEmployees() {
        String s = "SELECT e FROM Employe e ORDER BY e.nom";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Employe.class);
        return query.getResultList();
    }
    
    public Employe authenticateEmployee(String login, String mdp) {
        String s = "SELECT e FROM Employe e WHERE e.mdp = :mdp AND e.login = :login";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Employe.class);
        query.setParameter("login", login);
        query.setParameter("mdp", mdp);
        return (Employe)query.getSingleResult();
    }
    
    
    
}
