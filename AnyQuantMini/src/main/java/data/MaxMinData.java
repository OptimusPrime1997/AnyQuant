/**
 * 
 */
package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import dataservice.MaxMinDataService;
import po.MaxMinPO;
import utility.Constants;
import utility.exception.ExistID_exception;
import utility.exception.ExistName_exception;
import utility.exception.NotExistId_exception;
import utility.exception.NotFoundName_exception;

/**
 * @author 1
 *
 */
public class MaxMinData implements MaxMinDataService {
	private final String path = Constants.MaxMinPath;
	/**
	 * @author 1 to handle the user data
	 */
	private IOUtility d;

	public MaxMinData() {
		// TODO Auto-generated constructor stub
		d = new IOUtility();
	}

	@Override
	public MaxMinPO getChart(String id) throws NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		ArrayList<MaxMinPO> maxMinPOs = getAllMaxMinPO();
		MaxMinPO user = null;
		for (Iterator<MaxMinPO> t = maxMinPOs.iterator(); t.hasNext();) {
			MaxMinPO maxMinPO = t.next();
//			System.out.println(maxMinPO.toString());
			if (maxMinPO.getMaxPO().getID().equals(id)) {
				user = maxMinPO;
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
	public boolean add(MaxMinPO maxMinPO) throws ExistID_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		try {
			d.save(maxMinPO, path);
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
	public boolean update(String stockId, MaxMinPO maxMinPO) throws ExistName_exception {
		// TODO Auto-generated method stub
		print();
		boolean findPO = false;
		ArrayList<MaxMinPO> MaxMinPOs = getAllMaxMinPO();

		ArrayList<Object> objects = new ArrayList<Object>();
		for (Iterator<MaxMinPO> t = MaxMinPOs.iterator(); t.hasNext();) {
			MaxMinPO MaxMinPO = t.next();
			if (MaxMinPO.getMaxPO().getID().equals(stockId)) {
				MaxMinPOs.remove(MaxMinPO);
				MaxMinPOs.add(maxMinPO);
				findPO = true;
				break;
			}
		}
		if (findPO == true) {
			for (Object o : MaxMinPOs) {
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

	@Override
	public boolean addOrUpdate(MaxMinPO mmPO) throws ExistName_exception, ExistID_exception, NotFoundName_exception {
		boolean isUpdate = update(mmPO.getMaxPO().getID(), mmPO);
		boolean suc = false;
		if (isUpdate == false) {
			suc = add(mmPO);
		}
		if (isUpdate) {
			return isUpdate;
		} else {
			return suc;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataservice.UserDataService#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean remove(String id) throws NotExistId_exception, NotFoundName_exception {
		// TODO Auto-generated method stub
		print();
		boolean findPO = false;
		ArrayList<MaxMinPO> MaxMinPOs = getAllMaxMinPO();
		ArrayList<Object> objects = new ArrayList<Object>();
		for (Iterator<MaxMinPO> t = MaxMinPOs.iterator(); t.hasNext();) {
			MaxMinPO MaxMinPO = t.next();
			if (MaxMinPO.getMaxPO().getID().equals(id)) {
				MaxMinPOs.remove(MaxMinPO);
				findPO = true;
				break;
			}
		}
		if (findPO == true) {
			for (Object o : MaxMinPOs) {
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

	public ArrayList<MaxMinPO> getAllMaxMinPO() {
		print();
		ArrayList<Object> objects = new ArrayList<Object>();
		try {
			objects = d.getAll(path);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<MaxMinPO> pos = new ArrayList<MaxMinPO>();
		for (Object o : objects) {
			pos.add((MaxMinPO) o);
		}
		// if (ChartVOs.size() > 0) {
		// Constants.display(this.getClass().getName() +
		// ChartVOs.get(0).toString());
		// }
		return pos;
	}

	private void print() {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing "
//				+ Thread.currentThread().getStackTrace()[2].getMethodName());
	}
}
