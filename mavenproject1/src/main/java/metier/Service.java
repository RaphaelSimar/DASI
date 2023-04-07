/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import dao.EleveDao;
import dao.JpaUtil;
import dao.EmployeDao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static util.Message.envoyerMail;

/**
 *
 * @author rsimar
 */
public class Service {

    public Service() {
    }

    public void initialiserEmployes() {

        Employe emp1 = new Employe("FAVRO", "Samuel", "11111", "sfavro");
        emp1.setMdp("mdp1");

        Employe emp2 = new Employe("DEKEW", "Simon", "22222", "sdekew");
        emp2.setMdp("mdp2");

        Employe emp3 = new Employe("LOU", "Flavien", "33333", "flou");
        emp3.setMdp("mdp3");

        Employe emp4 = new Employe("GUOGUEN", "Gabriela", "44444", "gguoguen");
        emp4.setMdp("mdp4");

        Employe emp5 = new Employe("HERNENDEZ", "Vincent", "55555", "vhernendez");
        emp5.setMdp("mdp5");

        recruter(emp1);
        recruter(emp2);
        recruter(emp3);
        recruter(emp4);
        recruter(emp5);
    }

    public Employe recruter(Employe emp) {

        EmployeDao edao = new EmployeDao();

        try {

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            edao.create(emp);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès recruter" + emp);
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;
    }

    public Employe trouverEmployeParId(Long id) {
        EmployeDao edao = new EmployeDao();
        Employe emp = new Employe();

        try {

            JpaUtil.creerContextePersistance();
            emp = edao.findById(id);
            System.out.println("Trace : succès find " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;

    }

    public List<Employe> listerTousEmployes() {
        EmployeDao edao = new EmployeDao();
        List<Employe> emp;

        try {

            JpaUtil.creerContextePersistance();
            emp = edao.listAllEmployees();
            System.out.println("Trace : succès lister tous les employés");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;

    }

    public Employe authentifierEmploye(String login, String mdp) {
        EmployeDao edao = new EmployeDao();
        Employe emp = new Employe();

        try {

            JpaUtil.creerContextePersistance();
            emp = edao.authenticateEmployee(login, mdp);
            System.out.println("Trace : succès authentification " + emp.getLogin());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            emp = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;

    }

    public boolean inscriptionEleve(Eleve eleve) {
        EleveDao edao = new EleveDao();
        Eleve e = eleve;
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            edao.create(e);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès inscription eleve" + e);
            envoyerMail("toto@mail.com", e.getMail(), "Confirmation inscription", "Votre inscription à Instruct'IF est validée.");
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            envoyerMail("toto@mail.com", e.getMail(), "Echec inscription", "Votre inscription à Instruct'IF a malheureusement rencontré une erreur. Merci de réessayer.");
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public Eleve trouverEleveParId(Long id) {
        EleveDao edao = new EleveDao();
        Eleve e = new Eleve();

        try {

            JpaUtil.creerContextePersistance();
            e = edao.findEleveById(id);
            System.out.println("Trace : succès find " + id);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    public Eleve authentifierEleveMail(String mail, String mdp) {
        EleveDao edao = new EleveDao();
        Eleve e = new Eleve();

        try {

            JpaUtil.creerContextePersistance();
            e = edao.authenticateEleveMail(mail, mdp);
            System.out.println("Trace : succès authentification " + e.getMail());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    public Eleve authentifierEleveId(Long id, String mdp) {
        EleveDao edao = new EleveDao();
        Eleve e = new Eleve();

        try {

            JpaUtil.creerContextePersistance();
            e = edao.authenticateEleveId(id, mdp);
            System.out.println("Trace : succès authentification " + e.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

}
