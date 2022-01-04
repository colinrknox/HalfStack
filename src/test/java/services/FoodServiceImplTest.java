package services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dao.FoodDao;
import dao.FridgeDao;
import model.Food;
import model.Fridge;

class FoodServiceImplTest {

	FridgeDao fridgeDaoMock = Mockito.mock(FridgeDao.class);
	FoodDao foodDaoMock = Mockito.mock(FoodDao.class);
	FoodService testObject = new FoodServiceImpl(foodDaoMock, fridgeDaoMock);
	
	Fridge fullFridge = new Fridge(1, "myFridge", "owner");
	Fridge emptyFridge = new Fridge(2, "emptyFridge", "owner");
	Fridge oneItemFridge = new Fridge(3, "singleFridge", "owner");
	
	int id;
	String name;

	@BeforeEach
	void setUp() throws Exception {
		id = 0;
		name = "food";
		
		List<Food> fullFridgeContents = new LinkedList<>();
		fullFridgeContents.add(new Food(1, "eggs", 1));
		fullFridgeContents.add(new Food(2, "cheese", 1));
		fullFridgeContents.add(new Food(3, "milk", 1));
		fullFridge.setItems(fullFridgeContents);
		
		List<Food> singleItemFridgeContents = new LinkedList<>();
		singleItemFridgeContents.add(new Food(4, "beef", 3));
		oneItemFridge.setItems(singleItemFridgeContents);
	}
	
	@Test
	void testAddFoodFridgeDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(null);
		
		boolean result = testObject.addFood(name, id);
		
		assertFalse(result);
	}
	
	@Test
	void testAddFoodWithFridgeFull() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(fullFridge);
		Mockito.when(foodDaoMock.getFoodByFridgeID(id)).thenReturn(fullFridge.getItems());
		
		boolean result = testObject.addFood(name, id);
		
		assertFalse(result);
	}
	
	@Test
	void testSuccessfulFoodAdd() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(emptyFridge);
		Mockito.when(foodDaoMock.getFoodByFridgeID(id)).thenReturn(emptyFridge.getItems());
		Mockito.when(foodDaoMock.insertFood(name, id)).thenReturn(true);
		
		boolean result = testObject.addFood(name, id);
		
		assertTrue(result);
	}
	
	@Test
	void testTransferFoodFridgeDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(null);
		
		boolean result = testObject.transferFood(id, id);
		
		assertFalse(result);
	}
	
	@Test
	void testTransferFoodDestinationFridgeFull() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(fullFridge);
		Mockito.when(foodDaoMock.getFoodByFridgeID(id)).thenReturn(fullFridge.getItems());
		
		boolean result = testObject.transferFood(id, id);
		
		assertFalse(result);
	}
	
	@Test
	void testTransferFoodWhenFoodDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id + 1)).thenReturn(fullFridge);
		Mockito.when(foodDaoMock.getFoodByFoodID(id)).thenReturn(null);
		Mockito.when(foodDaoMock.getFoodByFoodID(id + 1)).thenReturn(fullFridge.getItems().get(0));
		
		boolean result = testObject.transferFood(id, id + 1);
		
		assertFalse(result);
	}
	
	@Test
	void testTransferFoodSuccessful() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(emptyFridge);
		Mockito.when(foodDaoMock.getFoodByFridgeID(id)).thenReturn(emptyFridge.getItems());
		Mockito.when(foodDaoMock.getFoodByFoodID(id)).thenReturn(fullFridge.getItems().get(0));
		Mockito.when(foodDaoMock.updateFood(fullFridge.getItems().get(0))).thenReturn(true);
		
		boolean result = testObject.transferFood(id, id);
		
		assertTrue(result);
	}
	
	@Test
	void testRemoveFood() {
		Mockito.when(foodDaoMock.deleteFood(id)).thenReturn(true);
		
		boolean result = testObject.removeFood(id);
		
		assertTrue(result);
	}

}
