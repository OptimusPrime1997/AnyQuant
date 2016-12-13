/**
 * 
 */
package data.datasql;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import utility.exception.ExistModel_exception;
import utility.exception.NotFoundModel_exception;

/**
 * @author 1
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelDataImpTest {
	private ModelDataImp modelDataImp;

	/**
	 * 
	 */
	public ModelDataImpTest() {
		// TODO Auto-generated constructor stub
		this.modelDataImp = new ModelDataImp();
	}

	/**
	 * @throws java.lang.Exception
	 */
//	@Test
//	public void test1InsertModel() {
//		boolean result = false;
//		try {
//			result = modelDataImp.insertScore("1111111", 0.03);
//		} catch (ExistModel_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		assertEquals(true, result);
//	}

	@Test
	public void test2UpdateModel() {
		boolean result1 = false;
		try {
			result1 = modelDataImp.updateScore("1111111", 81);
		} catch (NotFoundModel_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, result1);
	}

	@Test
	public void test3GetValue() {
		double po = 0;
		try {
			po = modelDataImp.getScore("1111111");
		} catch (NotFoundModel_exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0.05, po, 0.0001);

	}

//	@Test
//	public void test4DeleteModel() {
//		boolean result = false;
//		try {
//			result = modelDataImp.deleteScore("1111111");
//		} catch (NotFoundModel_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		assertEquals(true, result);
//	}

}
