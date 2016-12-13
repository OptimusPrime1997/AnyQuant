/**
 * 
 */
package bl.detailBL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import bl.compareBL.Compute;
import java.math.BigDecimal;
import po.StockPO;
/**
 * @author run
 *
 */
public class DesHelper implements getDes {
	
	Compute c;
	public DesHelper(){
		c = new Compute();
	}
	/* (non-Javadoc)
	 * @see bl.detailBL.getDes#getDescription(java.util.ArrayList)
	 */
	@Override
	public String getDescription(ArrayList<StockPO> array) throws IOException {
//		BigDecimal bigrange = new BigDecimal(this.getMaxRange(array).getRange());
//		String maxrange = bigrange.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
//		BigDecimal smallrange = new BigDecimal(this.getMinRange(array).getRange());
//		String minrange = smallrange.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
		String var = String.format("%.3f", c.computeVar_endprice(array));
//		StockPO max = getMAX(array);
//		StockPO min = getMIN(array);
//		StockPO maxstart = getMaxStart(array);
//		StockPO minstart = getMinStart(array);
//		StockPO maxend = getMaxEnd(array);
//		StockPO minend = getMinEnd(array);
//		StockPO maxv = getMaxVolume(array);
//		StockPO minv = getMinVolume(array);
		
//		String maxvolume="";
//		String minvolume="";
		
//		if(maxv.getVolume()>10000){
//			maxvolume = Double.toString(maxv.getVolume()*1.0/10000)+"万";
//		}else{
//			maxvolume = Double.toString(maxv.getVolume());
//		}
//		if(minv.getVolume()>10000){
//			minvolume = Double.toString(minv.getVolume()*1.0/10000)+"万";
//		}else{
//			minvolume = Double.toString(minv.getVolume());
//		}
		
		String temp = 
//					"收盘价最大值： "+maxend.getEndprice()+"\n"+"("+maxend.getDate().toString()+")"+"\n"+
//					"收盘价最小值： "+minend.getEndprice()+"\n"+"("+minend.getDate().toString()+")"+"\n"+
//					"交易量最大值： "+maxvolume+"\n"+"("+maxv.getDate().toString()+")"+"\n"+
//					"交易量最小值： "+minvolume+"\n"+"("+minv.getDate().toString()+")"+"\n"+
//					"涨（跌）幅最大值： "+maxrange+"%"+"\n"+"("+getMaxRange(array).getDate().toString()+")"+"\n"+
//					"涨（跌）幅最小值： "+minrange+"%"+"\n"+"("+getMinRange(array).getDate().toString()+")"+"\n"+
					"收盘价方差："+var;
		return temp;
	}
	
	StockPO getMAX(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getMaxprice()>po.getMaxprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMIN(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getMinprice()<po.getMinprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxStart(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getStartprice()>po.getStartprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinStart(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getStartprice()<po.getStartprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxEnd(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getEndprice()>po.getEndprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinEnd(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getEndprice()<po.getEndprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxVolume(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getVolume()>po.getVolume())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinVolume(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getVolume()<po.getVolume())
				po=temp;
		}
		return po;
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
	
	StockPO getMaxTurnover(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getTurnover()>po.getTurnover())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinTurnover(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getTurnover()<po.getTurnover())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxPe(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getPe()>po.getPe())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinPe(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getPe()<po.getPe())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxPb(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getPb()>po.getPb())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinPb(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getPb()<po.getPb())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxAdjprice(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getAdjprice()>po.getAdjprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinAdjprice(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getAdjprice()<po.getAdjprice())
				po=temp;
		}
		return po;
	}
	
	StockPO getMaxMoney(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getMoney()>po.getMoney())
				po=temp;
		}
		return po;
	}
	
	StockPO getMinMoney(ArrayList<StockPO> array){
		Iterator<StockPO> it = array.iterator();
		StockPO po = array.get(0);
		StockPO temp;
		while(it.hasNext()){
			temp = it.next();
			if(temp.getMoney()<po.getMoney())
				po=temp;
		}
		return po;
	}
	
	double getVar(ArrayList<StockPO> array){
		return c.computeVar_endprice(array);
	}
}
