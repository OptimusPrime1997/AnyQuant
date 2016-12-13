/**
 * 
 */
package bl.compareBL;

/**比较不同组数据的方差
 * @author run
 *
 */
public class CompareInfo {
	double turnover;
	double range;
	double pe;
	double pb;
	
	String turnoverID;
	String rangeID;
	String peID;
	String pbID;
	
	public CompareInfo(double turnover,String turnoverid,double range,String rangeid,
			double pe,String peid,double pb,String pbid){
		this.turnover=turnover;
		this.range=range;
		this.pe=pe;
		this.pb=pb;
		this.turnoverID=turnoverid;
		this.rangeID=rangeid;
		this.peID=peid;
		this.pbID=pbid;
	}
	
	public double getTurnover(){
		return this.turnover;
	}
	
	public double getRange(){
		return this.range;
	}
	
	public double getPe(){
		return this.pe;
	}
	
	public double getPb(){
		return this.pb;
	}
	
	public String getturnoverID(){
		return this.turnoverID;
	}
	
	public String getrangeID(){
		return this.rangeID;
	}
	
	public String getpeID(){
		return this.peID;
	}
	
	public String getpbID(){
		return this.pbID;
	}
	
	public String toString(String pre){
		String s ="";
		s = pre+"转手率："+turnover+"%("+turnoverID+")"+
			pre+"涨跌幅："+range+"%("+rangeID+")"+
			pre+"pe："+pe+"("+peID+")"+
			pre+"pb："+pb+"("+pbID+")";
		return s;
	}
}
