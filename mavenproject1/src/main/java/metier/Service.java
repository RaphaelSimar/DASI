/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import com.google.maps.model.LatLng;
import util.EducNetApi;
import dao.EleveDao;
import dao.JpaUtil;
import dao.EtablissementDao;
import dao.IntervenantDao;
import dao.MatiereDao;
import dao.SoutienDao;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import util.GeoNetApi;
import static util.Message.envoyerMail;
import static util.Message.envoyerNotification;

/**
 *
 * @authors Raphaël SIMAR & Lina Borg : B3129 
 * Dominque DROUILLY TORRES : Étudiante d'échange
 */
public class Service {

    public Service() {
    }

    /*
    Permet d’ajouter l’élève passé en paramètre à la base de données ainsi que, 
    s’il n’est pas déjà présent dans la base, son établissement donné par l’uai 
    passé en paramètre.
    Paramètres d’entrée :
        eleve (Eleve) que l’on veut ajouter dans la base de données
        uai (String) le code associé à l’établissement de l’élève
    Sortie : booléen indiquant si l’élève a bien été ajouté (true) ou non (false)
    */
    
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
                System.out.println("Trace : échec inscription eleve - établissement inconnu " + uai);
            } else {
                eleve.setEtablissement(etab);

                JpaUtil.ouvrirTransaction();
                edao.create(eleve);
                if (!etab_found) {
                    etabdao.create(etab);
                }
                JpaUtil.validerTransaction();

                System.out.println("Trace : succès inscription eleve " + eleve);

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
    
    /*
    Permet de trouver l’entité Eleve à l’aide de son ID passé en paramètre, et le renvoyer.
    Paramètres d’entrée : id (Long) de l’entité Eleve que l’on recherche
    Sortie : Eleve : l’entité recherchée

    */

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

    /*
    Permet d’authentifier un élève à l’aide de son mail et son mot de passe passés
    en paramètres. Il renvoie l’élève authentifié (ou null si erreur de connexion).
    Paramètres d’entrée : 
        - mail (String)
        - mdp (String)
    Sortie : 
        entité Eleve correspondant à l’élève authentifié
        null si le mail et/ou le mot de passe ne sont pas valides
    */
    public Eleve authentifierEleveMail(String mail, String mdp) {
        EleveDao edao = new EleveDao();
        Eleve e = new Eleve();

        try {

            JpaUtil.creerContextePersistance();
            e = edao.authenticateEleveMail(mail, mdp);
            System.out.println("Trace : succès authentification élèveMail" + e.getMail());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    /*
    Permet d’authentifier un élève à l’aide de son id et son mot de passe passés 
    en paramètres. Il renvoie l’élève authentifié (ou null si erreur de connexion).
    Paramètres d’entrée : 
        - id (Long)
        - mdp (String)
    Sortie : 
        entité Eleve correspondant à l’élève authentifié
        null si l'ID et/ou le mot de passe ne sont pas valides
    */
    public Eleve authentifierEleveId(Long id, String mdp) {
        EleveDao edao = new EleveDao();
        Eleve e = new Eleve();

        try {

            JpaUtil.creerContextePersistance();
            e = edao.authenticateEleveId(id, mdp);
            System.out.println("Trace : succès authentification élèveID " + e.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    /*
    Permet de lister les soutiens dans lesquels l’entité Eleve passée en paramètre 
    est présente. Il permet d’obtenir tous les soutiens demandés par tel élève 
    (à utiliser pour l’historique des soutiens).
    Paramètres d’entrée : 
        eleve (Eleve) l’entité dont on veut la liste de soutiens
    Sortie : liste de tous les soutiens dans lesquels l’entité Eleve passée en paramètre est présente
    */
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

    /*
    Permet de lister toutes les entités Eleve existant dans la base de données.
    Paramètres d’entrée : eleve (Eleve) l’entité dont on veut la liste complète
    Sortie : liste de toutes les entités Eleve existant dans la base de données
    */
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
    
    /*
    Permet de trouver l’entité Etablissement à l’aide de son UID passé en paramètre, et le renvoyer.
    Paramètres d’entrée : UID (String) de l’entité Etablissement que l’on recherche
    Sortie : Etablissement : l’entité recherchée
    */
    public Etablissement trouverEtablissementParUai(String uai) {
        EtablissementDao edao = new EtablissementDao();
        Etablissement e;

        try {

            JpaUtil.creerContextePersistance();
            e = edao.findEtablissementById(uai);
            System.out.println("Trace : succès find établissement " + uai);

        } catch (Exception ex) {
            ex.printStackTrace();

            e = null;

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }

    /*
    Permet de compléter toutes les informations nécessaires à la création 
    d'une entité Etablissement, à partir de son UAI.
    Cette fonction utilise une API contenue dans la classeEducNetApi
    Paramètres d’entrée : 
        - UID (String) de l’entité Etablissement que l’on recherche
        - niveau (Integer) de l'élève inscrit dans cet établissement. 
          Sert à déterminer si c'est un collège ou un lycée
    Sortie : Etablissement : l’entité construite
    */
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

    /*
    Permet d’ajouter l’entité Etablissement passée en paramètres à la base de données.
    Paramètres d’entrée : etablissement (Etablissement) l’entité que l’on veut rajouter à la base de données
    Sortie : booléen indiquant si l’entité a bien été ajoutée (true) ou non (false)
    */
    public boolean ajouterEtablissement(Etablissement e) {
        EtablissementDao edao = new EtablissementDao();
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            edao.create(e);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout établisement " + e);
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
    
    /*
    Permet d’ajouter l’entité Intervenant passée en paramètres à la base de données.
    Paramètres d’entrée : intervenant (Intervenant) l’entité que l’on veut rajouter à la base de données
    Sortie : booléen indiquant si l’entité a bien été ajoutée (true) ou non (false)
    */
    public boolean ajouterIntervenant(Intervenant i) {
        IntervenantDao idao = new IntervenantDao();
        boolean result;

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            idao.create(i);
            JpaUtil.validerTransaction();
            System.out.println("Trace : succès ajout intervenant " + i);
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

    /*
    Permet de trouver l’entité Intervenant à l’aide de son Id passé en paramètre, et le renvoyer.
    Paramètres d’entrée : id (Long) de l’entité Intervenant que l’on recherche
    Sortie : Intervenant : l’entité recherchée
    */
    public Intervenant trouverIntervenantParId(Long id) {
        IntervenantDao idao = new IntervenantDao();
        Intervenant i;

        try {

            JpaUtil.creerContextePersistance();
            i = idao.findIntervenantById(id);
            System.out.println("Trace : succès find intervenant " + id);

        } catch (Exception ex) {
            ex.printStackTrace();

            i = null;

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return i;
    }

    /*
    Permet d’initialiser des entités Intervenant en les créant en dur dans la base de données.
    Paramètres d’entrée : intervenant (Intervenant) l’entité que l’on veut rajouter à la base de données
    Sortie : booléen indiquant si l’entité a bien été ajoutée (true) ou non (false)
    */
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

    /*
    Permet d’authentifier un intervenant à l’aide de son mail et son mot de passe passés
    en paramètres. Il renvoie l’intervenant authentifié (ou null si erreur de connexion).
    Paramètres d’entrée : 
        - mail (String)
        - mdp (String)
    Sortie : 
        entité Intervanant correspondant à l’intervenant authentifié
        null si le mail et/ou le mot de passe ne sont pas valides
    */
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

    /*
    Permet d’authentifier un Intervenant à l’aide de son id et son mot de passe passés 
    en paramètres. Il renvoie l’intervenant authentifié (ou null si erreur de connexion).
    Paramètres d’entrée : 
        - id (Long)
        - mdp (String)
    Sortie : 
        entité Intervenant correspondant à l’intervenant authentifié
        null si l'ID et/ou le mot de passe ne sont pas valides
    */
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

    /*
    Permet d’authentifier un intervenant à l’aide de son login et son mot de 
    passe passés en paramètres. Il renvoie l’intervenant authentifié 
    (ou null si erreur de connexion).
    Paramètres d’entrée : 
        - login (String)
        - mdp (String)
    Sortie : 
        entité Intervenant correspondant à l’intervenant authentifié, 
        null si le mail et/ou le mot de passe ne sont pas valides
    */
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

    /*
    Permet d’incrémenter (de 1) le nombre d’interventions de l’intervenant passé en paramètre.
    Paramètres d’entrée : intervenant (Intervenant)
    Sortie : booléen indiquant si l’entité a bien été mise à jour (true) ou non (false)
    */
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

    /*
    Permet de trouver un intervenant dont les niveaux enseignés incluent le 
    niveau de l’élève passé en paramètre, qui est disponible et dont le nombre 
    d’interventions est minimal. Il permet aussi d’envoyer une notification à 
    l’intervenant trouvé avec les informations sur le soutien passé en paramètre.
    Paramètres d’entrée :
        - eleve (Eleve) l’élève ayant demandé un soutien pour lequel on souhaite 
          trouver un intervenant adéquat
        - soutien (Soutien) la demande de soutien comportant déjà l’élève 
          demandeur, la matière et la description saisies par ce dernier
    Sortie : Intervenant trouvé dont les niveaux enseignés incluent le niveau de 
             l’élève passé en paramètre, qui est disponible et dont le nombre 
             d’interventions est minimal
    */
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
            envoyerNotification(i.getTelephone(), "Bonjour " + i.getPrenom() + ". Merci de prendre en charge la demande de soutien en '" + s.getMatiere().getNom() + "' demandée à " + s.getEmissionDemande() + " par " + e.getPrenom() + " en classe de " + e.getNiveauString());
        }
        return i;
    }

    /*
    Permet de lister les soutiens dans lesquels l’entité Intervenant passée en paramètre 
    est présente. 
    Il permet d’obtenir tous les soutiens réalisés par tel intervenant 
    (à utiliser pour l’historique des soutiens).
    Paramètres d’entrée : intervenant (Intervenant) l’entité dont on veut la liste 
    de soutiens
    Sortie : liste de tous les soutiens dans lesquels l’entité Intervenant passée en paramètre est présente
    */
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

    /*
    Permet de lister toutes les entités Intervenants existant dans la base de données.
    Paramètres d’entrée : intervenants (Intervenants) l’entité dont on veut la liste complète
    Sortie : liste de toutes les entités Intervenants existant dans la base de données
    */
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
    
    /*
    Permet d’initialiser des entités Matieres en les créant en dur dans la base de données.
    Paramètres d’entrée : matieres (Matieres) l’entité que l’on veut rajouter à la base de données
    Sortie : booléen indiquant si l’entité a bien été ajoutée (true) ou non (false)
    */
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

    /*
    Permet de lister toutes les entités Matiere existant dans la base de données.
    Paramètres d’entrée : matiere (Matiere) l’entité dont on veut la liste complète
    Sortie : liste de toutes les entités Matiere existant dans la base de données
    */
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

    /*
    Permet de trouver l’entité Matiere à l’aide de son Id passé en paramètre, et le renvoyer.
    Paramètres d’entrée : id (Long) de l’entité Matiere que l’on recherche
    Sortie : Matiere : l’entité recherchée
    */
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
    
    
    /* -------------------SOUTIENS------------------- */

    /*
    Permet d’ajouter l’entité Soutien passée en paramètres à la base de données.
    Paramètres d’entrée : soutien (Soutien) l’entité que l’on veut rajouter à la base de données
    Sortie : booléen indiquant si l’entité a bien été ajoutée (true) ou non (false)
    */
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
    
    /*
    Permet de trouver l’entité Soutien à l’aide de son ID passé en paramètre, et le renvoyer.
    Paramètres d’entrée : id (Long) de l’entité Soutien que l’on recherche
    Sortie : Soutien : l’entité recherchée
    */
    public Soutien trouverSoutienParId(Long id) {
        SoutienDao sdao = new SoutienDao();
        Soutien s = new Soutien();

        try {

            JpaUtil.creerContextePersistance();
            s = sdao.findSoutienById(id);
            System.out.println("Trace : succès find soutien " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            s = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return s;
    }

    /*
    Permet de lister toutes les entités Soutien existant dans la base de données.
    Paramètres d’entrée : soutien (Soutien) l’entité dont on veut la liste complète
    Sortie : liste de toutes les entités Soutien existant dans la base de données
    */
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
    
    /*
    Permet de renvoyer la moyenne des notes attribuées à un intervenant donné.
    Paramètres d’entrée : i (Intervenant)
    Sortie : moyenne (Double) des notes de l’intervenant
    */
    public Double noteMoyenneIntervenant(Intervenant i) {
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

    /*
    Permet de connaître le nombre de soutiens fournis par classe, par l’intervenant donné.
    Paramètres d’entrée : i (Intervenant)
    Sortie : repartition (Integer[]) : c’est un tableau de six cases, 
             où repartition[0] correspond à la sixième et repartition[5] correspond 
             à la terminale.
    */
    public Integer[] repartitionClassesAidees(Intervenant i) {
        Integer repartition[] = {-1, -1, -1, -1, -1, -1, -1};
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

    /*
    Permet de connaître l’IPS (Indice de Position Sociale) moyen des établissements 
    où sont scolarisés les élèves aidés.
    Paramètres d’entrée : i (Intervenant)
    Sortie : ipsMoyen (Double)
    */
    public Double ipsMoyenAide(Intervenant i) {
        SoutienDao sdao = new SoutienDao();
        List<Soutien> soutiens;

        try {
            JpaUtil.creerContextePersistance();
            soutiens = sdao.listSoutiensByIntervenant(i);
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            soutiens = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        Double res = 0.0;
        for (Soutien s : soutiens) {
            res += Double.parseDouble(s.getEleve().getEtablissement().getIps());
        }
        res = res / soutiens.size();

        return res;
    }

    /*
    Permet de connaître les coordonnées GPS (latitude et longitude) des 
    établissements où sont scolarisés les élèves aidés. L’usage d’une HashMap 
    permet d’éviter les doublons : si un intervenant aide plusieurs élèves 
    d’un même établissement, une seule donnée GPS sera fournie.
    
    Paramètres d’entrée : i (Intervenant)
    Sortie : listeGPS (HashMap<String, Double[]>)
        - La clé de la HasMap est l’UAI de l’établissement
        - Le tableau des coordonnees (Double[]) est un tableau de taille 2 tel que 
          coordonnes[0] = latitude (Double) et coordonnes[1] = longitude (Double).
    */
    public HashMap<String, Double[]> coordonneesEtablissementsAides(Intervenant i) {
        SoutienDao sdao = new SoutienDao();
        List<Soutien> soutiens;

        try {
            JpaUtil.creerContextePersistance();
            soutiens = sdao.listSoutiensByIntervenant(i);
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            soutiens = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        HashMap<String, Double[]> res = new HashMap<String, Double[]>();
        
        for (Soutien s : soutiens) {
            String adresseEtablissement = s.getEleve().getEtablissement().getNom() + ", " + s.getEleve().getEtablissement().getCommune();
            LatLng coordsEtablissement = GeoNetApi.getLatLng(adresseEtablissement);
            Double[] coordonnees = {coordsEtablissement.lat, coordsEtablissement.lng};
            res.put(s.getEleve().getEtablissement().getUai(), coordonnees);
        }

        return res;
    }
}
