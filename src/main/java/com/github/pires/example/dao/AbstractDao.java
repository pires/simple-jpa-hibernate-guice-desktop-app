package com.github.pires.example.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * Abstract Data-Access Object class to be implemented by all DAO's.
 */
public abstract class AbstractDao<T> {
	protected Class<T> entityClass;

	private EntityManager em;

	public AbstractDao(EntityManager em, Class<T> entityClass) {
		this.em = em;
		this.entityClass = entityClass;
	}

	public EntityManager getEntityManager() {
		return this.em;
	}

	/**
	 * Retrieves the meta-model for a certain entity.
	 * 
	 * @return the meta-model of a certain entity.
	 */
	protected EntityType<T> getMetaModel() {
		CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(
		    entityClass);
		Metamodel m = getEntityManager().getMetamodel();

		return m.entity(entityClass);
	}

	public void create(T entity) {
		em.getTransaction().begin();
		getEntityManager().persist(entity);
		em.getTransaction().commit();
	}

	public void update(T entity) {
		em.getTransaction().begin();
		getEntityManager().merge(entity);
		em.getTransaction().commit();
	}

	public void remove(Long entityId) {
		T entity = find(entityId);

		if (entity != null)
			remove(entity);
	}

	public void remove(T entity) {
		em.getTransaction().begin();
		getEntityManager().remove(getEntityManager().merge(entity));
		em.getTransaction().commit();
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll() {
		CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(
		    entityClass);
		cq.select(cq.from(entityClass));

		return getEntityManager().createQuery(cq).getResultList();
	}

	public List<T> findRange(int[] range) {
		CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(
		    entityClass);
		cq.select(cq.from(entityClass));

		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);

		return q.getResultList();
	}

	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery(
		    entityClass);
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));

		javax.persistence.Query q = getEntityManager().createQuery(cq);

		return ((Long) q.getSingleResult()).intValue();
	}

}