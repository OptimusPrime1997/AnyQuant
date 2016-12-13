/**
 * 
 */
package utility.enums;

/**
 * @author bismuth
 *
 */
public enum IndustryType {

	FINANCE("金融", "finance", 0), ESTATE("房地产", "estate", 1), MATERIAL("建筑材料", "material", 2);

	private String name;
	private String id;
	private int no;

	private IndustryType(String name, String id, int no) {
		this.name = name;
		this.id = id;
		this.no = no;
	}
	
	public static IndustryType getIndustryById(String id){
		switch (id) {
		case "finance":
			return FINANCE;
		case "estate":
			return ESTATE;
		case "material":
			return MATERIAL;
		default:
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public String getID() {
		return id;
	}

	public int getNo() {
		return no;
	}
}
