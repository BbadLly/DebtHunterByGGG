/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityController;

import Entity.Debts;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Users;
import Entity.Paymenthistory;
import EntityController.exceptions.IllegalOrphanException;
import EntityController.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author GuideKai
 */
public class DebtsJpaController implements Serializable {

    public DebtsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Debts debts) {
        if (debts.getPaymenthistoryList() == null) {
            debts.setPaymenthistoryList(new ArrayList<Paymenthistory>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users usersId = debts.getUsersId();
            if (usersId != null) {
                usersId = em.getReference(usersId.getClass(), usersId.getId());
                debts.setUsersId(usersId);
            }
            List<Paymenthistory> attachedPaymenthistoryList = new ArrayList<Paymenthistory>();
            for (Paymenthistory paymenthistoryListPaymenthistoryToAttach : debts.getPaymenthistoryList()) {
                paymenthistoryListPaymenthistoryToAttach = em.getReference(paymenthistoryListPaymenthistoryToAttach.getClass(), paymenthistoryListPaymenthistoryToAttach.getPaymentId());
                attachedPaymenthistoryList.add(paymenthistoryListPaymenthistoryToAttach);
            }
            debts.setPaymenthistoryList(attachedPaymenthistoryList);
            em.persist(debts);
            if (usersId != null) {
                usersId.getDebtsList().add(debts);
                usersId = em.merge(usersId);
            }
            for (Paymenthistory paymenthistoryListPaymenthistory : debts.getPaymenthistoryList()) {
                Debts oldDebtsDebtIdOfPaymenthistoryListPaymenthistory = paymenthistoryListPaymenthistory.getDebtsDebtId();
                paymenthistoryListPaymenthistory.setDebtsDebtId(debts);
                paymenthistoryListPaymenthistory = em.merge(paymenthistoryListPaymenthistory);
                if (oldDebtsDebtIdOfPaymenthistoryListPaymenthistory != null) {
                    oldDebtsDebtIdOfPaymenthistoryListPaymenthistory.getPaymenthistoryList().remove(paymenthistoryListPaymenthistory);
                    oldDebtsDebtIdOfPaymenthistoryListPaymenthistory = em.merge(oldDebtsDebtIdOfPaymenthistoryListPaymenthistory);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Debts debts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Debts persistentDebts = em.find(Debts.class, debts.getDebtId());
            Users usersIdOld = persistentDebts.getUsersId();
            Users usersIdNew = debts.getUsersId();
            List<Paymenthistory> paymenthistoryListOld = persistentDebts.getPaymenthistoryList();
            List<Paymenthistory> paymenthistoryListNew = debts.getPaymenthistoryList();
            List<String> illegalOrphanMessages = null;
            for (Paymenthistory paymenthistoryListOldPaymenthistory : paymenthistoryListOld) {
                if (!paymenthistoryListNew.contains(paymenthistoryListOldPaymenthistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Paymenthistory " + paymenthistoryListOldPaymenthistory + " since its debtsDebtId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usersIdNew != null) {
                usersIdNew = em.getReference(usersIdNew.getClass(), usersIdNew.getId());
                debts.setUsersId(usersIdNew);
            }
            List<Paymenthistory> attachedPaymenthistoryListNew = new ArrayList<Paymenthistory>();
            for (Paymenthistory paymenthistoryListNewPaymenthistoryToAttach : paymenthistoryListNew) {
                paymenthistoryListNewPaymenthistoryToAttach = em.getReference(paymenthistoryListNewPaymenthistoryToAttach.getClass(), paymenthistoryListNewPaymenthistoryToAttach.getPaymentId());
                attachedPaymenthistoryListNew.add(paymenthistoryListNewPaymenthistoryToAttach);
            }
            paymenthistoryListNew = attachedPaymenthistoryListNew;
            debts.setPaymenthistoryList(paymenthistoryListNew);
            debts = em.merge(debts);
            if (usersIdOld != null && !usersIdOld.equals(usersIdNew)) {
                usersIdOld.getDebtsList().remove(debts);
                usersIdOld = em.merge(usersIdOld);
            }
            if (usersIdNew != null && !usersIdNew.equals(usersIdOld)) {
                usersIdNew.getDebtsList().add(debts);
                usersIdNew = em.merge(usersIdNew);
            }
            for (Paymenthistory paymenthistoryListNewPaymenthistory : paymenthistoryListNew) {
                if (!paymenthistoryListOld.contains(paymenthistoryListNewPaymenthistory)) {
                    Debts oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory = paymenthistoryListNewPaymenthistory.getDebtsDebtId();
                    paymenthistoryListNewPaymenthistory.setDebtsDebtId(debts);
                    paymenthistoryListNewPaymenthistory = em.merge(paymenthistoryListNewPaymenthistory);
                    if (oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory != null && !oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory.equals(debts)) {
                        oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory.getPaymenthistoryList().remove(paymenthistoryListNewPaymenthistory);
                        oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory = em.merge(oldDebtsDebtIdOfPaymenthistoryListNewPaymenthistory);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = debts.getDebtId();
                if (findDebts(id) == null) {
                    throw new NonexistentEntityException("The debts with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Debts debts;
            try {
                debts = em.getReference(Debts.class, id);
                debts.getDebtId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The debts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Paymenthistory> paymenthistoryListOrphanCheck = debts.getPaymenthistoryList();
            for (Paymenthistory paymenthistoryListOrphanCheckPaymenthistory : paymenthistoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Debts (" + debts + ") cannot be destroyed since the Paymenthistory " + paymenthistoryListOrphanCheckPaymenthistory + " in its paymenthistoryList field has a non-nullable debtsDebtId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users usersId = debts.getUsersId();
            if (usersId != null) {
                usersId.getDebtsList().remove(debts);
                usersId = em.merge(usersId);
            }
            em.remove(debts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Debts> findDebtsEntities() {
        return findDebtsEntities(true, -1, -1);
    }

    public List<Debts> findDebtsEntities(int maxResults, int firstResult) {
        return findDebtsEntities(false, maxResults, firstResult);
    }

    private List<Debts> findDebtsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Debts.class));
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

    public Debts findDebts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Debts.class, id);
        } finally {
            em.close();
        }
    }

    public int getDebtsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Debts> rt = cq.from(Debts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
