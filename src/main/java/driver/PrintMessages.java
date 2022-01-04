package driver;

public class PrintMessages {
	
	public static void restaurantOrInspector() {
		System.out.println("\nSelect the type of account you would like to access.\n1. Restaurant\n2. Health Inspector\n3. Exit");
	}
	public static void loginRegister() {
		System.out.println("\n1. Login\n2. Register\n3. Exit");
	}
	
	public static void restaurantMainMenu() {
		System.out.println("\n1. Add Food\n2. Remove Food\n3. Transfer Food\n4. Add Refrigerator\n5. Remove Refrigerator\n6. See Refrigerators\n7. Add Health Inspector to Refrigerator\n8. Logout\n9. Exit");
	}
	
	public static void healthMainMenu() {
		System.out.println("\n1. View Refrigerators\n2. Remove food\n3.Logout\n4. Exit");
	}
	
	public static void exit() {
		System.out.println("\nExiting...");
	}
	
	public static void invalidResponse() {
		System.out.println("\nInvalid input.\n");
	}
}
