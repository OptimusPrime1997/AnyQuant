package po;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this is the abstract of users
 * 
 * @author run
 *
 */
public class UserPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private ArrayList<String> stocks;

	/**
	 * @param name
	 *            :user name
	 * @param password:
	 *            user password
	 * @param stocks:the
	 *            user preferred stock list
	 */
	public UserPO(String name, String password, ArrayList<String> stocks) {

		super();
		this.name = name;
		this.password = password;
		this.stocks = stocks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<String> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<String> ids) {
		this.stocks = ids;
	}

	@Override
	public boolean equals(Object o) {
		if (name.equals(((UserPO) o).getName())) {
			return true;
		} else {
			return false;
		}
	}

}
