package services;

import dao.FridgeDao;
import dao.UserAccountDao;
import model.UserAccount;

public class UserAccountServiceImpl implements UserAccountService {
	
	UserAccountDao userDao;
	FridgeDao fridgeDao;
	
	public UserAccountServiceImpl(UserAccountDao userDao, FridgeDao fridgeDao) {
		this.userDao = userDao;
		this.fridgeDao = fridgeDao;
	}
	
	@Override
	public boolean addUser(String username, String password) {
		if (userDao.getAccountByUsername(username) == null) {
			return userDao.insertUserAccount(new UserAccount(username, password));
		}
		return false;
	}

	@Override
	public UserAccount getUserWithAuthenticate(String username, String password) {
		UserAccount user = userDao.getAccountByUsername(username);
		if (user == null)
			return null;
		return user.getPassword().equals(password) ?  user : null;
	}

	@Override
	public boolean addUserToFridge(String username, int fridgeID) {
		if (userDao.getAccountByUsername(username) != null && fridgeDao.getFridgeByID(fridgeID) != null) {
			return userDao.insertAccountFridgeJunction(username, fridgeID);
		}
		return false;
	}

}
