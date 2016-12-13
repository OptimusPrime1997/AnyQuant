/**
 * 
 */
package data;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import data.UserData;
import po.UserPO;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author 1 this the UserData test case
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDataTest {
	private UserData userData;

	public UserDataTest() {
		// TODO Auto-generated constructor stub
		userData = new UserData();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1Regist() {
		boolean result = false;
		try {
			result = userData.regist("JLBuffett", "123456");
		} catch (ExistName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result);
	}

	@Test
	public void test2GetUser() {
		UserPO userPO = null;
		try {
			userPO = userData.getUser("JLBuffett");
			assertEquals("JLBuffett", userPO.getName());
			assertEquals("123456", userPO.getPassword());
			assertEquals(0, userPO.getStocks().size());
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test3Add() {
		boolean result;
		try {
			result = userData.add("JLBuffett", "000001");
		} catch (ExistID_exception | NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserPO userPO = null;
		try {
			userPO = userData.getUser("JLBuffett");
			assertEquals(1, userPO.getStocks().size());
			assertEquals("000001", userPO.getStocks().get(0));
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void test4Remove() {
		boolean result = false;
		try {
			userData.add("JLBuffett", "000002");
			result = userData.remove("JLBuffett", "000001");
		} catch (ExistID_exception | NotFoundName_exception | NotExistId_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserPO userPO = null;
		try {
			userPO = userData.getUser("JLBuffett");
			assertEquals(1, userPO.getStocks().size());
			assertEquals("000002", userPO.getStocks().get(0));
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
