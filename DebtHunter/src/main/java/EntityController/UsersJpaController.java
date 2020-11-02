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
import Entity.Users;
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
public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        if (users.getDebtsList() == null) {
            users.setDebtsList(new ArrayList<Debts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Debts> attachedDebtsList = new ArrayList<Debts>();
            for (Debts debtsListDebtsToAttach : users.getDebtsList()) {
                debtsListDebtsToAttach = em.getReference(debtsListDebtsToAttach.getClass(), debtsListDebtsToAttach.getDebtId());
                attachedDebtsList.add(debtsListDebtsToAttach);
            }
            users.setDebtsList(attachedDebtsList);
            em.persist(users);
            for (Debts debtsListDebts : users.getDebtsList()) {
                Users oldUsersIdOfDebtsListDebts = debtsListDebts.getUsersId();
                debtsListDebts.setUsersId(users);
                debtsListDebts = em.merge(debtsListDebts);
                if (oldUsersIdOfDebtsListDebts != null) {
                    oldUsersIdOfDebtsListDebts.getDebtsList().remove(debtsListDebts);
                    oldUsersIdOfDebtsListDebts = em.merge(oldUsersIdOfDebtsListDebts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            List<Debts> debtsListOld = persistentUsers.getDebtsList();
            List<Debts> debtsListNew = users.getDebtsList();
            List<String> illegalOrphanMessages = null;
            for (Debts debtsListOldDebts : debtsListOld) {
                if (!debtsListNew.contains(debtsListOldDebts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Debts " + debtsListOldDebts + " since its usersId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Debts> attachedDebtsListNew = new ArrayList<Debts>();
            for (Debts debtsListNewDebtsToAttach : debtsListNew) {
                debtsListNewDebtsToAttach = em.getReference(debtsListNewDebtsToAttach.getClass(), debtsListNewDebtsToAttach.getDebtId());
                attachedDebtsListNew.add(debtsListNewDebtsToAttach);
            }
            debtsListNew = attachedDebtsListNew;
            users.setDebtsList(debtsListNew);
            users = em.merge(users);
            for (Debts debtsListNewDebts : debtsListNew) {
                if (!debtsListOld.contains(debtsListNewDebts)) {
                    Users oldUsersIdOfDebtsListNewDebts = debtsListNewDebts.getUsersId();
                    debtsListNewDebts.setUsersId(users);
                    debtsListNewDebts = em.merge(debtsListNewDebts);
                    if (oldUsersIdOfDebtsListNewDebts != null && !oldUsersIdOfDebtsListNewDebts.equals(users)) {
                        oldUsersIdOfDebtsListNewDebts.getDebtsList().remove(debtsListNewDebts);
                        oldUsersIdOfDebtsListNewDebts = em.merge(oldUsersIdOfDebtsListNewDebts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Debts> debtsListOrphanCheck = users.getDebtsList();
            for (Debts debtsListOrphanCheckDebts : debtsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Debts " + debtsListOrphanCheckDebts + " in its debtsList field has a non-nullable usersId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
