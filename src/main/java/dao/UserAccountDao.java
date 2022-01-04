package dao;

import model.UserAccount;

public interface UserAccountDao {

	/***
	 * Adds a new UserAccount to the database
	 * @param newUser a new user account object to be added to the database
	 * @return true on successful insertion, otherwise false
	 */
	public boolean insertUserAccount(UserAccount newUser);
	
	public boolean insertAccountFridgeJunction(String username, int fridgeID);
		
	/***
	 * Gets an account from the database by username
	 * @param username
	 * @return UserAccount object with associated username (unique identifier)
	 */
	public UserAccount getAccountByUsername(String username);
		
	/***
	 * Deletes an account from the database
	 * @param username
	 * @return true on successful delete otherwise false
	 */
	public boolean deleteAccount(String username);
}