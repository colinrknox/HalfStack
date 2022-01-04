package services;

import java.util.List;
import java.util.stream.Collectors;

import dao.FridgeDao;
import model.Food;
import model.Fridge;

public class FridgeServiceImpl implements FridgeService {
	
	FridgeDao dao;
	FoodService foodService;
	
	public FridgeServiceImpl(FridgeDao dao, FoodService foodService) {
		this.dao = dao;
		this.foodService = foodService;
	}
	
	@Override
	public boolean addFridge(String name, String owner) {
		return dao.addFridge(name, owner);
	}

	@Override
	public List<Food> removeFridge(int fridgeID, String owner) {
		// get list of owner fridges
		List<Fridge> fridges = getFridgesByOwner(owner);
		if (fridges == null)
			return null;
		List<Integer> fridgeIDs = fridges.stream().map((f)->f.getID()).collect(Collectors.toList());
		List<Food> foodToMove = foodService.getFoodByFridgeID(fridgeID);
		// try to move items to other fridges until exhausted
		while(!foodToMove.isEmpty() && !fridgeIDs.isEmpty()) {
			if (foodService.transferFood(foodToMove.get(0).getID(), fridgeIDs.get(0))) {
				foodToMove.remove(0);
			} else {
				fridgeIDs.remove(0);
			}
		}
		// get leftover items, delete items from db
		//foodToMove = foodService.getFoodByFridgeID(fridgeID);
		dao.deleteFridge(fridgeID);
		return foodToMove;
	}

	@Override
	public List<Fridge> getFridgesByOwner(String username) {
		List<Fridge> fridges = dao.getFridgesByUsername(username);
		if (fridges == null)
			return fridges;
		
		for (Fridge f : fridges) {
			f.setItems(foodService.getFoodByFridgeID(f.getID()));
		}
		return fridges;
	}

	@Override
	public List<Fridge> getFridgesByInspector(String inspector) {
		List<Fridge> fridges = dao.getFridgesByInspector(inspector);
		if (fridges == null)
			return fridges;
		
		for (Fridge f : fridges) {
			f.setItems(foodService.getFoodByFridgeID(f.getID()));
		}
		return fridges;
	}
	
	@Override
	public Fridge getFridgeByID(int fridgeID) {
		return dao.getFridgeByID(fridgeID);
	}

}
