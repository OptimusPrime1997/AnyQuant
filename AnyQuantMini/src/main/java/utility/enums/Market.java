/**
 * 
 */
package utility.enums;

/**
 * @author run this is market enumeration
 */
public enum Market {
	/**
	 * 国内全部交易所
	 */
	ALL("all", "国内全部交易所","benchmark/"),
	/**
	 * 上海证券交易所
	 */
	SHANGHAI("sh", "上海证券交易所","stock/"),
	/**
	 * 深圳证券交易所
	 */
	SHENZHEN("sz", "深圳证券交易所","stock/");
	private String abbr;
	private String position;
	private String value;
	private Market(String abbr, String p,String v) {
		this.abbr = abbr;
		this.position = p;
		this.value=v;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

}
