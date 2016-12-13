/**
 * 
 */
package bl.compareBL;

import java.io.IOException;
import java.util.ArrayList;

import po.StockPO;
/**
 * @author run
 *
 */
public class CompareWithMarket {
	Compute c;
	
	public CompareWithMarket(){
		c = new Compute();
	}
	
	public String compare(ArrayList<StockPO> array) throws IOException{
		String s = "";
//		StockPO s1 = getMaxRange(array);
//		StockPO s2 = getMinRange(array);
		double exp_range = c.getEX_range(array);
		double exp_endprice= c.getEX_endprice(array);
//		double maxrange = s1.getRange();
//		double minrange = s2.getRange();
//		
//		String maxrangedate = s1.getDate().toString();
//		String minrangedate = s2.getDate().toString();
		
		s = "平均收盘价："+format(exp_endprice)+"\n"+
			"平均涨幅："+format(exp_range)+"%";
		
		return s;
	}
	
	String format(double d){
		return String.format("%.2f", d);
	}

	StockPO getMaxRange(ArrayList<StockPO> array) throws IOException{
		StockPO po1 = array.get(0);
		StockPO po2 = array.get(1);
		double j = (po1.getEndprice()-po2.getEndprice())/po2.getEndprice()*100;
		
		for(int i=1;i<array.size()-1;++i){
			double k = (array.get(i).getEndprice()-array.get(i+1).getEndprice())/
					array.get(i+1).getEndprice()*100;
			if(k>j){
				po1 = array.get(i);
			}
		}
		return po1;
	}
	
	StockPO getMinRange(ArrayList<StockPO> array) throws IOException{
		StockPO po1 = array.get(0);
		StockPO po2 = array.get(1);
		double j = (po1.getEndprice()-po2.getEndprice())/po2.getEndprice()*100;
		
		for(int i=1;i<array.size()-1;++i){
			double k = (array.get(i).getEndprice()-array.get(i+1).getEndprice())/
					array.get(i+1).getEndprice()*100;
			if(k<j){
				po1 = array.get(i);
			}
		}
		return po1;
	}
}
