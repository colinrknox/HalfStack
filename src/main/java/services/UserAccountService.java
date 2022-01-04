package services;

import model.UserAccount;

public interface UserAccountService {
	public boolean addUser(String username, String password);
	public UserAccount getUserWithAuthenticate(String username, String password);
	public boolean addUserToFridge(String username, int fridgeID);
}
