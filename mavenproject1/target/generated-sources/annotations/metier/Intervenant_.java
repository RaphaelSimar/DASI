package metier;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-02T17:52:51")
@StaticMetamodel(Intervenant.class)
public class Intervenant_ { 

    public static volatile SingularAttribute<Intervenant, Integer> niveau_max;
    public static volatile SingularAttribute<Intervenant, Integer> niveau_min;
    public static volatile SingularAttribute<Intervenant, String> mail;
    public static volatile SingularAttribute<Intervenant, Integer> nbInterventions;
    public static volatile SingularAttribute<Intervenant, String> mdp;
    public static volatile SingularAttribute<Intervenant, String> telephone;
    public static volatile SingularAttribute<Intervenant, Long> id;
    public static volatile SingularAttribute<Intervenant, String> login;
    public static volatile SingularAttribute<Intervenant, String> nom;
    public static volatile SingularAttribute<Intervenant, String> prenom;
    public static volatile SingularAttribute<Intervenant, Boolean> disponible;

}