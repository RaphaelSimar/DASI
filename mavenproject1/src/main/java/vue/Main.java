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
import metier.Autre;
import metier.Eleve;
import metier.Employe;
import metier.Enseignant;
import metier.Etudiant;
import metier.Intervenant;
import metier.Matiere;
import metier.Service;
import metier.Soutien;
import static util.Message.envoyerNotification;
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
        //testerInscriptionEleve(s);

        //testerAuthentifierEleveMailSaisie(s);
        //testerAuthentifierIntervenantMailSaisie(s);
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
        /* ------------- MENU ------------- */
        int choix = -1;
        Eleve eleveConnecte = null;
        Intervenant intervenantConnecte = null;

        while (choix != 0) {
            System.out.println(FG_GREEN + "\n================= Menu ====================" + RESET);
            if (eleveConnecte != null) {
                System.out.println(FG_CYAN + "Élève connecté : " + eleveConnecte.getNom() + " " + eleveConnecte.getPrenom() + ", niveau = " + eleveConnecte.getNiveau());
            }
            if (intervenantConnecte != null) {
                System.out.println(FG_CYAN + "Intervenant connecté : " + intervenantConnecte.getNom() + " " + intervenantConnecte.getPrenom() + " de type " + intervenantConnecte.getClass().getSimpleName());
            }
            System.out.println(FG_GREEN + "1.  " + RESET + "Générer inscriptions élèves");
            System.out.println(FG_GREEN + "2.  " + RESET + "Générer soutiens");
            System.out.println(FG_GREEN + "3.  " + RESET + "Inscrire un élève");
            System.out.println(FG_GREEN + "4.  " + RESET + "Authentifier un élève");
            System.out.println(FG_GREEN + "5.  " + RESET + "Authentifier un intervenant");
            System.out.println(FG_GREEN + "6.  " + RESET + "Effectuer une demande de soutien (côté Élève)");
            System.out.println(FG_GREEN + "7.  " + RESET + "Effectuer une réception de soutien (côté Intervenant)");
            System.out.println(FG_GREEN + "8.  " + RESET + "Voir son historique de soutiens (élève)");
            System.out.println(FG_GREEN + "9.  " + RESET + "Voir son historique de soutiens (intervenant)");
            System.out.println(FG_GREEN + "10. " + RESET + "Lister toutes les matières");
            System.out.println(FG_GREEN + "11. " + RESET + "Lister tous les élèves");
            System.out.println(FG_GREEN + "12. " + RESET + "Lister tous les intervenants");
            System.out.println(FG_GREEN + "13. " + RESET + "Lister tous les soutiens");
            System.out.println(FG_GREEN + "0.  " + FG_RED + "Quitter" + RESET);
            System.out.println(FG_GREEN + "===========================================" + RESET);

            List<Integer> valeursPossibles = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
            choix = lireInteger("Entrez votre choix : ", valeursPossibles);

            switch (choix) {

                case 1:
                    System.out.println(FG_GREEN + "Vous avez choisi la génération d'inscriptions d'élèves." + RESET);
                    testerInscriptionEleve(s);
                    break;
                case 2:
                    System.out.println(FG_GREEN + "Vous avez choisi la génération de soutiens" + RESET);
                    testerGenererSoutiens(s);
                    break;
                case 3:
                    System.out.println(FG_GREEN + "Vous avez choisi l'inscription élève." + RESET);
                    testerInscriptionEleveSaisie(s);
                    break;
                case 4:
                    System.out.println(FG_GREEN + "Vous avez choisi l'authentification élève." + RESET);
                    eleveConnecte = testerAuthentifierEleveMailSaisie(s);
                    break;
                case 5:
                    System.out.println(FG_GREEN + "Vous avez choisi l'authentification intervenant." + RESET);
                    intervenantConnecte = testerAuthentifierIntervenantMailSaisie(s);
                    break;
                case 6:
                    System.out.println(FG_GREEN + "Vous avez choisi d'effectuer une demande de soutien (côté élève)." + RESET);
                    testerDemandeSoutienSaisie(s, eleveConnecte);
                    break;
                case 7:
                    System.out.println(FG_GREEN + "Vous avez choisi d'effectuer une réception de soutien (côté intervenant)." + RESET);
                    testerReceptionDemandeSoutienSaisie(s);
                    break;
                case 8:
                    System.out.println(FG_GREEN + "Vous avez choisi de voir l'historique de soutiens (en tant qu'élève)." + RESET);
                    testerVoirHistoriqueEleve(s, eleveConnecte);
                    break;
                case 9:
                    System.out.println(FG_GREEN + "Vous avez choisi de voir l'historique de soutiens (côté en tant qu'intervenant)." + RESET);
                    testerVoirHistoriqueIntervenant(s, intervenantConnecte);
                    break;
                case 10:
                    System.out.println(FG_GREEN + "Vous avez choisi de lister toutes les matières." + RESET);
                    testerListerToutesMatieres(s);
                    break;
                case 11:
                    System.out.println(FG_GREEN + "Vous avez choisi de lister tous les élèves." + RESET);
                    testerListerTousEleves(s);
                    break;
                case 12:
                    System.out.println(FG_GREEN + "Vous avez choisi de lister tous les intervenants." + RESET);
                    testerListerTousIntervenants(s);
                    break;
                case 13:
                    System.out.println(FG_GREEN + "Vous avez choisi de lister tous les soutiens." + RESET);
                    testerListerTousSoutiens(s);
                    break;
                case 0:
                    System.out.println(FG_RED + "\nFermeture de l'application." + RESET);
                    break;
                default:
                    System.out.println(FG_RED + "Choix invalide." + RESET);
                    break;
            }
        }
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
        String codeEtablissement = lireChaine("Code établissement (ex : 0691664J) : ");
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

    static Eleve testerAuthentifierEleveMailSaisie(Service s) {

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Bienvenue sur Instruct'IF ! Vous allez maintenant vous connectez en tant qu'" + FG_RED + "élève" + FG_CYAN + "." + RESET);

        String adresseMail = lireChaine("Adresse e-mail : ");
        String mdp = lireChaine("Mot de passe : ");
        System.out.println("\n===========================================");

        Eleve eleveConnecte = s.authentifierEleveMail(adresseMail, mdp);

        if (eleveConnecte != null) {
            testerAffichageProfilEleve(eleveConnecte);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\n===========================================" + RESET);
            System.out.println(FG_RED + "ERREUR : Échec de la connexion : adresse e-mail et/ou mot de passe invalide(s)." + RESET);
        }
        return eleveConnecte;
    }

    static void testerAffichageProfilEleve(Eleve eleveConnecte) {
        System.out.println("\n===========================================");

        System.out.println(FG_CYAN + "Bienvenue " + eleveConnecte.getPrenom() + ". Voici ton profil :" + RESET);
        System.out.println("Nom : " + FG_GREEN + eleveConnecte.getNom() + RESET);
        System.out.println("Prénom : " + FG_GREEN + eleveConnecte.getPrenom() + RESET);
        // On formate la date pour un meilleur rendu visuel
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(eleveConnecte.getDateNaissance());
        System.out.println("Date de naissance : " + FG_GREEN + strDate + RESET);
        System.out.println("Code établissement : " + FG_GREEN + eleveConnecte.getEtablissement().getUai() + RESET);
        System.out.println("Nom établissement : " + FG_GREEN + eleveConnecte.getEtablissement().getNom() + RESET);
        System.out.println("Niveau : " + FG_GREEN + eleveConnecte.getNiveau() + RESET);
        System.out.println("E-mail : " + FG_GREEN + eleveConnecte.getMail() + RESET);
        System.out.println("Mot de passe : " + FG_GREEN + eleveConnecte.getMdp() + RESET);

        System.out.println("===========================================\n");
    }

    static void testerInscriptionEleve(Service s) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e1 = new Eleve(dateFormat.parse("11/06/2002"), 6, "SIMAR", "Raphaël", "simar.raphael@laposte.net", "mdp1", "3ter chemin des boubibou");
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

    static void testerListerTousEleves(Service s) {
        List<Eleve> elist;
        elist = s.listerTousEleves();
        if (elist.size() > 0) {
            System.out.println(FG_GREEN + "\n===========================================" + RESET);
            System.out.println(FG_GREEN + "Liste des élèves :" + RESET);
            for (Eleve e : elist) {
                System.out.println(e);
            }
            System.out.println(FG_GREEN + "===========================================\n" + RESET);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\nERREUR : Aucun élève n'est inscrit." + RESET);
        }
    }

    static void testerVoirHistoriqueEleve(Service ser, Eleve eleve) {
        while (eleve == null) {
            System.out.println(FG_RED + "\nVeuillez d'abord vous authentifier en tant qu'élève." + RESET);
            eleve = testerAuthentifierEleveMailSaisie(ser);
        }
        List<Soutien> slist;
        slist = ser.listerSoutiensEleve(eleve);
        if (slist.size() > 0) {
            System.out.println(FG_GREEN + "\n===========================================" + RESET);
            System.out.println(FG_GREEN + "Historique de vos soutiens :" + RESET);
            for (Soutien s : slist) {
                System.out.println(s);
            }
            System.out.println(FG_GREEN + "===========================================\n" + RESET);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\nERREUR : Aucun soutien n'a été effectué." + RESET);
        }

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

    static Intervenant testerAuthentifierIntervenantMailSaisie(Service s) {

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Bienvenue sur Instruct'IF ! Vous allez maintenant vous connectez en tant qu'" + FG_RED + "intervenant" + FG_CYAN + "." + RESET);

        String adresseMail = lireChaine("Adresse e-mail : ");
        String mdp = lireChaine("Mot de passe : ");
        System.out.println("\n===========================================");

        Intervenant intervenantConnecte = s.authentifierIntervenantMail(adresseMail, mdp);
        if (intervenantConnecte != null) {
            testerAffichageProfilIntervenant(intervenantConnecte);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\n===========================================" + RESET);
            System.out.println(FG_RED + "ERREUR : Échec de la connexion : adresse e-mail et/ou mot de passe invalide(s)." + RESET);
        }

        return intervenantConnecte;
    }

    static void testerAffichageProfilIntervenant(Intervenant intervenantConnecte) {
        System.out.println("\n===========================================");

        System.out.println(FG_CYAN + "Bienvenue " + intervenantConnecte.getPrenom() + ". Voici ton profil :" + RESET);
        System.out.println("Nom : " + FG_GREEN + intervenantConnecte.getNom() + RESET);
        System.out.println("Prénom : " + FG_GREEN + intervenantConnecte.getPrenom() + RESET);
        System.out.println("Niveau(x) enseigné(s) : " + FG_GREEN + "De " + intervenantConnecte.getNiveau_min() + " à " + intervenantConnecte.getNiveau_max() + RESET);
        System.out.println("Nombres d'interventions : " + FG_GREEN + intervenantConnecte.getNbInterventions() + RESET);
        System.out.println("Téléphone : " + FG_GREEN + intervenantConnecte.getTelephone() + RESET);
        System.out.println("E-mail : " + FG_GREEN + intervenantConnecte.getMail() + RESET);
        System.out.println("Mot de passe : " + FG_GREEN + intervenantConnecte.getMdp() + RESET);

        System.out.println(FG_CYAN + "\nVous êtes un intervenant de type " + FG_RED + intervenantConnecte.getClass().getSimpleName() + FG_CYAN + " : " + RESET);
        switch (intervenantConnecte.getClass().getSimpleName()) {
            case "Etudiant":
                Etudiant etudiantConnecte = (Etudiant) intervenantConnecte;
                System.out.println("Université : " + FG_GREEN + etudiantConnecte.getUniversite() + RESET);
                System.out.println("Spécialité : " + FG_GREEN + etudiantConnecte.getSpecialite() + RESET);
                break;
            case "Enseignant":
                Enseignant enseignantConnecte = (Enseignant) intervenantConnecte;
                System.out.println("Type d'établissement d'exercice : " + FG_GREEN + enseignantConnecte.getTypeEtablissement() + RESET);
                break;
            case "Autre":
                Autre autreConnecte = (Autre) intervenantConnecte;
                System.out.println("Activité : " + FG_GREEN + autreConnecte.getActivite() + RESET);
                break;
        }

        System.out.println("===========================================\n");
    }

    static void testerVoirHistoriqueIntervenant(Service ser, Intervenant intervenant) {
        while (intervenant == null) {
            System.out.println(FG_RED + "\nVeuillez d'abord vous authentifier en tant qu'intervenant." + RESET);
            intervenant = testerAuthentifierIntervenantMailSaisie(ser);
        }
        List<Soutien> slist;
        slist = ser.listerSoutiensIntervenant(intervenant);
        if (slist.size() > 0) {
            System.out.println(FG_GREEN + "\n===========================================" + RESET);
            System.out.println(FG_GREEN + "Historique de vos soutiens :" + RESET);
            for (Soutien s : slist) {
                System.out.println(s);
            }
            System.out.println(FG_GREEN + "===========================================\n" + RESET);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\nERREUR : Aucun soutien n'a été effectué." + RESET);
        }

    }

    static void testerListerTousIntervenants(Service s) {
        List<Intervenant> ilist;
        ilist = s.listerTousIntervenants();
        if (ilist.size() > 0) {
            System.out.println(FG_GREEN + "\n===========================================" + RESET);
            System.out.println(FG_GREEN + "Liste des intervenants :" + RESET);
            for (Intervenant e : ilist) {
                System.out.println(e);
            }
            System.out.println(FG_GREEN + "===========================================\n" + RESET);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\nERREUR : Aucun intervenant n'est inscrit." + RESET);
        }
    }

    /* -------------------MATIERES------------------- */
    static void testerListerToutesMatieres(Service s) {
        List<Matiere> mlist;
        mlist = s.listerToutesMatieres();
        System.out.println(FG_GREEN + "\n===========================================" + RESET);
        System.out.println(FG_GREEN + "Liste des matières :" + RESET);
        for (Matiere m : mlist) {
            System.out.println(m);
        }
        System.out.println(FG_GREEN + "===========================================\n" + RESET);
        String temp = lireChaine("Tapez n'importe quoi pour continuer");
    }

    /* -------------------SOUTIENS------------------- */
    static void testerCreationSoutien(Service s) {
        Soutien soutien = new Soutien(s.trouverEleveParId(Long.valueOf(20)), s.trouverMatiereParId(Long.valueOf(8)), "Aled");
        Intervenant i = s.trouverIntervenantSoutien(soutien.getEleve(), soutien);
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

    static void testerDemandeSoutienSaisie(Service s, Eleve eleve) {
        while (eleve == null) {
            System.out.println(FG_RED + "\nVeuillez d'abord vous authentifier en tant qu'élève." + RESET);
            eleve = testerAuthentifierEleveMailSaisie(s);
        }
        System.out.println(FG_CYAN + "Bonjour, " + eleve.getPrenom() + ". Tu as demandé à effectuer un soutien, donne-nous plus d'informations :" + RESET);
        testerListerToutesMatieres(s);

        Integer matiereInt = lireInteger("\nID de la matière voulue : ");
        Long idMatiere = Long.valueOf(matiereInt);
        String description = lireChaine("Dis-nous en plus sur ta demande : ");

        Matiere m = s.trouverMatiereParId(idMatiere);

        if (m == null) {
            System.out.println(FG_RED + "\nERREUR : Matière non trouvée." + RESET);
        } else {
            Soutien soutien = new Soutien(s.trouverEleveParId(eleve.getId()), m, description);
            Intervenant intervenant = s.trouverIntervenantSoutien(soutien.getEleve(), soutien);
            if (intervenant == null) {
                System.out.println(FG_RED + "\nDésolé, nous n'avons pas trouvé d'intervenant adéquat. Merci de réessayer plus tard." + RESET);
            } else {
                soutien.setIntervenant(intervenant);
                soutien.setDebutSoutien(new Date());
                intervenant.setDisponible(false);
                System.out.println(FG_GREEN + "Intervenant trouvé : " + intervenant.getNom() + " " + intervenant.getPrenom() + " de type " + intervenant.getClass().getSimpleName());
                String temp = lireChaine(FG_GREEN + "==========VISIO EN COURS==========" + RESET + "\nTapez n'importe quoi pour y mettre fin.");
                intervenant.setDisponible(true);
                soutien.setFinSoutien(new Date());
                List<Integer> valeursPossibles = Arrays.asList(1, 2, 3, 4, 5);
                Integer note = lireInteger("Comment as-tu trouvé le soutien ? Note-le de 1 à 5 : ", valeursPossibles);
                soutien.setNote(note);
                s.ajouterSoutien(soutien);
            }
        }
    }

    static void testerReceptionDemandeSoutienSaisie(Service ser) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e = new Eleve(dateFormat.parse("15/09/2001"), 6, "Chaplin", "Charlie", "charlie.chaplin@gmail.com", "mdp", "???");
            ser.inscriptionEleve(e, "0691664J");

            Soutien s = new Soutien(e, ser.trouverMatiereParId(Long.valueOf(8)), "J'aurais besoin d'aide pour un DM");
            Intervenant i = ser.trouverIntervenantParId(Long.valueOf(2));

            envoyerNotification(i.getTelephone(), "Bonjour " + i.getPrenom() + ". Merci de prendre en charge la demande de soutien en '" + s.getMatiere().getNom() + "' demandée à " + s.getEmissionDemande() + " par " + e.getPrenom() + " en classe de " + e.getNiveau());
            s.setIntervenant(i);

            Intervenant i2 = testerAuthentifierIntervenantMailSaisie(ser);
            if (i2 != null) {
                System.out.println(FG_RED + i.getPrenom() + ", vous avez une demande de soutien !" + RESET);
                System.out.println("\n===========================================");

                System.out.println(FG_CYAN + "Voici le profil de " + e.getPrenom() + " : " + RESET);
                System.out.println("Nom : " + FG_GREEN + e.getNom() + RESET);
                System.out.println("Prénom : " + FG_GREEN + e.getPrenom() + RESET);
                // On formate la date pour un meilleur rendu visuel
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = formatter.format(e.getDateNaissance());
                System.out.println("Date de naissance : " + FG_GREEN + strDate + RESET);
                System.out.println("Code établissement : " + FG_GREEN + e.getEtablissement().getUai() + RESET);
                System.out.println("Nom établissement : " + FG_GREEN + e.getEtablissement().getNom() + RESET);
                System.out.println("Niveau : " + FG_GREEN + e.getNiveau() + RESET);
                System.out.println("E-mail : " + FG_GREEN + e.getMail() + RESET);

                System.out.println("\nMatière demandée : " + FG_GREEN + s.getMatiere().getNom() + RESET);
                System.out.println("Descriptif de la demande : " + FG_GREEN + s.getDescription_demande() + RESET);

                System.out.println("===========================================\n");
                String temp = lireChaine("Tapez n'importe quoi pour accepter la demande");

                s.setDebutSoutien(new Date());
                i.setDisponible(false);
                System.out.println(FG_GREEN + "Élève accompagné : " + e.getNom() + " " + e.getPrenom() + " de niveau " + e.getNiveau());
                String temp2 = lireChaine(FG_GREEN + "==========VISIO EN COURS==========" + RESET + "\nTapez n'importe quoi pour y mettre fin.");
                i.setDisponible(true);
                s.setFinSoutien(new Date());
                s.setNote(4);
                ser.ajouterSoutien(s);
            }
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void testerGenererSoutiens(Service ser) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e1 = new Eleve(dateFormat.parse("01/02/2003"), 0, "Hollande", "François", "francois.hollande@gmail.com", "mdp", "???");
            Eleve e2 = new Eleve(dateFormat.parse("03/04/2005"), 1, "Sarkozy", "Nicolas", "nicolas.sarkozy@gmail.com", "mdp", "???");
            Eleve e3 = new Eleve(dateFormat.parse("05/06/2007"), 3, "Obama", "Barack", "barack.obama@gmail.com", "mdp", "???");
            ser.inscriptionEleve(e1, "0640126P");
            ser.inscriptionEleve(e2, "0640126P"); //0641658E college Lina
            ser.inscriptionEleve(e3, "0641658E");

            Soutien s1 = new Soutien(e1, ser.trouverMatiereParId(Long.valueOf(8)), "Aidez-moi");
            Intervenant i1 = ser.trouverIntervenantSoutien(s1.getEleve(), s1);
            s1.setIntervenant(i1);

            s1.setDebutSoutien(new Date());
            // La visio est en cours
            // L'élève met fin à la visio
            s1.setFinSoutien(new Date());
            // L'élève saisit la note à donner au soutien
            s1.setNote(3);
            ser.ajouterSoutien(s1);

            Soutien s2 = new Soutien(e2, ser.trouverMatiereParId(Long.valueOf(9)), "Au secours");
            Intervenant i2 = ser.trouverIntervenantSoutien(s2.getEleve(), s2);
            s2.setIntervenant(i2);

            s2.setDebutSoutien(new Date());
            // La visio est en cours
            // L'élève met fin à la visio
            s2.setFinSoutien(new Date());
            // L'élève saisit la note à donner au soutien
            s2.setNote(4);
            ser.ajouterSoutien(s2);

            Soutien s3 = new Soutien(e3, ser.trouverMatiereParId(Long.valueOf(10)), "Help me");
            Intervenant i3 = ser.trouverIntervenantSoutien(s3.getEleve(), s3);
            s3.setIntervenant(i3);

            s3.setDebutSoutien(new Date());
            // La visio est en cours
            // L'élève met fin à la visio
            s3.setFinSoutien(new Date());
            // L'élève saisit la note à donner au soutien
            s3.setNote(5);
            ser.ajouterSoutien(s3);

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void testerListerTousSoutiens(Service ser) {
        List<Soutien> slist;
        slist = ser.listerTousSoutiens();
        if (slist.size() > 0) {
            System.out.println(FG_GREEN + "\n===========================================" + RESET);
            System.out.println(FG_GREEN + "Liste des soutiens :" + RESET);
            for (Soutien s : slist) {
                System.out.println(s);
            }
            System.out.println(FG_GREEN + "===========================================\n" + RESET);
            String temp = lireChaine("Tapez n'importe quoi pour continuer");
        } else {
            System.out.println(FG_RED + "\nERREUR : Aucun soutien n'a été effectué." + RESET);
        }
    }

    /* -------------------COULEURS------------------- */
    public static final String FG_GREEN = "\u001b[32m";
    public static final String FG_RED = "\u001b[31m";
    public static final String RESET = "\u001B[0m";
    public static final String FG_BLUE = "\u001b[34m";
    public static final String FG_CYAN = "\u001b[36m";
}
