/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.Eleve;
import metier.Employe;
import metier.Service;

/**
 *
 * @author rsimar
 */
public class Main {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        Service s = new Service();
        s.initialiserEmployes();
        

        // ==================Tests================== //
/*        
// Employés
        testerTrouverEmployeParId(Long.valueOf(2), s);

        testerListerTousEmployes(s);

        testerAuthentifierEmploye("sdekew", "mdp2", s);

        // Élèves
        testerInscriptionEleve();

        testerTrouverEleveParId(Long.valueOf(8), s);

        testerAuthentifierEleveMail("pas.moi.non.plus@insa-lyon.fr", "mdpamoi", s);

        testerAuthentifierEleveId(Long.valueOf(8), "mdpamoi", s);
        // ========================================= // */
        testerInscriptionEleve(s);

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

    static void testerInscriptionEleve(Service s) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Eleve e1 = new Eleve(dateFormat.parse("11/06/2002"), 6, "SIMAR", "Raphael", "simar.raphael@laposte.net", "mdp1", "3ter chemin des boubibou");
            Eleve e2 = new Eleve(dateFormat.parse("06/08/2001"), 5, "BORG", "Lina", "borg.lina@laposte.net", "mdp2", "Sous l'océan");
            Eleve e3 = new Eleve(dateFormat.parse("01/01/2000"), 1, "LASALLE", "Jean", "lasalle.jean@laposte.net", "mdp3", "Lourdios");
            s.inscriptionEleve(e1, "0780656P"); 
            s.inscriptionEleve(e2, "0780656P"); //0641658E college Lina
            s.inscriptionEleve(e3, "0640126P");
            
            //s.inscriptionEleve(new Eleve(dateFormat.parse("06/08/2000"), "etablissement", "laclasse", "lenom", "leprenom", "pas.moi@insa-lyon.fr", "mdpamoi", "ladresse"));
            //s.inscriptionEleve(new Eleve(dateFormat.parse("06/08/2000"), "etablissement", "laclasse", "lenom", "leprenom", "pas.moi.non.plus@insa-lyon.fr", "mdpamoi", "ladresse"));
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
        System.out.println(FG_GREEN + "Élève identifié par : mail - " + mail + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + e + RESET);
        System.out.println("===========================================\n");
    }

    static void testerAuthentifierEleveId(Long id, String mdp, Service s) {

        Eleve e = s.authentifierEleveId(id, mdp);

        System.out.println("\n===========================================");
        System.out.println(FG_GREEN + "Élève identifié par : id - " + id + " / mdp - " + mdp + RESET);

        System.out.println(FG_GREEN + e + RESET);
        System.out.println("===========================================\n");
    }

    public static final String FG_GREEN = "\u001b[32m";
    public static final String FG_RED = "\u001b[31m";
    public static final String RESET = "\u001B[0m";
    public static final String FG_BLUE = "\u001b[34m";
}
