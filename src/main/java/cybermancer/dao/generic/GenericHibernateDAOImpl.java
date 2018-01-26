package cybermancer.dao.generic;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cybermancer.dto.generic.GenericDTO;

@Repository
@MappedSuperclass
public abstract class GenericHibernateDAOImpl<T extends GenericDTO<T>>
		implements GenericDAO<T> {

	/**
	 * With the @PersistenceContext annotation, we are requesting a
	 * transactional EntityManager (also called "shared EntityManager" because
	 * it is a shared, thread-safe proxy for the actual transactional
	 * EntityManager) to be injected instead of the factory. That way we don't
	 * have to create a new EntityManager through EntityManagerFactory each time
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Used within Custom DAOs extending from this class!
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return em;
	}

	private Class<T> clazz;

	public GenericHibernateDAOImpl() {

		clazz = ((Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);

	}

	@Override
	public void save(T u) {
		if (u.getId() == 0) {
			em.persist(u);
		} else {
			update(u);
		}

	}

	@Override
	public T get(int id) {
		return em.find(clazz, id);
	}

	@Override
	public void delete(T t) {
		em.remove(t);
	}

	@Override
	public void delete(int id) {
		em.remove(get(id));
	}

	@Override
	public void update(T u) {
		em.merge(u);
	}

	@Override
	public T getLatest() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root);

		criteria.orderBy(builder.desc(root.get("id")));

		List<T> persons = em.createQuery(criteria).setMaxResults(1)
				.getResultList();

		return persons.get(0);
	}

	@Override
	public List<T> getAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root);

		List<T> persons = em.createQuery(criteria).getResultList();

		return persons;
	}

	@Override
	public List<T> getLast(int n) {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root);

		criteria.orderBy(builder.desc(root.get("id")));

		List<T> persons = em.createQuery(criteria).setMaxResults(n)
				.getResultList();

		return persons;
	}

}
