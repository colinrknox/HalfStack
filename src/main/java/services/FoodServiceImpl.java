package services;

import java.util.List;

import dao.FoodDao;
import dao.FridgeDao;
import model.Food;

public class FoodServiceImpl implements FoodService {

	FoodDao dao;
	FridgeDao fridgeDao;
	static final int MAX_CAPACITY = 3;
	
	public FoodServiceImpl(FoodDao dao, FridgeDao fridgeDao) {
		this.dao = dao;
		this.fridgeDao = fridgeDao;
	}
	
	@Override
	public boolean addFood(String food, int fridgeID) {
		if (fridgeDao.getFridgeByID(fridgeID) == null || dao.getFoodByFridgeID(fridgeID).size() >= MAX_CAPACITY)
			return false;
		return dao.insertFood(food, fridgeID);
	}

	@Override
	public boolean removeFood(int foodID) {
		return dao.deleteFood(foodID);
	}

	@Override
	public boolean transferFood(int foodID, int fridgeID) {
		List<Food> foods = getFoodByFridgeID(fridgeID);
		if (fridgeDao.getFridgeByID(fridgeID) == null || foods.size() >= MAX_CAPACITY)
			return false;
		
		Food editedFood = dao.getFoodByFoodID(foodID);
		if (editedFood == null)
			return false;
		
		editedFood.setFridgeID(fridgeID);
		return dao.updateFood(editedFood);
	}

	@Override
	public List<Food> getFoodByFridgeID(int fridgeID) {
		if (fridgeDao.getFridgeByID(fridgeID) == null)
			return null;
		return dao.getFoodByFridgeID(fridgeID);
	}

}
