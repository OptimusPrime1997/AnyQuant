/**
 * 
 */
package utility.enums;

/**
 * @author 1
 *
 */
public enum Field {
	/**
	 * open: 开盘价
	 */
	OPEN("open", "开盘价"),
	/**
	 * high: 最高价
	 */
	HIGH("high", "最高价"),
	/**
	 * low: 最低价
	 */
	LOW("low", "最低价"),
	/**
	 * close: 收盘价
	 */
	CLOSE("close", "收盘价"),
	/**
	 * adj_price: 后复权价
	 */
	ADJPRICE("adj_price", "后复权价"),
	/**
	 * volume: 成交量
	 */
	VOLUME("volume", "成交量"),
	/**
	 * turnover: 换手率
	 */
	TURNOVER("turnover", "换手率"),
	/**
	 * pe_ttm: 市盈率
	 */
	PE("pe_ttm", "市盈率"),
	/**
	 * pb: 市净率
	 */
	PB("pb", "市净率"),
	/**
	 * all fields
	 */
	ALL("open+high+low+close+adj_price+volume+turnover+pe_ttm+pb", "全部属性"),
	/**
	 * 大盘显示属性：open+high+low+close+volume+turnover
	 */
	SELECT("open+high+low+close+volume+turnover", "大盘显示信息");
	private String field;
	private String name;

	private Field(String field, String n) {
		this.field = field;
		this.name = n;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
