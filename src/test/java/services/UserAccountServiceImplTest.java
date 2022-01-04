package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import dao.FridgeDao;
import dao.UserAccountDao;
import model.Fridge;
import model.UserAccount;

class UserAccountServiceImplTest {
	
	UserAccountDao userDao = Mockito.mock(UserAccountDao.class);
	FridgeDao fridgeDao = Mockito.mock(FridgeDao.class);
	UserAccountService testObject = new UserAccountServiceImpl(userDao, fridgeDao);
	
	String username, password = "colin";
	UserAccount acct = new UserAccount(username, password);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddUser() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(null);
		Mockito.when(userDao.insertUserAccount((UserAccount)notNull())).thenReturn(true);
		
		boolean result = testObject.addUser(username, password);
		
		assertTrue(result);
	}
	
	@Test
	void testAddUserAlreadyExists() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(acct);
		
		boolean result = testObject.addUser(username, password);
		
		assertFalse(result);
	}

	@Test
	void testGetUserWithAuthenticateAccountNotFound() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(null);
		
		UserAccount result = testObject.getUserWithAuthenticate(username, password);
		
		assertNull(result);
	}
	
	@Test
	void testGetUserWithPassNotMatching() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(any(UserAccount.class));
		
		UserAccount result = testObject.getUserWithAuthenticate(username, password);
		
		assertNull(result);
	}
	
	@Test
	void testGetUserWithAuthenticateSuccess() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(acct);
		
		UserAccount result = testObject.getUserWithAuthenticate(username, password);
		
		assertEquals(acct, result);
	}

	@Test
	void testAddUserToFridgeConditionFail() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(null);
		
		boolean result = testObject.addUserToFridge(username, 0);
		
		assertFalse(result);
	}
	
	@Test
	void testAddUserToFridgeConditionSatisfied() {
		Mockito.when(userDao.getAccountByUsername(username)).thenReturn(acct);
		Mockito.when(fridgeDao.getFridgeByID(1)).thenReturn(new Fridge(0, password, password));
		Mockito.when(userDao.insertAccountFridgeJunction(username, 1)).thenReturn(true);
		
		boolean result = testObject.addUserToFridge(username, 1);
		
		assertTrue(result);
	}

}
