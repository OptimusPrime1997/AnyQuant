/**
 * 
 */
package vo;

import java.util.ArrayList;


import po.UserPO;

/**
 * @author 1
 *
 */
public class UserVO {
	public String name;
	public String password;
	public ArrayList<StockVO> stocks;

	/**
	 * @param name
	 *            :user name
	 * @param password:
	 *            user password
	 * @param stocks:the
	 *            user preferred stock list
	 */
	public UserVO(String name, String password, ArrayList<StockVO> stocks) {

		super();
		this.name = name;
		this.password = password;
		this.stocks = stocks;
	}

	@Override
	public boolean equals(Object o) {
		if (name.equals(((UserVO) o).name)) {
			return true;
		} else {
			return false;
		}
	}
}
