package Entity;

import Entity.Debts;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2563-11-02T15:14:17")
@StaticMetamodel(Paymenthistory.class)
public class Paymenthistory_ { 

    public static volatile SingularAttribute<Paymenthistory, String> cost;
    public static volatile SingularAttribute<Paymenthistory, Debts> debtsDebtId;
    public static volatile SingularAttribute<Paymenthistory, Integer> paymentId;

}