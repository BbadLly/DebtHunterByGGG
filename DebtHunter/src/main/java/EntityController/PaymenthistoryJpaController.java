/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Debts;
import Entity.Paymenthistory;
import EntityController.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GuideKai
 */
public class PaymenthistoryJpaController implements Serializable {

    public PaymenthistoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Paymenthistory paymenthistory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Debts debtsDebtId = paymenthistory.getDebtsDebtId();
            if (debtsDebtId != null) {
                debtsDebtId = em.getReference(debtsDebtId.getClass(), debtsDebtId.getDebtId());
                paymenthistory.setDebtsDebtId(debtsDebtId);
            }
            em.persist(paymenthistory);
            if (debtsDebtId != null) {
                debtsDebtId.getPaymenthistoryList().add(paymenthistory);
                debtsDebtId = em.merge(debtsDebtId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Paymenthistory paymenthistory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paymenthistory persistentPaymenthistory = em.find(Paymenthistory.class, paymenthistory.getPaymentId());
            Debts debtsDebtIdOld = persistentPaymenthistory.getDebtsDebtId();
            Debts debtsDebtIdNew = paymenthistory.getDebtsDebtId();
            if (debtsDebtIdNew != null) {
                debtsDebtIdNew = em.getReference(debtsDebtIdNew.getClass(), debtsDebtIdNew.getDebtId());
                paymenthistory.setDebtsDebtId(debtsDebtIdNew);
            }
            paymenthistory = em.merge(paymenthistory);
            if (debtsDebtIdOld != null && !debtsDebtIdOld.equals(debtsDebtIdNew)) {
                debtsDebtIdOld.getPaymenthistoryList().remove(paymenthistory);
                debtsDebtIdOld = em.merge(debtsDebtIdOld);
            }
            if (debtsDebtIdNew != null && !debtsDebtIdNew.equals(debtsDebtIdOld)) {
                debtsDebtIdNew.getPaymenthistoryList().add(paymenthistory);
                debtsDebtIdNew = em.merge(debtsDebtIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = paymenthistory.getPaymentId();
                if (findPaymenthistory(id) == null) {
                    throw new NonexistentEntityException("The paymenthistory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Paymenthistory paymenthistory;
            try {
                paymenthistory = em.getReference(Paymenthistory.class, id);
                paymenthistory.getPaymentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The paymenthistory with id " + id + " no longer exists.", enfe);
            }
            Debts debtsDebtId = paymenthistory.getDebtsDebtId();
            if (debtsDebtId != null) {
                debtsDebtId.getPaymenthistoryList().remove(paymenthistory);
                debtsDebtId = em.merge(debtsDebtId);
            }
            em.remove(paymenthistory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Paymenthistory> findPaymenthistoryEntities() {
        return findPaymenthistoryEntities(true, -1, -1);
    }

    public List<Paymenthistory> findPaymenthistoryEntities(int maxResults, int firstResult) {
        return findPaymenthistoryEntities(false, maxResults, firstResult);
    }

    private List<Paymenthistory> findPaymenthistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Paymenthistory.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Paymenthistory findPaymenthistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Paymenthistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymenthistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Paymenthistory> rt = cq.from(Paymenthistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
