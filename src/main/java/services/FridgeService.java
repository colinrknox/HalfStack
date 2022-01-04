package services;

import java.util.List;

import model.Food;
import model.Fridge;

public interface FridgeService {
	public boolean addFridge(String name, String owner);
	public List<Food> removeFridge(int fridgeID, String owner);
	public List<Fridge> getFridgesByOwner(String username);
	public List<Fridge> getFridgesByInspector(String inspector);
	public Fridge getFridgeByID(int fridgeID);
}
