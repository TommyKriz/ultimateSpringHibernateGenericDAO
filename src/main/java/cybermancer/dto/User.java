package cybermancer.dto;

import javax.persistence.Entity;
import javax.persistence.Table;

import cybermancer.dto.generic.GenericDTO;

@Entity
@Table(name = "USERS")
public class User extends GenericDTO<User> {

	public User() {

	}

	private String name;

	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
