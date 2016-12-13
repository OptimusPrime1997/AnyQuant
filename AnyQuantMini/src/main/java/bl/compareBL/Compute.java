/**
 * 
 */
package bl.compareBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import po.StockPO;

/**
 * @author run
 *
 */
public class Compute {
	public double computeVar_endprice(ArrayList<StockPO> a){
		double result=0;
		double ex=getEX_endprice(a);
		Iterator<StockPO> it = a.iterator();
		StockPO s;
		while(it.hasNext()){
			s = it.next();
			double d = s.getEndprice()-ex;
			result=result+d*d;
		}
		return result/a.size();
	}
	
	public double getEX_endprice(ArrayList<StockPO> array){
		double result = 0 ;
		int i = 0;
		int len = array.size();
		while(i<len){
			result = result+array.get(i).getEndprice();
			++i;
		}
		return result/len;
	}
	
	public double computeVar_turnover(ArrayList<StockPO> a){
		double result=0;
		double ex=getEX_turnover(a);
		Iterator<StockPO> it = a.iterator();
		StockPO s;
		while(it.hasNext()){
			s = it.next();
			double d = s.getTurnover()-ex;
			result=result+d*d;
		}
		return result/a.size();
	}
	
	public double getEX_turnover(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getTurnover();
		}
		return temp/a.size();
	}

	public double computeVar_range(ArrayList<StockPO> a) throws IOException{	
		double result = 0;
		int i=0,j=1;
		int len = a.size();
		double ex = getEX_range(a);
		double temp = 0;
		while(i<=len-1){
			if(i==len-1){
				a.get(i).setRange();
				temp = a.get(i).getRange();
				result+=(temp-ex)*(temp-ex);
				break;
			}else{
				double r = (a.get(i).getEndprice()-a.get(j).getEndprice())/a.get(j).getEndprice()*100;
				result+=(r-ex)*(r-ex);
				++i;
				++j;
			}
		}
		return result/len;
	}
	
	public double getEX_range(ArrayList<StockPO> a) throws IOException{
//		double result = 0;
//		int i =0,j=0;
//		int len = a.size();
//		StockPO s,t;
//		while(j<=len-1){
//			if(i==0){
//				result+=a.get(i).getRange();
//				++j;
//			}
//			else{
//				s=a.get(i);
//				t=a.get(j);
//				result+=(s.getEndprice()-t.getEndprice())/t.getEndprice()*100;
//				++i;
//				++j;
//			}
		double result = 0;
		int i=0,j=1;
		StockPO s,t;
		int len = a.size();
		while(i<=len-1){
			if(i==len-1){
				a.get(i).setRange();
				result+=a.get(i).getRange();
				break;
			}
			else{
				s=a.get(i);
				t=a.get(j);
				result+=(s.getEndprice()-t.getEndprice())/t.getEndprice()*100;
				++i;
				++j;
			}
		}
		return result/a.size();
	}
	public double computeVar_pe(ArrayList<StockPO> a){
		double result=0;
		double ex=getEX_pe(a);
		Iterator<StockPO> it = a.iterator();
		StockPO s;
		while(it.hasNext()){
			s = it.next();
			result=result+(s.getPe()-ex)*(s.getPe()-ex);
		}
		return result/a.size();
	}
	
	public double getEX_pe(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getPe();
		}
		return temp/a.size();
	}
	public double computeVar_pb(ArrayList<StockPO> a){
		double result=0;
		double ex=getEX_pb(a);
		Iterator<StockPO> it = a.iterator();
		StockPO s;
		while(it.hasNext()){
			s = it.next();
			double d = s.getPb()-ex;
			result=result+d*d;
		}
		return result/a.size();
	}
	
	public double getEX_pb(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getPb();
		}
		return temp/a.size();
	}
	
	public double getEX_Volume(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getVolume();
		}
		return temp/a.size();
	}
	
	public double getEX_Adjprice(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getAdjprice();
		}
		return temp/a.size();
		
	}
	
	public double getEX_Startprice(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getStartprice();
		}
		return temp/a.size();
	}
	public double getEX_Endprice(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getEndprice();
		}
		return temp/a.size();
	}
	public double getEX_Maxprice(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getMaxprice();
		}
		return temp/a.size();
	}
	public double getEX_Minprice(ArrayList<StockPO> a){
		double temp=0;
		Iterator<StockPO> it = a.iterator();
		while(it.hasNext()){
			temp=temp+it.next().getMinprice();
		}
		return temp/a.size();
	}
	
}
