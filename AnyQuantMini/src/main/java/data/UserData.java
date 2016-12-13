/**
 * 
 */
package data;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import dataservice.UserDataService;
import po.UserPO;
import utility.Constants;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

public class UserData implements UserDataService {
	private final String path = Constants.USERPATH;
	/**
	 * @author 1 to handle the user data
	 */
	private IOUtility d;
	private java.sql.Connection conn;

	public UserData() {
		// TODO Auto-generated constructor stub
		this.d = new IOUtility();
	}

	@Override
	public UserPO getUser(String name) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		ArrayList<UserPO> userPOs = getAllUser();
		UserPO user = null;
		for (Iterator<UserPO> t = userPOs.iterator(); t.hasNext();) {
			UserPO userPO = t.next();
			// System.out.println(userPO.getName());
			if (userPO.getName().equals(name)) {
				user = userPO;
				break;
			}
		}
		if (user == null) {
			throw new NotFoundName_exception();
		} else {
			return user;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#regist(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean regist(String name, String password) throws ExistName_exception {
		// TODO Auto-generated method stub
		UserPO userPO = new UserPO(name, password, new ArrayList<String>());
		try {
			d.save(userPO, path);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#add(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean add(String name, String id) throws ExistID_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		boolean findPO = false;
		UserPO user = getUser(name);
		ArrayList<String> ids = user.getStocks();
		if (ids.contains(id)) {
			throw new ExistID_exception();
		} else {
			ids.add(id);
			user.setStocks(ids);
			ArrayList<UserPO> userPOs = getAllUser();
			ArrayList<Object> objects = new ArrayList<Object>();
			for (Iterator<UserPO> t = userPOs.iterator(); t.hasNext();) {
				UserPO userPO = t.next();
				if (userPO.getName().equals(name)) {
					userPOs.remove(userPO);
					userPOs.add(user);
					findPO = true;
					break;
				}
			}
			if (findPO == true) {
				for (Object o : userPOs) {
					objects.add(o);
				}
				try {
					d.saveAll(objects, path);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					System.out.println("：存储信息出错save information error!");
					e.printStackTrace();
					return false;
				}
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean remove(String name, String id) throws NotExistId_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		boolean findPO = false;
		UserPO user = getUser(name);
		ArrayList<String> ids = user.getStocks();
		if (!ids.contains(id)) {
			throw new NotExistId_exception();
		} else {
			ids.remove(id);
			user.setStocks(ids);
			ArrayList<UserPO> userPOs = getAllUser();
			ArrayList<Object> objects = new ArrayList<Object>();
			for (Iterator<UserPO> t = userPOs.iterator(); t.hasNext();) {
				UserPO userPO = t.next();
				if (userPO.getName().equals(name)) {
					userPOs.remove(userPO);
					userPOs.add(user);
					findPO = true;
					break;
				}
			}
			if (findPO == true) {
				for (Object o : userPOs) {
					objects.add(o);
				}
				try {
					d.saveAll(objects, path);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				return true;
			} else {
				return false;
			}
		}
	}

	public ArrayList<UserPO> getAllUser() {
		print();
		ArrayList<Object> objects = new ArrayList<Object>();
		try {
			objects = d.getAll(path);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<UserPO> userPOs = new ArrayList<UserPO>();
		for (Object o : objects) {
			userPOs.add((UserPO) o);
		}
		// if (userPOs.size() > 0) {
		// Constants.display(this.getClass().getName() +
		// userPOs.get(0).toString());
		// }
		return userPOs;
	}

	private void print() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing "
				+ Thread.currentThread().getStackTrace()[2].getMethodName());
	}
}
