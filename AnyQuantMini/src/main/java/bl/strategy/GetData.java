package bl.strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

import data.StockData;
import po.StockPO;
import utility.MyDate;
import utility.Range_Date;

public class GetData {
	public static void main(String[] args) throws IOException{
		ArrayList<StockPO> a = new ArrayList<StockPO>();
		MyDate date = new MyDate(Calendar.getInstance());
		
		MyDate d = date.beforeDay();
		for(int i=0;i<2500;++i){
			d = d.beforeDay();
			while(!d.isWorkDay()){
				d=d.beforeDay();
			}
		}
		
		StockData s = new StockData();
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ids.txt")));
		String id ="";
		do{
			//id = br.readLine();
			id = "hs300";
			if(id == null){
				break;
			}
			System.out.println(id+"  reading.................................");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(id+".txt")));
			try {
				a = s.getDataByID(id, new Range_Date(d,date));
				System.out.println(a.size());
				int j = 1;
				for(int i=a.size()-1;i>=0;--i){
//					System.out.println(j+"  "+a.get(i).getEndprice());
					bw.write(j+"  "+a.get(i).getEndprice());
					bw.newLine();
					++j;
				}
				bw.flush();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bw.close();
		}while(id == null);
		
		//br.close();
		
	}

}
