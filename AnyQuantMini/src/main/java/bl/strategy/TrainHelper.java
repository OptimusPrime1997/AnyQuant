package bl.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import data.StockData;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;

public class TrainHelper {
	int N =0;
	public static double[] getRange(MyDate d,int n,int inputsize) throws IOException{
		double[] re = new double[n+inputsize+1];
		StockData data = new StockData();
		MyDate before = null;
		MyDate it = d.clone();
		MyDate temp = null;
		for(int i=0;i<n+inputsize+1;++i){
			temp = it.beforeDay();
			while(!temp.isWorkDay()){
				temp = temp.beforeDay();
			}
			it = temp;
		}
		//System.out.println(it.toString());
		ArrayList<StockPO> array = data.getDataByID("hs300", new Range_Date(it,d));
		//System.out.println(array.size()+" "+inputsize);
		if(array.size()==inputsize+1+n){
			for(int i = 0;i<n+inputsize;++i){
				re[i]=array.get(i).getEndprice();
			}
		}
		return re;
	}
	
	public static void main(String[] args){
		MyDate d = new MyDate(Calendar.getInstance());
		try {
			double[] s = getRange(d, 10,6);
			for(int i = 0;i<s.length;++i){
				System.out.println(s[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
