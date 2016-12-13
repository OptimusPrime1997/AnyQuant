/**
 * 
 */
package utility.enums;

/**
 * @author 1
 *
 */
public enum Attribute {
	TURNOVER("换手率"), VOLUME("成交量"), ADJPRICE("后复收盘价"), PE("市盈率"), PB("市净率");
	private String value;

	private Attribute(String Value) {
		this.value = Value;
	}

	public String getValue() {
		return value;
	}
}
