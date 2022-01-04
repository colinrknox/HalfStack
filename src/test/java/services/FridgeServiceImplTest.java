package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dao.FridgeDao;
import model.Food;
import model.Fridge;

class FridgeServiceImplTest {

	FridgeDao fridgeDaoMock = Mockito.mock(FridgeDao.class);
	FoodService foodServiceMock = Mockito.mock(FoodService.class);
	FridgeService testObject = new FridgeServiceImpl(fridgeDaoMock, foodServiceMock);
	
	Fridge fullFridge = new Fridge(1, "myFridge", "owner");
	Fridge emptyFridge = new Fridge(2, "emptyFridge", "owner");
	Fridge oneItemFridge = new Fridge(3, "singleFridge", "owner");
	
	int id;
	String name;

	@BeforeEach
	void setUp() throws Exception {
		id = 3;
		name = "owner";
		
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
	void testGetFridgesByOwnerDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgesByUsername(name)).thenReturn(null);
		
		List<Fridge> results = testObject.getFridgesByOwner(name);
		
		assertNull(results);
	}
	
	@Test
	void testGetFridgesByOwnerSuccess() {
		Mockito.when(fridgeDaoMock.getFridgesByUsername(name)).thenReturn(Arrays.asList(fullFridge));
		Mockito.when(foodServiceMock.getFoodByFridgeID(id)).thenReturn(fullFridge.getItems());
		
		List<Fridge> result = testObject.getFridgesByOwner(name);
		
		assertEquals(result, Arrays.asList(fullFridge));
	}
	
	@Test
	void testGetFridgesByHIDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgesByInspector(name)).thenReturn(null);
		
		List<Fridge> results = testObject.getFridgesByInspector(name);
		
		assertNull(results);
	}
	
	@Test
	void testGetFridgesByHISuccess() {
		Mockito.when(fridgeDaoMock.getFridgesByInspector(name)).thenReturn(Arrays.asList(fullFridge));
		Mockito.when(foodServiceMock.getFoodByFridgeID(id)).thenReturn(fullFridge.getItems());
		
		List<Fridge> result = testObject.getFridgesByInspector(name);
		
		assertEquals(result, Arrays.asList(fullFridge));
	}
	
	@Test
	void testRemoveFridgeDoesntExist() {
		Mockito.when(fridgeDaoMock.getFridgesByUsername(name)).thenReturn(null);
		
		List<Food> result = testObject.removeFridge(id, name);
		
		assertNull(result);
	}
	
	@Test
	void testRemoveFridgeNoRoom() {
		Mockito.when(fridgeDaoMock.getFridgesByUsername(name)).thenReturn(Arrays.asList(fullFridge, oneItemFridge));
		Mockito.when(foodServiceMock.getFoodByFridgeID(id)).thenReturn(oneItemFridge.getItems());
		
		List<Food> result = testObject.removeFridge(id, name);
		
		assertEquals(result, oneItemFridge.getItems());
	}
	
	@Test
	void testRemoveFridgeAllItemsSaved() {
		Mockito.when(fridgeDaoMock.getFridgesByUsername(name)).thenReturn(Arrays.asList(fullFridge, emptyFridge));
		Mockito.when(foodServiceMock.getFoodByFridgeID(id)).thenReturn(fullFridge.getItems());
		Mockito.when(foodServiceMock.transferFood(1, 2)).thenReturn(true);
		Mockito.when(foodServiceMock.transferFood(2, 2)).thenReturn(true);
		Mockito.when(foodServiceMock.transferFood(3, 2)).thenReturn(true);
		
		List<Food> result = testObject.removeFridge(id, name);
		
		assertEquals(result, new LinkedList<>());
	}
	
	@Test
	void testAddFridge() {
		Mockito.when(fridgeDaoMock.addFridge(name, name)).thenReturn(true);
		
		boolean result = testObject.addFridge(name, name);
		
		assertTrue(result);
	}
	
	@Test
	void testGetFridgeByFridgeID() {
		Mockito.when(fridgeDaoMock.getFridgeByID(id)).thenReturn(fullFridge);
		
		Fridge result = testObject.getFridgeByID(id);
		
		assertEquals(result, fullFridge);
	}
}
