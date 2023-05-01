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
import dao.IntervenantDao;
import dao.MatiereDao;
import dao.SoutienDao;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static util.Message.envoyerMail;
import static util.Message.envoyerNotification;

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
            envoyerMail("contact@instruct.if", eleve.getMail(), "Bienvenue sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", nous te confirmons ton inscription sur le réseau INSTRUCT'IF.\nSi tu as besoin de soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
        } else {
            envoyerMail("contact@instruct.if", eleve.getMail(), "Echec de l'inscription sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", ton insription sur le réseau INSTRUCT'IF a malencontreusement échoué...\nMerci de recommencer ultérieurement.");
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

    public List<Soutien> listerSoutiensEleve(Eleve e) {
        SoutienDao sdao = new SoutienDao();
        List<Soutien> s;

        try {

            JpaUtil.creerContextePersistance();
            s = sdao.listSoutiensByEleve(e);
            System.out.println("Trace : succès lister tous les soutiens de l'élève " + e.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            s = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return s;

    }

    public List<Eleve> listerTousEleves() {
        EleveDao edao = new EleveDao();
        List<Eleve> e;

        try {

            JpaUtil.creerContextePersistance();
            e = edao.listAllEleves();
            System.out.println("Trace : succès lister tous les élèves");
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

    /* -------------------INTERVENANTS------------------- */
    public boolean ajouterIntervenant(Intervenant i) {
        IntervenantDao idao = new IntervenantDao();
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            idao.create(i);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout intervenant" + i);
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

    public Intervenant trouverIntervenantParId(Long id) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i;

        try {

            JpaUtil.creerContextePersistance();
            i = idao.findIntervenantById(id);
            System.out.println("Trace : succès find " + id);

        } catch (Exception ex) {
            ex.printStackTrace();

            i = null;

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;
    }

    public boolean initialiserIntervenants() {
        // login, nom, prénom, niveau min, niveau max, téléphone, mail, mdp, disponible, nbInterventions
        /*
        Intervenant i1 = new Intervenant("aalphabet", "alphabet", "adrien", 5, 4, "0100000000", "adrien.alphabet@insa-lyon.fr", "mdp1", true, 8);
        Intervenant i2 = new Intervenant("bbouteille", "bouteille", "baptiste", 6, 3, "0200000000", "bapiste.bouteille@insa.fr", "mdp2", true, 18);
        Intervenant i3 = new Intervenant("cchapeau", "chapeau", "coralie", 2, 0, "0300000000", "coralie.chapeau@insa.fr", "mdp3", false, 4);
        Intervenant i4 = new Intervenant("ddivision", "division", "donald", 4, 0, "0400000000", "donald.division@insa.fr", "mdp4", true, 53);
        Intervenant i5 = new Intervenant("eecharpe", "ehcarpe", "emilie", 6, 5, "0500000000", "emlilie.ehcarpe@insa.fr", "mdp5", false, 1);
        Intervenant i6 = new Intervenant("fflute", "flute", "flore", 6, 5, "0600000000", "flore.flute@insa.fr", "mdp6", true, 6);
         */
        Intervenant i1 = new Etudiant("Sorbonne", "Langues orientales", "cmartin", "Martin", "Camille", 6, 3, "0655447788", "camille.martin@gmail.com", "mdp1", false, 8);
        Intervenant i2 = new Enseignant("Supérieur", "azola", "Zola", "Anna", 6, 0, "0633221144", "anna.zola@gmail.com", "mdp2", true, 18);
        Intervenant i3 = new Enseignant("Collège", "hemile", "Hugo", "Emile", 3, 3, "0788559944", "emile.hugo@gmail.com", "mdp3", true, 3);
        Intervenant i4 = new Autre("Retraité", "syourcenar", "Yourcenar", "Simone", 5, 1, "0722447744", "simone.yourcenar@gmail.com", "mdp4", true, 4);

        IntervenantDao idao = new IntervenantDao();

        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            idao.create(i1);
            idao.create(i2);
            idao.create(i3);
            idao.create(i4);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout intervenants");
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

    public Intervenant authentifierIntervenantMail(String mail, String mdp) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i = new Intervenant();

        try {

            JpaUtil.creerContextePersistance();
            i = idao.authenticateIntervenantMail(mail, mdp);
            System.out.println("Trace : succès authentification intervenant mail " + i.getMail());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            i = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;
    }

    public Intervenant authentifierIntervenantId(Long id, String mdp) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i = new Intervenant();

        try {

            JpaUtil.creerContextePersistance();
            i = idao.authenticateIntervenantId(id, mdp);
            System.out.println("Trace : succès authentification intervenant id " + i.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            i = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;
    }

    public Intervenant authentifierIntervenantLogin(String login, String mdp) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i = new Intervenant();

        try {

            JpaUtil.creerContextePersistance();
            i = idao.authenticateIntervenantLogin(login, mdp);
            System.out.println("Trace : succès authentification intervenant login " + i.getLogin());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            i = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;
    }

    public boolean incrementerNbInterventions(Intervenant i) {
        IntervenantDao idao = new IntervenantDao();
        boolean result;

        try {
            i.incrementerNbInterventions();
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            idao.update(i);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès mise à jour intervenant" + i);
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

    public Intervenant trouverIntervenantSoutien(Eleve e, Soutien s) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i = new Intervenant();
        boolean result = true;

        try {

            JpaUtil.creerContextePersistance();
            i = idao.findIntervenantSoutien(e);
            System.out.println("Trace : succès find intervenant " + i.getPrenom() + " pour soutien");
            result = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            i = null;
            result = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        if (result) {
            envoyerNotification(i.getTelephone(), "Bonjour " + i.getPrenom() + ". Merci de prendre en charge la demande de soutien en '" + s.getMatiere().getNom() + "' demandée à " + s.getEmissionDemande() + " par " + e.getPrenom() + " en classe de " + e.getNiveau());
        }
        return i;
    }

    public List<Soutien> listerSoutiensIntervenant(Intervenant i) {
        SoutienDao sdao = new SoutienDao();
        List<Soutien> s;

        try {

            JpaUtil.creerContextePersistance();
            s = sdao.listSoutiensByIntervenant(i);
            System.out.println("Trace : succès lister tous les soutiens de l'intervenant " + i.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            s = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return s;

    }

    public List<Intervenant> listerTousIntervenants() {
        IntervenantDao idao = new IntervenantDao();
        List<Intervenant> i;

        try {

            JpaUtil.creerContextePersistance();
            i = idao.listAllIntervenants();
            System.out.println("Trace : succès lister tous les employés");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            i = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;

    }

    /* -------------------MATIERES------------------- */
    public boolean initialiserMatieres() {
        Matiere fra = new Matiere("Français");
        Matiere mat = new Matiere("Mathématiques");
        Matiere hig = new Matiere("Histoire-Géographie");
        Matiere phc = new Matiere("Physique-Chimie");
        Matiere svt = new Matiere("SVT");
        Matiere emc = new Matiere("Education morale et civique");
        Matiere ses = new Matiere("SES");
        Matiere tec = new Matiere("Technologie");
        Matiere ang = new Matiere("Anglais");
        Matiere esp = new Matiere("Espagnol");
        Matiere all = new Matiere("Allemand");
        Matiere lat = new Matiere("Latin");
        Matiere gre = new Matiere("Grec");

        MatiereDao mdao = new MatiereDao();

        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mdao.create(fra);
            mdao.create(mat);
            mdao.create(hig);
            mdao.create(phc);
            mdao.create(svt);
            mdao.create(emc);
            mdao.create(ses);
            mdao.create(tec);
            mdao.create(ang);
            mdao.create(esp);
            mdao.create(all);
            mdao.create(lat);
            mdao.create(gre);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout matières");
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

    public List<Matiere> listerToutesMatieres() {
        MatiereDao edao = new MatiereDao();
        List<Matiere> mat;

        try {

            JpaUtil.creerContextePersistance();
            mat = edao.getAllMatieres();
            System.out.println("Trace : succès lister toutes les matières");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            mat = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return mat;

    }

    public Matiere trouverMatiereParId(Long id) {
        MatiereDao mdao = new MatiereDao();
        Matiere m = new Matiere();

        try {

            JpaUtil.creerContextePersistance();
            m = mdao.findMatiereById(id);
            System.out.println("Trace : succès find matière " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            m = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return m;
    }

    public boolean ajouterSoutien(Soutien s) {
        SoutienDao sdao = new SoutienDao();
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            sdao.create(s);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout soutien" + s);
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

    /* -------------------SOUTIENS------------------- */
    public List<Soutien> listerTousSoutiens() {
        SoutienDao sdao = new SoutienDao();
        List<Soutien> s;

        try {

            JpaUtil.creerContextePersistance();
            s = sdao.listAllSoutiens();
            System.out.println("Trace : succès lister tous les soutiens");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            s = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return s;

    }
    
    /* -------------------STATISTIQUES------------------- */

    public Double noteMoyenneIntervenant(Intervenant i){
        Double moyenne = -1.0;
        SoutienDao sdao = new SoutienDao();

        try {

            JpaUtil.creerContextePersistance();
            moyenne = sdao.getMeanIntervenant(i);
            System.out.println("Trace : succès calcul note moyenne " + i);

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return moyenne;
    }

    public Integer[] repartitionClassesAidees(Intervenant i){
        Integer repartition[] = {-1,-1,-1,-1,-1,-1,-1};
        SoutienDao sdao = new SoutienDao();

        try {

            JpaUtil.creerContextePersistance();
            repartition = sdao.getLevelsRepartition(i);
            System.out.println("Trace : succès récupération interventions par classe " + i);

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return repartition;
    }
    
    
}
