package cybermancer.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cybermancer.config.SpringConfig;
import cybermancer.dao.UserDao;
import cybermancer.dto.User;

/**
 * see <a href=
 * "http://www.logicbig.com/tutorials/spring-framework/spring-orm/spring-jpa-transaction/"
 * >http://www.logicbig.com/tutorials/spring-framework/spring-orm/spring-jpa-
 * transaction/</a>
 * 
 * @author Tommy
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
@Transactional
public class RealUserDaoTest {

	private static final String PASSWORD_USER_1 = "geheim";
	private static final String USER_NAME_1 = "Hugo";

	private static final String PASSWORD_USER_2 = "passwort";
	private static final String USER_NAME_2 = "Johnny";

	private static final String PASSWORD_USER_3 = "root";
	private static final String USER_NAME_3 = "Marlene";

	@Autowired
	UserDao userService;

	@Test
	@Rollback(true)
	public void oneUser() {

		User u = new User();
		u.setName(USER_NAME_1);
		u.setPassword(PASSWORD_USER_1);

		userService.save(u);

		User uu = userService.get(1);

		assertEquals(USER_NAME_1, uu.getName());
		assertEquals(PASSWORD_USER_1, userService.getByName(USER_NAME_1)
				.getPassword());

		uu.setPassword("dadadadam");
		userService.save(uu);

		assertEquals("dadadadam", userService.getByName(USER_NAME_1)
				.getPassword());

	}

	@Test
	@Rollback(true)
	public void twoUsers() {

		User u1 = new User();
		u1.setName(USER_NAME_1);
		u1.setPassword(PASSWORD_USER_1);

		userService.save(u1);

		User u2 = new User();
		u2.setName(USER_NAME_2);
		u2.setPassword(PASSWORD_USER_2);

		userService.save(u2);

		assertEquals(2, userService.getAll().size());

		userService.delete(userService.getByName(USER_NAME_2));

		assertEquals(1, userService.getAll().size());

		assertEquals(userService.getByName(USER_NAME_2), null);

		userService.save(u2);

		assertEquals(2, userService.getAll().size());
	}

	@Test
	@Rollback(true)
	public void threeUsers() {
		User u1 = new User();
		u1.setName(USER_NAME_1);
		u1.setPassword(PASSWORD_USER_1);

		userService.save(u1);

		User u2 = new User();
		u2.setName(USER_NAME_2);
		u2.setPassword(PASSWORD_USER_2);

		userService.save(u2);

		User u3 = new User();
		u3.setName(USER_NAME_3);
		u3.setPassword(PASSWORD_USER_3);

		userService.save(u3);

		List<User> users = userService.getAll();

		assertEquals(3, users.size());
		assertEquals(USER_NAME_3, users.get(2).getName());

		users = userService.getLast(2);

		assertEquals(2, users.size());
		assertEquals(USER_NAME_3, users.get(0).getName());

		assertEquals(USER_NAME_3, userService.getLatest().getName());

	}

}
