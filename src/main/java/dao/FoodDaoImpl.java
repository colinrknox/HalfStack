package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Food;

public class FoodDaoImpl implements FoodDao {

	@Override
	public boolean insertFood(String name, int fridgeID) {
		String insert = "INSERT INTO food VALUES(DEFAULT, ?, ?)";
		int rows = 0;
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement ps = conn.prepareStatement(insert);
			ps.setString(1, name);
			ps.setInt(2, fridgeID);
			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows != 0;
	}

	@Override
	public boolean deleteFood(int foodID) {
		int rows = 0;
		try(Connection conn = DBConnection.getConnection()) {
			String delete = "DELETE FROM food WHERE f_id = ?";
			PreparedStatement ps = conn.prepareStatement(delete);
			ps.setInt(1, foodID);
			
			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows != 0;
	}

	@Override
	public List<Food> getFoodByFridgeID(int id) {
		String selectFood = "SELECT * FROM food WHERE fridge_id = ?";
		List<Food> foodList = new LinkedList<>();
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement foodStatement = conn.prepareStatement(selectFood);
			foodStatement.setInt(1, id);
			ResultSet foodSet = foodStatement.executeQuery();
			
			while (foodSet.next()) {
				foodList.add(new Food(foodSet.getInt(1), foodSet.getString(2), foodSet.getInt(3)));
			}
		} catch (SQLException e) {
		}
		return foodList;
	}

	@Override
	public boolean updateFood(Food f) {
		int rows = 0;
		try(Connection conn = DBConnection.getConnection()) {
			String update = "UPDATE food SET fridge_id = ? WHERE f_id = ?";
			PreparedStatement ps = conn.prepareStatement(update);
			ps.setInt(1, f.getFridgeID());
			ps.setInt(2, f.getID());
			
			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows != 0;
	}

	@Override
	public Food getFoodByFoodID(int foodID) {
		String selectFood = "SELECT * FROM food WHERE f_id = ?";
		Food food = null;
		try(Connection conn = DBConnection.getConnection()) {
			PreparedStatement foodStatement = conn.prepareStatement(selectFood);
			foodStatement.setInt(1, foodID);
			ResultSet foodSet = foodStatement.executeQuery();
			
			if (foodSet.next()) {
				food = new Food(foodSet.getInt(1), foodSet.getString(2), foodSet.getInt(3));
			}
		} catch (SQLException e) {
		}
		return food;
	}

}
