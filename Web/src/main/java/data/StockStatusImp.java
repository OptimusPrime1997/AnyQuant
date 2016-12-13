package data;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import dataservice.StockStatusDataService;

public class StockStatusImp implements StockStatusDataService {

	@Override
	public String getStockStatus(String stockId) {
		System.setProperty("java.security.policy", StockStatusImp.class.getClassLoader().getResource("mypolicy.policy").getPath());
		//System.out.println("------test:"+StockStatusImp.class.getClassLoader().getResource("mypolicy.policy").getPath());
//		System.setProperty("java.security.policy",
//				StockStatusImp.class.getClassLoader().getResource("mypolicy.policy").getPath());
	//	System.setSecurityManager(new SecurityManager());
		//System.setProperty("java.security.policy","file:/G:/workspace-java/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/webapps/AnyQuantWeb/WEB-INF/classes/mypolicy.policy");
		
		if (stockId.length() == 8 && stockId.charAt(0) == 's') {
			Document doc;
			Elements textContents = null;
			org.jsoup.nodes.Element element = null;
			String result = null;
			try {
				doc = Jsoup.connect("http://cj.gw.com.cn/news/stock/" + stockId + "/cpbd.shtml").get();
				textContents = doc.select("div.item_littlebox_c.mtup");
				element = textContents.select("div.p10").first();
				result = Jsoup.clean(element.toString(), Whitelist.none());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] temp = result.split(" ");
			// System.out.println(temp[0]);
			return temp[0];
		} else {
			return "";
		}
	}

	/*
	 * (non-Javadoc) get the stock prediction info 综合分值 报告数值 阻力位 支撑位
	 * 
	 * @see
	 * dataservice.StockStatusDataService#getStockPrediction(java.lang.String)
	 */
	@Override
	public String getStockPrediction(String stockId) {
		System.setProperty("java.security.policy", StockStatusImp.class.getClassLoader().getResource("mypolicy.policy").getPath());
		if (stockId.length() == 8 && stockId.charAt(0) == 's') {
			Document doc;
			Elements textContents = null;
			String result = null;
			try {
				doc = Jsoup.connect("http://finance.ifeng.com/app/hq/stock/" + stockId + "/").get();
				textContents = doc.select("div.lable030_b.clearfix.bg_c");
				// System.out.println(textContents.toString());
				for (int k = 0; k < textContents.size(); k++) {
					org.jsoup.nodes.Element e = textContents.get(k);
					Elements tempEle = e.select("div.lable030_b_R");
					Elements tempEle2 = tempEle.select("table.gradTab");
					Elements tempEle3 = tempEle.select("p.line_24");
					tempEle2.addAll(tempEle3);
					for (int i = 0; i < tempEle2.size(); i++) {
						org.jsoup.nodes.Element el = tempEle2.get(i);
						String temp = Jsoup.clean(el.toString(), Whitelist.none());
						result += temp + "    ";
					}
				}
				return result.replaceAll("null", " ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}
}
