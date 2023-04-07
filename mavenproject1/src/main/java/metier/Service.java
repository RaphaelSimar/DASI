/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import dao.EducNetApi;
import dao.EleveDao;
import dao.JpaUtil;
import dao.EmployeDao;
import dao.EtablissementDao;
import java.io.IOException;
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

    public boolean inscriptionEleve(Eleve eleve, String uai) {
        EleveDao edao = new EleveDao();
        EtablissementDao etabdao = new EtablissementDao();
        Etablissement etab;
        boolean result = false;
        boolean etab_found = true;

        try {
            JpaUtil.creerContextePersistance();
            etab = etabdao.findEtablissementById(uai);
            //Vérifie l'existence de l'étab dans la BD
            if (etab == null) {
                etab_found = false;
                etab = creerEtablissementApi(uai, eleve.getNiveau());
            }
            //Vérifie si l'étab a bien été créé avec l'API
            if (etab == null) {
                System.out.println("Trace : échec inscription eleve - établissement inconnu" + uai);
            } else {
                eleve.setEtablissement(etab);

                JpaUtil.ouvrirTransaction();
                edao.create(eleve);
                if (!etab_found) {
                    etabdao.create(etab);
                }
                JpaUtil.validerTransaction();

                System.out.println("Trace : succès inscription eleve" + eleve);

                result = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        if (result) {
            envoyerMail("toto@mail.com", eleve.getMail(), "Confirmation inscription", "Votre inscription à Instruct'IF est validée.");
        } else {
            envoyerMail("toto@mail.com", eleve.getMail(), "Echec inscription", "Votre inscription à Instruct'IF a malheureusement rencontré une erreur. Merci de réessayer.");
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

    /* -------------------ETABLISSEMENTS ------------------- */
    public Etablissement trouverEtablissementParUai(String uai) {
        EtablissementDao edao = new EtablissementDao();
        Etablissement e;

        try {

            JpaUtil.creerContextePersistance();
            e = edao.findEtablissementById(uai);
            System.out.println("Trace : succès find " + uai);

        } catch (Exception ex) {
            ex.printStackTrace();

            e = null;

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    private Etablissement creerEtablissementApi(String uai, Integer niveau) {
        Etablissement e;
        EducNetApi api = new EducNetApi();

        try {
            List<String> result;
            if (niveau < 3) {
                result = api.getInformationLycee(uai);
            } else {
                result = api.getInformationCollege(uai);
            }
            // List<String> result = api.getInformationLycee("0690132U");
            if (result != null) {
                String nom = result.get(1);
                String secteur = result.get(2);
                String insee = result.get(3);
                String commune = result.get(4);
                String codeDepartement = result.get(5);
                String departement = result.get(6);
                String academie = result.get(7);
                String ips = result.get(8);

                e = new Etablissement(uai, nom, secteur, commune, ips, academie, departement, codeDepartement, insee);

            } else {
                e = null;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            e = null;
        }

        return e;

    }

    public boolean ajouterEtablissement(Etablissement e) {
        EtablissementDao edao = new EtablissementDao();
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            edao.create(e);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout établisement" + e);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

}
