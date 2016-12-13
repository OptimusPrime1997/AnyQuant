package bl.loginBL;

import data.UserData;
import po.UserPO;
import utility.exception.NotFoundName_exception;
import utility.exception.WrongPwd_exception;

public class Login {
	UserData userData;
	
	public Login(){
		userData=new UserData();
	}
	/**
	 * this is the method to log in
	 * @param id
	 * @param pwd
	 * @return
	 * @throws NotFoundName_exception 
	 * @throws ExistID_exception
	 * @throws NoId_exception
	 * @throws NoPwd_exception
	 */
	public boolean login(String id,String pwd)throws WrongPwd_exception, NotFoundName_exception{
		UserPO po=userData.getUser(id);
		
		if(po.getPassword().equals(pwd)){
			
			return true;
		}
		else{
			throw new WrongPwd_exception();
		}
	}
}
