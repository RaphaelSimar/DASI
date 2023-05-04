package metier;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.Eleve;
import metier.Intervenant;
import metier.Matiere;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-04T12:50:32")
@StaticMetamodel(Soutien.class)
public class Soutien_ { 

    public static volatile SingularAttribute<Soutien, Date> finSoutien;
    public static volatile SingularAttribute<Soutien, Date> emissionDemande;
    public static volatile SingularAttribute<Soutien, Integer> note;
    public static volatile SingularAttribute<Soutien, Boolean> visioLancee;
    public static volatile SingularAttribute<Soutien, String> description_demande;
    public static volatile SingularAttribute<Soutien, Date> debutSoutien;
    public static volatile SingularAttribute<Soutien, Long> id;
    public static volatile SingularAttribute<Soutien, Eleve> eleve;
    public static volatile SingularAttribute<Soutien, Intervenant> intervenant;
    public static volatile SingularAttribute<Soutien, Matiere> matiere;

}