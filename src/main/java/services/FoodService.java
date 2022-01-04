package services;

import java.util.List;

import model.Food;

public interface FoodService {
	public boolean addFood(String food, int fridgeID);
	public boolean removeFood(int foodID);
	public boolean transferFood(int foodID, int fridgeID);
	public List<Food> getFoodByFridgeID(int fridgeID);
}
