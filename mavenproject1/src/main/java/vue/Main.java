/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Eleve;
import metier.Employe;
import metier.Intervenant;
import metier.Matiere;
import metier.Service;
import metier.Soutien;
import static util.Saisie.lireChaine;
import static util.Saisie.lireInteger;

/**
 *
 * @author rsimar
 */
public class Main {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        Service s = new Service();
        //s.initialiserEmployes();
        s.initialiserIntervenants();
        s.initialiserMatieres();

        testerInscriptionEleve(s);

        //testerAuthentifierEleveMailSaisie(s);
        testerAuthentifierIntervenantMailSaisie(s);
        //testerInscriptionEleveSaisie(s);

        //testerListerToutesMatieres(s);
        //testerCreationSoutien(s);
        //testerAuthentifierIntervenantMail("adrien.alphabet@insa-lyon.fr", "mdp1", s);
        // TESTER CREATION INTERVENANTS : creer contexte persistance, valider transaction etc.
        // ==================Tests================== //
/*        
// Employés
        testerTrouverEmployeParId(Long.valueOf(2), s);

        testerListerTousEmployes(s);

        testerAuthentifierEmploye("sdekew", "mdp2", s);

        // Élèves
        

        testerTrouverEleveParId(Long.valueOf(8), s);

        testerAuthentifierEleveMail("pas.moi.non.plus@insa-lyon.fr", "mdpamoi", s);

        testerAuthentifierEleveId(Long.valueOf(8), "mdpamoi", s);
        // ========================================= // */
        //testerInscriptionEleve(s);
    }

    static void testerTrouverEmployeParId(Long id, Service s) {
        Employe e = new Employe();
        e = s.trouverEmployeParId(id);
        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Employé trouvé par id " + id + " : " + e + RESET);
        System.out.println("===========================================\n");
    }

    static void testerListerTousEmployes(Service s) {
        List<Employe> elist;
        elist = s.listerTousEmployes();
        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Liste des employés :" + RESET);
        for (Employe e : elist) {
            System.out.println(e);
        }
        System.out.println("===========================================\n");
    }

    static void testerAuthentifierEmploye(String login, String mdp, Service s) {

        Employe e = s.authentifierEmploye(login, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Employé identifié par : login - " + login + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + e + RESET);
        System.out.println("===========================================\n");
    }

    /* -------------------ELEVES------------------- */
    static void testerAuthentifierEleveId(Long id, String mdp, Service s) {

        Eleve e = s.authentifierEleveId(id, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Élève identifié par : id - " + id + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + e + RESET);
        System.out.println("===========================================\n");
    }

    static void testerInscriptionEleveSaisie(Service s) {

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Bienvenue sur Instruct'IF ! Vous allez maintenant vous inscrire en tant qu'élève." + RESET);

        String nom = lireChaine("Nom : ");
        String prenom = lireChaine("Prénom : ");
        String dateNaissance = lireChaine("Date de naissance (format jj/mm/aaaa) : ");
        String codeEtablissement = lireChaine("Code établissement (ex : 0780656P) : ");
        List<Integer> valeursPossibles = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
        Integer niveau = lireInteger("Niveau (6 pour 6ème, 0 pour terminale)", valeursPossibles);
        String adresseMail = lireChaine("Adresse e-mail : ");
        String mdp = lireChaine("Mot de passe : ");
        System.out.println("===========================================\n");

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e = new Eleve(dateFormat.parse(dateNaissance), niveau, nom, prenom, adresseMail, mdp, "on garde l'adresse postale ?");
            s.inscriptionEleve(e, codeEtablissement);

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void testerAuthentifierEleveMailSaisie(Service s) {

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Bienvenue sur Instruct'IF ! Vous allez maintenant vous connectez en tant qu'élève." + RESET);

        String adresseMail = lireChaine("Adresse e-mail : ");
        String mdp = lireChaine("Mot de passe : ");
        System.out.println("\n===========================================");

        Eleve eleveConnecte = s.authentifierEleveMail(adresseMail, mdp);
        if (eleveConnecte != null) {
            System.out.println("\n===========================================");

            System.out.println(FG_CYAN + "Bienvenue " + eleveConnecte.getPrenom() + ". Voici ton profil :" + RESET);
            System.out.println("Nom : " + FG_GREEN + eleveConnecte.getNom() + RESET);
            System.out.println("Prénom : " + FG_GREEN + eleveConnecte.getPrenom() + RESET);
            System.out.println("Date de naissance : " + FG_GREEN + eleveConnecte.getDateNaissance() + RESET);
            System.out.println("Code établissement : " + FG_GREEN + eleveConnecte.getEtablissement().getUai() + RESET);
            System.out.println("Niveau : " + FG_GREEN + eleveConnecte.getNiveau() + RESET);
            System.out.println("E-mail : " + FG_GREEN + eleveConnecte.getMail() + RESET);
            System.out.println("Mot de passe : " + FG_GREEN + eleveConnecte.getMdp() + RESET);

            System.out.println("===========================================\n");
        } else {
            System.out.println(FG_RED + "ERREUR : Échec de la connexion : adresse e-mail et/ou mot de passe invalide(s)." + RESET);
        }

    }

    static void testerInscriptionEleve(Service s) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e1 = new Eleve(dateFormat.parse("11/06/2002"), 6, "SIMAR", "Raphael", "simar.raphael@laposte.net", "mdp1", "3ter chemin des boubibou");
            Eleve e2 = new Eleve(dateFormat.parse("06/08/2001"), 5, "BORG", "Lina", "borg.lina@laposte.net", "mdp2", "Sous l'océan");
            Eleve e3 = new Eleve(dateFormat.parse("01/01/2000"), 1, "LASALLE", "Jean", "lasalle.jean@laposte.net", "mdp3", "Lourdios");
            s.inscriptionEleve(e1, "0780656P");
            s.inscriptionEleve(e2, "0780656P"); //0641658E college Lina
            s.inscriptionEleve(e3, "0640126P");

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void testerTrouverEleveParId(Long id, Service s) {

        Eleve e = new Eleve();
        e = s.trouverEleveParId(id);
        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Élève trouvé par id " + id + " : " + e + RESET);
        System.out.println("===========================================\n");
    }

    static void testerAuthentifierEleveMail(String mail, String mdp, Service s) {

        Eleve e = s.authentifierEleveMail(mail, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Élève connecté par : mail - " + mail + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + e + RESET);
        System.out.println("===========================================\n");

    }

    /* -------------------INTERVENANTS------------------- */
    static void testerAuthentifierIntervenantMail(String mail, String mdp, Service s) {

        Intervenant i = s.authentifierIntervenantMail(mail, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Intervenant identifié par : mail - " + mail + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + i + RESET);
        System.out.println("===========================================\n");
    }

    static void testerAuthentifierIntervenantId(Long id, String mdp, Service s) {

        Intervenant i = s.authentifierIntervenantId(id, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Intervenant identifié par : id - " + id + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + i + RESET);
        System.out.println("===========================================\n");
    }

    static void testerAuthentifierIntervenantMailSaisie(Service s) {

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Bienvenue sur Instruct'IF ! Vous allez maintenant vous connectez en tant qu'intervenant." + RESET);

        String adresseMail = lireChaine("Adresse e-mail : ");
        String mdp = lireChaine("Mot de passe : ");
        System.out.println("\n===========================================");

        Intervenant intervenantConnecte = s.authentifierIntervenantMail(adresseMail, mdp);
        if (intervenantConnecte != null) {
            System.out.println("\n===========================================");

            System.out.println(FG_CYAN + "Bienvenue " + intervenantConnecte.getPrenom() + ". Voici ton profil :" + RESET);
            System.out.println("Nom : " + FG_GREEN + intervenantConnecte.getNom() + RESET);
            System.out.println("Prénom : " + FG_GREEN + intervenantConnecte.getPrenom() + RESET);
            System.out.println("Niveau(x) enseigné(s) : " + FG_GREEN + "De " + intervenantConnecte.getNiveau_min() + " à " + intervenantConnecte.getNiveau_max() + RESET);
            System.out.println("Nombres d'interventions : " + FG_GREEN + intervenantConnecte.getNbInterventions() + RESET);
            System.out.println("Téléphone : " + FG_GREEN + intervenantConnecte.getTelephone() + RESET);
            System.out.println("E-mail : " + FG_GREEN + intervenantConnecte.getMail() + RESET);
            System.out.println("Mot de passe : " + FG_GREEN + intervenantConnecte.getMdp() + RESET);

            System.out.println("===========================================\n");
        } else {
            System.out.println(FG_RED + "ERREUR : Échec de la connexion : adresse e-mail et/ou mot de passe invalide(s)." + RESET);
        }
    }

    /* -------------------MATIERES------------------- */
    static void testerListerToutesMatieres(Service s) {
        List<Matiere> mlist;
        mlist = s.listerToutesMatieres();
        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Liste des matières :" + RESET);
        for (Matiere m : mlist) {
            System.out.println(m);
        }
        System.out.println("===========================================\n");
    }

    /* -------------------SOUTIENS------------------- */
    static void testerCreationSoutien(Service s) {
        Soutien soutien = new Soutien(s.trouverEleveParId(Long.valueOf(20)), s.trouverMatiereParId(Long.valueOf(8)), "Aled");
        Intervenant i = s.trouverIntervenantSoutien(soutien.getEleve());
        System.out.println("Intervenant trouvé : " + i);
        soutien.setIntervenant(i);

        soutien.setDebutSoutien(new Date());
        // La visio est en cours
        // L'élève met fin à la visio
        soutien.setFinSoutien(new Date());
        // L'élève saisit la note à donner au soutien
        soutien.setNote(5);
        s.ajouterSoutien(soutien);
    }

    /* -------------------COULEURS------------------- */
    public static final String FG_GREEN = "\u001b[32m";
    public static final String FG_RED = "\u001b[31m";
    public static final String RESET = "\u001B[0m";
    public static final String FG_BLUE = "\u001b[34m";
    public static final String FG_CYAN = "\u001b[36m";
}
