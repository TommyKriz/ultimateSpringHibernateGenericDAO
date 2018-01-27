package cybermancer.dao.generic;

import java.util.List;

public interface IGenericDAO<T> {

	public void save(T t);

	public T get(int id);

	public void delete(T t);

	public void delete(int id);

	public void update(T t);

	public T getLatest();

	public List<T> getAll();

	public List<T> getLast(int n);

}
