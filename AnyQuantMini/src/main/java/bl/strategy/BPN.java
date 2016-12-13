package bl.strategy;

import java.io.IOException;
import java.util.Calendar;

import utility.MyDate;

public class BPN {
	public static double[] getInput(MyDate d) throws IOException{
		double[] a = TrainHelper.getRange(d, 1, 7);
		System.out.println(a.length);
		
		double[] b =new double[7];
		for(int i=0;i<7;++i){
			b[i]=(a[i]<a[i+1])?1:0;
		}
		return b;
	}
	
	public static boolean valid(double a){
		return (Math.abs(a-0.5)>0.4)? true:false;
	}
	
	public static MyDate getBefore(MyDate d){
		MyDate x = d.clone();
		x=x.beforeDay();
		while(!x.isWorkDay()){
			x=x.beforeDay();
		}
		return x;
	}
	
	public static boolean isempty(double[] a){
		boolean empty = false;
		for(int i=0;i<a.length;++i){
			if(a[i]==0){
				empty=true;
				break;
			}
		}
		return empty;
	}
	
	public static void main(String[] args){
		NeuralNetwork n = new NeuralNetwork(6, 10, 1);
		int a = 0;
		int N = 0;
		double[] result;
		double[] temp;
		MyDate d = new MyDate(Calendar.getInstance());
		try{
			for(int j=0;j<1000;j++){
				temp = getInput(d);
				if(temp.length==7){
					n.setInput(temp);
					n.activate();
					result = n.getOutput();
					if(valid(result[0])){
						++a;
					}
					++N;
				}
				d=getBefore(d);
			}
		}
		catch(IOException e){
			System.out.println(a/N);
		}
		System.out.println("N "+N+" a: "+ a);
	}

}
