/**
 * 
 */
package bl.detailBL;
import data.UserData;
import po.UserPO;
import utility.exception.ExistID_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author run
 *
 */
public class RemoveHelper {
	UserData userData;
	UserPO userPO;
	/**
	 * struct method
	 */
	public RemoveHelper(){
		userData=new UserData();
	}
	
	
	/**
	 * remove a stock from prefered
	 * @param id,name
	 * @return
	 * @throws NotFoundName_exception 
	 * @throws ExistID_exception 
	 * @throws NotExistId_exception 
	 */
	public boolean select(String name,String id) throws NotFoundName_exception, ExistID_exception{
		boolean result=false;
//		boolean exist=false;
//		boolean temp=false;
		userPO=userData.getUser(name);
		
//		ArrayList<String> arrayList=userPO.getStocks();
//		Iterator<String> iterator=arrayList.iterator();
//		while(iterator.hasNext()){
//			String s=iterator.next();
//			if(s.equals(id)){
//				exist=true;
//			}
//		}
		
//		if(exist){
//			try {
//				temp=userData.remove(name, id);
//			} catch (NotExistId_exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
			result=userData.add(name, id);
		
		return result;
	}

}
