package dao;

import java.util.List;

import model.Fridge;

public interface FridgeDao {
	public boolean addFridge(String name, String owner);
	public List<Fridge> getFridgesByUsername(String username);
	public List<Fridge> getFridgesByInspector(String username);
	public Fridge getFridgeByID(int fridgeID);
	public boolean deleteFridge(int id);
}
