package bl.loginBL;

import blservice.loginBLService;
import utility.exception.ExistName_exception;
import utility.exception.NoName_exception;
import utility.exception.NoPwd_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.NotSamePwd_exception;
import utility.exception.WrongPwd_exception;

public class loginBLController implements loginBLService{
	
	Login login;
	Register register;
	
	public loginBLController(){
		login=new Login();
		register=new Register();
	}

	@Override
	public boolean login(String id, String password) throws WrongPwd_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		return login.login(id, password);
	}

	@Override
	public boolean register(String id, String password,String confirm) throws ExistName_exception,
	NoName_exception, NoPwd_exception ,NotSamePwd_exception{
		// TODO Auto-generated method stub
		return register.register(id, password,confirm);
	}

}
