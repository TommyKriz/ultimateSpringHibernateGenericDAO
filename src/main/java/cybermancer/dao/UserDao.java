package cybermancer.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cybermancer.dao.generic.GenericDAOImpl;
import cybermancer.dto.User;
import cybermancer.dto.User_;

@Repository
public class UserDao extends GenericDAOImpl<User> implements IUserDAO {

	private EntityManager em;

	@PostConstruct
	public void init() {
		em = getEntityManager();
	}

	/**
	 * NEEDS
	 * 
	 * https://mvnrepository.com/artifact/org.hibernate/hibernate-jpamodelgen
	 * 
	 * for generating the User_.name Annotation files -->
	 * 
	 * ADD this dependency to pom.xml, run mvn clean install and add the
	 * generated-sources folder to the build path.
	 * 
	 */
	@Override
	public User getByName(String name) {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(User_.name), name));

		List<User> persons = em.createQuery(criteria).getResultList();

		if (persons.size() == 0) {
			return null;
		} else {
			return persons.get(0);
		}
	}

}
