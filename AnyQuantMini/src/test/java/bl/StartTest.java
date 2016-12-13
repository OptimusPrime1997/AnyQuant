/**
 * 
 */
package bl;

import bl.loginBL.loginBLController;
import blservice.loginBLService;
import utility.exception.ExistName_exception;
import utility.exception.NoName_exception;
import utility.exception.NoPwd_exception;
import utility.exception.NotFoundName_exception;
import utility.exception.NotSamePwd_exception;
import utility.exception.WrongPwd_exception;

/**
 * @author run
 *
 */
public class StartTest {
	
	loginBLService service=new loginBLController();
	
	public void test1(){
		try {
			boolean r=service.login("jiaorun", "JLBuffett");
			System.out.println(r);
		} catch (WrongPwd_exception e) {
			System.out.println("wrong pwd");
		} catch (NotFoundName_exception e) {
			System.out.println("user not exist");
		}
	}
	
	public void test2(){
		try {
			boolean r=service.register("666", null, "0224");
			System.out.println(r);
		} catch (ExistName_exception e) {
			System.out.println("user exists");
		} catch (NoName_exception e) {
			System.out.println("please input name");
		} catch (NoPwd_exception e) {
			System.out.println("please input pwd");
		} catch (NotSamePwd_exception e) {
			System.out.println("please confirm pwd");
		}
	}
	
	public void test3(){
		try {
			boolean r=service.login("j", "0224");
			System.out.println(r);
		} catch (WrongPwd_exception e) {
			System.out.println("wrong pwd");
		} catch (NotFoundName_exception e) {
			e.printStackTrace();
			System.out.println("user do not exist");
		}
	}
	
	public static void main(String[] args){
		StartTest start=new StartTest();
		//start.test1();
		start.test2();
		//start.test3();
	}

}
