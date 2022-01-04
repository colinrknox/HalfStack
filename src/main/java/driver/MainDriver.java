package driver;

import java.util.List;
import java.util.Scanner;

import dao.FoodDao;
import dao.FoodDaoImpl;
import dao.FridgeDao;
import dao.FridgeDaoImpl;
import dao.UserAccountDao;
import dao.UserAccountDaoImpl;
import model.Food;
import services.FoodService;
import services.FoodServiceImpl;
import services.FridgeService;
import services.FridgeServiceImpl;
import services.PermissionService;
import services.UserAccountService;
import services.UserAccountServiceImpl;

public class MainDriver {

	public static void main(String[] args) {
		FoodDao foodDao = new FoodDaoImpl();
		FridgeDao fridgeDao = new FridgeDaoImpl();
		UserAccountDao userDao = new UserAccountDaoImpl();
		FoodService foodService = new FoodServiceImpl(foodDao, fridgeDao);
		FridgeService fridgeService = new FridgeServiceImpl(fridgeDao, foodService);
		UserAccountService userService = new UserAccountServiceImpl(userDao, fridgeDao);
		PermissionService permService = PermissionService.getPermissionServiceSingle();
		boolean isHealthInspector = false;
		boolean exitFlag = true;
		Scanner input = new Scanner(System.in);

		// Account type and login loop
		while (exitFlag) {
			PrintMessages.restaurantOrInspector();
			String response = input.nextLine();
			switch (response.toLowerCase()) {
			case "1":
			case "restaurant":
				break;
			case "2":
			case "health inspector":
				isHealthInspector = true;
				break;
			default:
				PrintMessages.invalidResponse();
				break;
			}
			PrintMessages.loginRegister();
			response = input.nextLine();
			switch (response.toLowerCase()) {
			case "1":
			case "login":
				System.out.print("Login: ");
				String loginName = input.nextLine();
				System.out.print("Password: ");
				String loginPass = input.nextLine();
				permService.setUser(userService.getUserWithAuthenticate(loginName, loginPass));
				if (permService.getUser() == null) {
					System.out.println("Login failed");
				}
				break;
			case "2":
			case "register":
				System.out.print("Login: ");
				loginName = input.nextLine();
				System.out.print("Password: ");
				loginPass = input.nextLine();
				if (!userService.addUser(loginName, loginPass)) {
					System.out.println("Registration failed, username already exists");
				}
				break;
			case "3":
			case "exit":
				exitFlag = false;
				PrintMessages.exit();
				break;
			default:
				PrintMessages.invalidResponse();
				break;
			}
			// actions loop
			while (permService.getUser() != null) {
				// Update user data in the permission service
				if (isHealthInspector)
					permService.getUser().setFridges(fridgeService.getFridgesByInspector(permService.getUser().getUsername()));
				else
					permService.getUser().setFridges(fridgeService.getFridgesByOwner(permService.getUser().getUsername()));
				
				String item, healthInspector, fridgeTo;
				Integer foodID, fridgeToID;
				
				// Separate menu interfaces for health inspectors/restaurant owners
				if (!isHealthInspector) {
					PrintMessages.restaurantMainMenu();
					response = input.nextLine();
					switch (response.toLowerCase()) {
					case "1":
					case "add food":
						System.out.print("Name of food to add: ");
						item = input.nextLine();
						fridgeToID = scannerIntInput(input, "ID number of fridge (integer): ");
						if (fridgeToID != null && permService.userHasPermissionFridge(fridgeToID) && foodService.addFood(item, fridgeToID)) {
							System.out.println(item + " added to fridge with id " + fridgeToID);
						} else {
							System.out.println("Not able to add to fridge");
						}
						break;
					case "2":
					case "remove food":
						foodID = scannerIntInput(input, "ID of food to remove/consume (integer): ");
						if (foodID != null && permService.userHasPermissionFood(foodID) && foodService.removeFood(foodID)) {
							System.out.println("Food removed");
						} else {
							System.out.println("Food not found");
						}
						break;
					case "3":
					case "transfer food":
						foodID = scannerIntInput(input, "ID of food to transfer (integer): ");
						fridgeToID = scannerIntInput(input, "ID of fridge to be transferred to (integer): ");
						if (foodID != null
								&& fridgeToID != null
								&& permService.userHasPermissionFood(foodID)
								&& permService.userHasPermissionFridge(fridgeToID)
								&& foodService.transferFood(foodID, fridgeToID)) {
							System.out.println("Food successfully transferred");
						} else {
							System.out.println("Incorrect food/fridge ID");
						}
						break;
					case "4":
					case "add refrigerator":
						System.out.print("Name of fridge to be added: ");
						fridgeTo = input.nextLine();
						if (fridgeService.addFridge(fridgeTo, permService.getUser().getUsername())) {
							System.out.println("Refrigerator " + fridgeTo + " added");
						}
						break;
					case "5":
					case "remove refrigerator":
						fridgeToID = scannerIntInput(input, "ID of fridge to be removed (integer): ");
						List<Food> overflow = null;
						if (fridgeToID != null && permService.userHasPermissionFridge(fridgeToID))
							overflow = fridgeService.removeFridge(fridgeToID, permService.getUser().getUsername());
						if (overflow != null) {
							System.out.println("Food thrown away: " + overflow.toString());
						}
						break;
					case "6":
					case "see refrigerators":
						System.out.println(permService.getUser().getFridges());
						break;
					case "7":
					case "add health inspector":
						System.out.print("Health inspector to be added: ");
						healthInspector = input.nextLine();
						fridgeToID = scannerIntInput(input, "ID of fridge to be affected: ");
						if (fridgeToID != null
								&& permService.userHasPermissionFridge(fridgeToID)
								&& !healthInspector.equals(permService.getUser().getUsername())
								&& userService.addUserToFridge(healthInspector, fridgeToID)) {
							System.out.println("Inspector " + healthInspector + " added to fridge " + fridgeToID);
						}
						break;
					case "8":
					case "logout":
						permService.setUser(null);
						System.out.println("Logging out...");
						break;
					case "9":
					case "exit":
						permService.setUser(null);
						exitFlag = false;
						PrintMessages.exit();
						break;
					default:
						PrintMessages.invalidResponse();
						break;
					}
				} else {
					PrintMessages.healthMainMenu();
					response = input.nextLine();
					switch (response.toLowerCase()) {
					case "1":
					case "view refrigerators":
						System.out.println(permService.getUser().getFridges());
						break;
					case "2":
					case "remove food":
						foodID = scannerIntInput(input, "ID of food to remove (integer):");
						if (foodID != null && permService.userHasPermissionFood(foodID) && foodService.removeFood(foodID)) {
							System.out.println("Item removed");
						} else {
							System.out.println("Food doesn't exist or this inspector doesn't have access");
						}
						break;
					case "3":
					case "logout":
						permService.setUser(null);
						System.out.println("Logging out...");
						break;
					case "4":
					case "exit":
						permService.setUser(null);
						exitFlag = false;
						PrintMessages.exit();
						break;
					default:
						PrintMessages.invalidResponse();
						break;
					}
				}
			}
		}
		input.close();
	}
	
	// Trevin's method to check integer input from console
	public static Integer scannerIntInput(Scanner myScan , String prompt) {
		System.out.println(prompt);
		try {
			String deletePKInput = myScan.nextLine();
			int deletePKConverted = Integer.parseInt(deletePKInput);
			return deletePKConverted;
		}catch(NumberFormatException e) {
			System.out.println("Sorry, you entered text that is not a number");
		}
		return null;
	}
}
