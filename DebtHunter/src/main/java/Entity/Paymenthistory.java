/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author GuideKai
 */
@Entity
@Table(name = "PAYMENTHISTORY")
@NamedQueries({
    @NamedQuery(name = "Paymenthistory.findAll", query = "SELECT p FROM Paymenthistory p"),
    @NamedQuery(name = "Paymenthistory.findByPaymentId", query = "SELECT p FROM Paymenthistory p WHERE p.paymentId = :paymentId"),
    @NamedQuery(name = "Paymenthistory.findByCost", query = "SELECT p FROM Paymenthistory p WHERE p.cost = :cost")})
public class Paymenthistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYMENT_ID")
    private Integer paymentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "COST")
    private String cost;
    @JoinColumn(name = "DEBTS_DEBT_ID", referencedColumnName = "DEBT_ID")
    @ManyToOne(optional = false)
    private Debts debtsDebtId;

    public Paymenthistory() {
    }

    public Paymenthistory(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Paymenthistory(Integer paymentId, String cost) {
        this.paymentId = paymentId;
        this.cost = cost;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Debts getDebtsDebtId() {
        return debtsDebtId;
    }

    public void setDebtsDebtId(Debts debtsDebtId) {
        this.debtsDebtId = debtsDebtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paymenthistory)) {
            return false;
        }
        Paymenthistory other = (Paymenthistory) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Paymenthistory[ paymentId=" + paymentId + " ]";
    }
    
}
