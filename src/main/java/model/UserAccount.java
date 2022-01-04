package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

/***
 * Class for holding user account information
 * @author Colin Knox
 *
 */
public class UserAccount implements Serializable {
	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 2278004298375048976L;
	
	String username;
	String password;
	List<Fridge> fridges;
	
	public UserAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.fridges = new LinkedList<Fridge>();
	}
	
	public UserAccount(String username, String password, List<Fridge> fridges) {
		this.username = username;
		this.password = password;
		this.fridges = fridges;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Fridge> getFridges() {
		return fridges;
	}
	
	public void setFridges(List<Fridge> fridges) {
		this.fridges = fridges;
	}

	@Override
	public String toString() {
		return "UserAccount [username=" + username + ", password=" + password + ", fridges=" + fridges + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fridges, password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return password.equals(other.password) && username.equals(other.username);
	}
}
