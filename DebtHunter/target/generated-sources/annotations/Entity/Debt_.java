package Entity;

import Entity.Paymenthistory;
import Entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-10-31T20:43:37")
@StaticMetamodel(Debt.class)
public class Debt_ { 

    public static volatile SingularAttribute<Debt, Integer> cost;
    public static volatile SingularAttribute<Debt, String> debtorMail;
    public static volatile SingularAttribute<Debt, Users> usersId;
    public static volatile SingularAttribute<Debt, Integer> boardId;
    public static volatile SingularAttribute<Debt, String> description;
    public static volatile SingularAttribute<Debt, String> boardName;
    public static volatile ListAttribute<Debt, Paymenthistory> paymenthistoryList;

}