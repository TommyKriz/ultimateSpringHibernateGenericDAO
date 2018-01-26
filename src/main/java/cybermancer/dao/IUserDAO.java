package cybermancer.dao;

import cybermancer.dao.generic.GenericDAO;
import cybermancer.dto.User;

public interface IUserDAO extends GenericDAO<User> {

	public User getByName(String name);

}
