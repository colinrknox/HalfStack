package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UserAccount;

public class UserAccountDaoImpl implements UserAccountDao {
	
	FridgeDao fridgeDao = new FridgeDaoImpl();
	
	@Override
	public boolean insertUserAccount(UserAccount newUser) {
		String insertUser = "INSERT INTO accounts VALUES(?, ?)";
		return updateCalls(insertUser, newUser.getUsername(), newUser.getPassword());
	}
	
	@Override
	public boolean insertAccountFridgeJunction(String username, int fridgeID) {
		int numOfRowsChanged = 0;
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement insert = conn.prepareStatement("INSERT INTO h_inspector_fridge_junction VALUES(?, ?)");
			insert.setString(1, username);
			insert.setInt(2, fridgeID);
			numOfRowsChanged = insert.executeUpdate();
		} catch (SQLException e) {
		}
		return numOfRowsChanged != 0;
	}

	@Override
	public UserAccount getAccountByUsername(String username) {
		try(Connection conn = DBConnection.getConnection()) {
			String selectAccount = "SELECT * FROM accounts WHERE acct_username = ?";
			PreparedStatement select = conn.prepareStatement(selectAccount);
			select.setString(1, username);
			
			ResultSet account = select.executeQuery();
			
			if (account.next()) {
				return new UserAccount(account.getString(1), account.getString(2));
			}
		} catch (SQLException e) {
		}
		return null;
	}
	
	@Override
	public boolean deleteAccount(String username) {
		String insertUser = "DELETE FROM accounts WHERE account_username = ?";
		return updateCalls(insertUser, username);
	}
	
	
	private boolean updateCalls(String statement, String... values) {
		int numOfRowsChanged = 0;
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement insert = conn.prepareStatement(statement);
			for (int i = 0; i < values.length; i++) {
				insert.setString(i + 1, values[i]);
			}			
			numOfRowsChanged = insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numOfRowsChanged != 0;
	}
}
