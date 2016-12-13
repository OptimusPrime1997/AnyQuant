/**
 * 
 */
package bl.detailBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import utility.MyDate;
import utility.Range_Date;
import utility.exception.TimeOut_exception;
import vo.KDJVO;

/**
 * @author 1
 *
 */
public class KDJHelperTest {

	/**
	 * @throws java.lang.Exception
	 */
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

	@Test
	public void test() {
		Helper kdjHelper = new Helper();
		ArrayList<KDJVO> kdjVOs=new ArrayList<KDJVO>();
		try {
			kdjVOs = kdjHelper.getKDJ("hs300",
					new Range_Date(new MyDate("2016-03-01"), new MyDate("2016-04-01")));
		} catch (TimeOut_exception | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator<KDJVO> t = kdjVOs.iterator(); t.hasNext();) {
			System.out.println(t.next().toString());
		}
	}

}
