package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Fridge;

public class FridgeDaoImpl implements FridgeDao {
		
	@Override
	public boolean addFridge(String name, String owner) {
		String insert = "INSERT INTO refrigerators VALUES(DEFAULT, ?, ?)";
		int rows = 0;
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, name);
			ps.setString(2, owner);
			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows != 0;
	}

	@Override
	public List<Fridge> getFridgesByUsername(String username) {
		List<Fridge> fridges = new LinkedList<>();
		try(Connection conn = DBConnection.getConnection()) {
			String selectAccount = "SELECT * FROM refrigerators WHERE owner_name = ?";
			PreparedStatement select = conn.prepareStatement(selectAccount);
			select.setString(1, username);
			
			ResultSet fridgesSet = select.executeQuery();
			
			while (fridgesSet.next()) {
				fridges.add(new Fridge(fridgesSet.getInt(1), fridgesSet.getString(2), fridgesSet.getString(3)));
			}
		} catch (SQLException e) {
			return null;
		}
		return fridges;
	}
	
	@Override
	public Fridge getFridgeByID(int fridgeID) {
		Fridge fridge = null;
		try(Connection conn = DBConnection.getConnection()) {
			String selectAccount = "SELECT * FROM refrigerators WHERE r_id = ?";
			PreparedStatement select = conn.prepareStatement(selectAccount);
			select.setInt(1, fridgeID);
			
			ResultSet fridgeSet = select.executeQuery();
			
			if (fridgeSet.next()) {
				fridge = new Fridge(fridgeSet.getInt(1), fridgeSet.getString(2), fridgeSet.getString(3));
			}
		} catch (SQLException e) {
		}
		return fridge;
	}
	
	@Override
	public boolean deleteFridge(int id) {
		int rows = 0;
		try(Connection conn = DBConnection.getConnection()) {
			String delete = "DELETE FROM refrigerators WHERE r_id = ?";
			PreparedStatement ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			
			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows != 0;
	}

	@Override
	public List<Fridge> getFridgesByInspector(String username) {
		List<Fridge> fridges = new LinkedList<>();
		try(Connection conn = DBConnection.getConnection()) {
			String selectAccount = "SELECT r_id, r_name, owner_name FROM get_health_inspector_fridges WHERE acct_username = ?";
			PreparedStatement select = conn.prepareStatement(selectAccount);
			select.setString(1, username);
			
			ResultSet fridgesSet = select.executeQuery();
			
			while (fridgesSet.next()) {
				fridges.add(new Fridge(fridgesSet.getInt(1), fridgesSet.getString(2), fridgesSet.getString(3)));
			}
		} catch (SQLException e) {
			return null;
		}
		return fridges;
	}
}
