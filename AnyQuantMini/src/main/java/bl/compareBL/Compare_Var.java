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
public class Compare_Var implements Compare {
	Compute c;
	
	public Compare_Var(){
		c = new Compute();
	}

	/* (non-Javadoc)
	 * @see bl.compareBL.Compare#CompareData(java.util.ArrayList)
	 */
	@Override
	public CompareInfo Compare_Max(ArrayList<ArrayList<StockPO>> array) throws IOException {
		double turnover=0,range=0,pb=0,pe=0;
		String str_turnover = array.get(0).get(0).getId();
		String str_range = array.get(0).get(0).getId();
		String str_pe = array.get(0).get(0).getId();
		String str_pb = array.get(0).get(0).getId();

		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_turnover(array.get(i));
			if(j>turnover){
				turnover=j;
				str_turnover = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_range(array.get(i));
			if(j>range){
				range=j;
				str_range = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_pe(array.get(i));
			if(j>pe){
				pe=j;
				str_pe = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_pb(array.get(i));
			if(j>pb){
				pb=j;
				str_pb = array.get(i).get(0).getId();
			}
		}
		return new CompareInfo(turnover,str_turnover,range,str_range,pe,str_pe,pb,str_pb);
	}
	
	@Override
	public CompareInfo Compare_Min(ArrayList<ArrayList<StockPO>> array) throws IOException{
		double turnover=0,range=0,pb=0,pe=0;
		turnover = computeVar_turnover(array.get(0));
		range = computeVar_range(array.get(0));
		pe = computeVar_pe(array.get(0));
		pb = computeVar_pb(array.get(0));
		String str_turnover = array.get(0).get(0).getId();
		String str_range = array.get(0).get(0).getId();
		String str_pe = array.get(0).get(0).getId();
		String str_pb = array.get(0).get(0).getId();
		
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_turnover(array.get(i));
			if(j<turnover){
				turnover=j;
				str_turnover = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_range(array.get(i));
			if(j<range){
				range=j;
				str_range = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_pe(array.get(i));
			if(j<pe){
				pe=j;
				str_pe = array.get(i).get(0).getId();
			}
		}
		for(int i=0;i<array.size()-1;++i){
			double j = computeVar_pb(array.get(i));
			if(j<pb){
				pb=j;
				str_pb = array.get(i).get(0).getId();
			}
		}
				
		return new CompareInfo(turnover,str_turnover,range,str_range,pe,str_pe,pb,str_pb);
	}
	
	double computeVar_turnover(ArrayList<StockPO> a){
		return c.computeVar_turnover(a);
	}
	
	double getEX_turnover(ArrayList<StockPO> a){
		return c.getEX_turnover(a);
	}

	double computeVar_range(ArrayList<StockPO> a) throws IOException{
		return c.computeVar_range(a);
	}
	
	double getEX_range(ArrayList<StockPO> a) throws IOException{
		return c.getEX_range(a);
	}
	double computeVar_pe(ArrayList<StockPO> a){
		return c.computeVar_pe(a);
	}
	
	double getEX_pe(ArrayList<StockPO> a){
		return c.getEX_pe(a);
	}
	double computeVar_pb(ArrayList<StockPO> a){
		return c.computeVar_pb(a);
	}
	
	double getEX_pb(ArrayList<StockPO> a){
		return c.getEX_pb(a);
	}
}
