package services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Fridge;
import model.UserAccount;

public class PermissionService {
	
	static PermissionService permissionService;
	UserAccount user;
	FridgeService fridgeService;
	
	private PermissionService() {
	}
	
	public static PermissionService getPermissionServiceSingle() {
		if (permissionService == null)
			permissionService = new PermissionService();
		return permissionService;
	}
	
	public void setUser(UserAccount user) {
		this.user = user;
	}
	
	public UserAccount getUser() {
		return user;
	}

	/***
	 * Checks if a particular user has ownership over a particular food
	 * @param foodID
	 * @return true if the restaurant has ownership over the food
	 */
	public boolean userHasPermissionFood(int foodID) {
		List<Fridge> fridges = user.getFridges();
		if (fridges == null)
			return false;
		
		Set<Integer> result = new HashSet<>();
		for (Fridge f : fridges) {
			result.addAll(f.getItems().stream().map((food)->food.getID()).collect(Collectors.toSet()));
		}
		
		return result.contains(foodID);
	}

	/***
	 * Checks if a particular account has ownership over a particular food
	 * @param foodID
	 * @return true if the restaurant has ownership over the food
	 */
	public boolean userHasPermissionFridge(int fridgeID) {
		List<Fridge> fridges = user.getFridges();
		if (fridges == null)
			return false;
		Set<Integer> fridgeIDs = fridges.stream().map((f)->f.getID()).collect(Collectors.toSet());
		return fridgeIDs.contains(fridgeID);
	}
}
