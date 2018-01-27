package cybermancer.dao;

import cybermancer.dao.generic.IGenericDAO;
import cybermancer.dto.User;

public interface IUserDAO extends IGenericDAO<User> {

	public User getByName(String name);

}
