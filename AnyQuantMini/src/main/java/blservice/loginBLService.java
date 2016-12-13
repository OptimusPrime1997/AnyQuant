package blservice;

import utility.exception.ExistName_exception;
import utility.exception.NoName_exception;
import utility.exception.NoPwd_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.NotSamePwd_exception;
import utility.exception.WrongPwd_exception;

/**
 * this is the interface of loginBL to interact with loginUI
 * @author run
 *
 */
public interface loginBLService {
	/**
	 * this is the method to log in
	 * @param id
	 * @param password
	 * @return
	 * @throws WrongPwd_exception
	 */
	boolean login(String id,String password)throws WrongPwd_exception,NotFoundName_exception;
	
	/**
	 * this the method to register
	 * @param id
	 * @param password
	 * @return
	 * @throws ExistName_exception
	 * @throws NoId_exception
	 * @throws NoPwd_exception
	 */
	boolean register(String id,String password,String confirm)throws ExistName_exception,
	NoName_exception,NoPwd_exception,NotSamePwd_exception;
}
