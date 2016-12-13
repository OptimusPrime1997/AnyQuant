/**
 * 
 */
package data.datasql;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import po.UserPO;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author 1
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDataImplTest {
	private UserDataImp userDataImpl;

	/**
	* 
	*/
	public UserDataImplTest() {
		// TODO Auto-generated constructor stub
		this.userDataImpl = new UserDataImp();
	}

	/**
	 * @throws java.lang.Exception
	 */

	@Test
	public void test1Regist() {
		boolean result = false;
		try {
			result = userDataImpl.regist("anyone", "123456");
		} catch (ExistName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result);
	}

	@Test
	public void test2Add() {
		boolean result1 = false;
		boolean result2 = false;

		try {
			result1 = userDataImpl.add("anyone", "sh600000");
			result2 = userDataImpl.add("anyone", "sz002644");

		} catch (ExistID_exception | NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result1);
		assertEquals(true, result2);
	}

	@Test
	public void test3remove() {
		boolean result = false;
		try {
			result = userDataImpl.remove("anyone", "sh600000");
		} catch (NotExistId_exception | NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result);
	}

	@Test
	public void test4getUser() {
		UserPO po = null;
		try {
			po = userDataImpl.getUser("anyone");
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("123456", po.getPassword());
		assertEquals(1, po.getStocks().size());
		assertEquals("sz002644", po.getStocks().get(0));
	}

	@Test
	public void test5Delete() {
		boolean result = false;
		try {
			result = userDataImpl.delete("anyone");
		} catch (NotFoundName_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result);
	}
}
