package bl.loginBL;

import data.UserData;
import po.UserPO;
import utility.exception.ExistName_exception;
import utility.exception.NoName_exception;
import utility.exception.NoPwd_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.NotSamePwd_exception;

public class Register {
	UserData userData;
	
	public Register(){
		userData=new UserData();
	}
	/**
	 * this is the method to register
	 * @param id
	 * @param pwd
	 * @return
	 * @throws ExistName_exception
	 * @throws NoId_exception
	 * @throws NoPwd_exception
	 * @throws NoName_exception
	 */
	public boolean register(String id,String pwd,String confirm)throws ExistName_exception,
	NoName_exception, NoPwd_exception, NotSamePwd_exception{
		if(pwd.equals("")){
			throw new NoPwd_exception();
		}
		if(confirm.equals("")){
			throw new NoPwd_exception();
		}
		if(!pwd.equals(confirm)){
			throw new NotSamePwd_exception();
		}
		if(id==null||id==""){
			throw new NoName_exception();
		} else {
			try {
				UserPO po=userData.getUser(id);
				throw new ExistName_exception();
			} catch (NotFoundName_exception e) {
				boolean result=false;
				result=userData.regist(id, pwd);
				return result;
			}
			
			
		}
	}
}
