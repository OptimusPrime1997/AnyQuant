package model;

import java.util.List;

/**
 * this is the abstract of users
 *  
 * @author run
 *
 */
public class User {

	private String name;
	private String password;
	private List<String> stocks;
	public User() {
		// TODO Auto-generated constructor stub
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


	public List<String> getStocks() {
		return stocks;
	}


	public void setStocks(List<String> stocks) {
		this.stocks = stocks;
	}


	@Override
	public boolean equals(Object o) {
		if (name.equals(((User) o).getName())) {
			return true;
		} else {
			return false;
		}
	}

}
