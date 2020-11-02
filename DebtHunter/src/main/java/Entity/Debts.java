/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author GuideKai
 */
@Entity
@Table(name = "DEBTS")
@NamedQueries({
    @NamedQuery(name = "Debts.findAll", query = "SELECT d FROM Debts d"),
    @NamedQuery(name = "Debts.findByDebtId", query = "SELECT d FROM Debts d WHERE d.debtId = :debtId"),
    @NamedQuery(name = "Debts.findByDebtName", query = "SELECT d FROM Debts d WHERE d.debtName = :debtName"),
    @NamedQuery(name = "Debts.findByDebtorMail", query = "SELECT d FROM Debts d WHERE d.debtorMail = :debtorMail"),
    @NamedQuery(name = "Debts.findByDescription", query = "SELECT d FROM Debts d WHERE d.description = :description"),
    @NamedQuery(name = "Debts.findByCost", query = "SELECT d FROM Debts d WHERE d.cost = :cost")})
public class Debts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEBT_ID")
    private Integer debtId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DEBT_NAME")
    private String debtName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DEBTOR_MAIL")
    private String debtorMail;
    @Size(max = 200)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COST")
    private int cost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "debtsDebtId")
    private List<Paymenthistory> paymenthistoryList;
    @JoinColumn(name = "USERS_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Users usersId;

    public Debts() {
    }

    public Debts(Integer debtId) {
        this.debtId = debtId;
    }

    public Debts(Integer debtId, String debtName, String debtorMail, int cost) {
        this.debtId = debtId;
        this.debtName = debtName;
        this.debtorMail = debtorMail;
        this.cost = cost;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

    public String getDebtName() {
        return debtName;
    }

    public void setDebtName(String debtName) {
        this.debtName = debtName;
    }

    public String getDebtorMail() {
        return debtorMail;
    }

    public void setDebtorMail(String debtorMail) {
        this.debtorMail = debtorMail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Paymenthistory> getPaymenthistoryList() {
        return paymenthistoryList;
    }

    public void setPaymenthistoryList(List<Paymenthistory> paymenthistoryList) {
        this.paymenthistoryList = paymenthistoryList;
    }

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (debtId != null ? debtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Debts)) {
            return false;
        }
        Debts other = (Debts) object;
        if ((this.debtId == null && other.debtId != null) || (this.debtId != null && !this.debtId.equals(other.debtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Debts[ debtId=" + debtId + " ]";
    }
    
}
