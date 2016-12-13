/**
 * 
 */
package utility.emums;


/**
 * @author bismuth
 *
 */
public enum IndustryType {

	FINANCE("金融", "finance", 0), ESTATE("房地产", "estate", 1), MATERIAL("建筑材料", "material", 2), ELECTINFO("电子信息",
			"electInfo", 3), AVIATION("航空航天", "aviation", 4)
	/*, ENVIRONMENT("环保工程", "environment", 5), SOFTWARE("软件服务",
					"software", 6), PETROCHEMICAL("石油行业", "petroChemical", 7)*/;

	private String name;
	private String id;
	private int no;

	private IndustryType(String name, String id, int no) {
		this.name = name;
		this.id = id;
		this.no = no;
	}

	public static IndustryType getIndustryById(String id) {
		switch (id) {
		case "finance":
			return FINANCE;
		case "estate":
			return ESTATE;
		case "material":
			return MATERIAL;
		case "electInfo":
			return ELECTINFO;
		case "aviation":
			return  AVIATION;
		/*case "software":
			return SOFTWARE;
		case "petroChemical":
			return PETROCHEMICAL;*/
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
