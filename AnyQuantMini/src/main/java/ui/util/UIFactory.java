/**
 * 
 */
package ui.util;

import ui.large.Market;
import ui.large.Select;
import ui.large.industry.Industry;
import ui.large.industry.IndustryCompare;
import ui.large.industry.IndustryDetail;
import ui.large.single.SingleCompare;
import ui.large.single.SingleDetail;
import ui.large.single.Single;

/**
 * @author bismuth
 *
 */
public class UIFactory {
	private UIFactory() {
	}

	public static UIFactory getInstance() {
		return UIFactoryHolder.uiFactory;
	}

	private static class UIFactoryHolder {
		private static UIFactory uiFactory = new UIFactory();
	}

	private static Market market = null;
	private static Select select = null;
	private static Single single = null;
	private static SingleDetail singleDetail = null;
	private static SingleCompare singleCompare = null;
	private static Industry industry = null;
	private static IndustryDetail industryDetail = null;
	private static IndustryCompare industryCompare = null;

	public Market getMarket() {
		if (market == null) {
			return new Market();
		} else {
			return market;
		}
	}

	public Select getSelect() {
		if (select == null) {
			return new Select();
		} else {
			return select;
		}
	}
	
	public Single getSingle() {
		if (single == null) {
			return new Single();
		} else {
			return single;
		}
	}
	
	public SingleDetail getSingleDetail() {
		if (singleDetail == null) {
			return new SingleDetail();
		} else {
			return singleDetail;
		}
	}

	public SingleCompare getSingleCompare() {
		if (singleCompare == null) {
			return new SingleCompare();
		} else {
			return singleCompare;
		}
	}
	
	public Industry getIndustry() {
		if (select == null) {
			return new Industry();
		} else {
			return industry;
		}
	}
	
	public IndustryDetail getIndustryDetail() {
		if (singleDetail == null) {
			return new IndustryDetail();
		} else {
			return industryDetail;
		}
	}

	public IndustryCompare getIndustryCompare() {
		if (singleCompare == null) {
			return new IndustryCompare();
		} else {
			return industryCompare;
		}
	}

}
