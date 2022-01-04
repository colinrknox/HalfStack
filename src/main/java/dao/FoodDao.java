package dao;

import java.util.List;

import model.Food;

public interface FoodDao {
	public boolean insertFood(String food, int fridgeID);
	public boolean deleteFood(int foodID);
	
	/***
	 * Gets the food items stored in a particular fridge
	 * @param fridgeID the id of the fridge to lookup
	 * @return An empty list if the fridge is empty, otherwise a list of the food object
	 */
	public List<Food> getFoodByFridgeID(int fridgeID);
	
	/***
	 * Gets a particular food item given an ID
	 * @param foodID
	 * @return
	 */
	public Food getFoodByFoodID(int foodID);
	public boolean updateFood(Food f);
}
