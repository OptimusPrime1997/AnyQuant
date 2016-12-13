///**
// * 
// */
//package data;
//
//import static org.junit.Assert.*;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import data.MaxMinData;
//import po.MaxMinPO;
//import utility.MyDate;
//import utility.enums.IndustryType;
//import utility.exception.ExistID_exception;
//import utility.exception.ExistName_exception;
//import utility.exception.NotExistId_exception;
//import utility.exception.NotFoundName_exception;
//import vo.ChartVO;
//
///**
// * @author 1
// *
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class MaxMinDataTest {
//
//	private MaxMinData data;
//	private ChartVO maxVO;
//	private ChartVO minVO;
//	private MaxMinPO mmPO;
//
//	public MaxMinDataTest() {
//		// TODO Auto-generated constructor stub
//		data = new MaxMinData();
//		maxVO = new ChartVO(1, 2, 3, 4, 5, "test1", "sz000619", new MyDate("2016-01-01"));
//		minVO = new ChartVO(10, 10, 10, 10, 10, "test1", "sz000619", new MyDate("2016-01-01"));
//		mmPO = new MaxMinPO(maxVO, minVO);
//	}
//
//	@Test
//	public void test1Add() {
//		boolean suc = false;
//		try {
//			suc = data.add(mmPO);
//			assertEquals(true, suc);
//			System.out.println("Add success or not:" + suc);
//		} catch (ExistID_exception | NotFoundName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void test2update() {
//		MaxMinPO p = new MaxMinPO(minVO, maxVO);
//		boolean suc = false;
//		try {
//			suc = data.update("sz000619", p);
//			assertEquals(true, suc);
//		} catch (ExistName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void test3getMaxMinPO() {
//		try {
//			System.out.println("just a test");
//			MaxMinPO temp = data.getChart(IndustryType.ESTATE.getID());
//			assertEquals(
//					"Max:id:estatename:房地产turnover:18.75volume:1.969241E8adjprice:22.81pe:98.99pb:6.96Min:id:sz000014name:沙河股份turnover:0.38volume:987700.0adjprice:4.72pe:9.41pb:1.37",
//					temp.toString());
//			System.out.println(temp.toString());
//		} catch (NotFoundName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void test4remove() {
//		boolean isSuccess = false;
//		try {
//			isSuccess = data.remove("sz000619");
//			assertEquals(true, isSuccess);
//		} catch (NotExistId_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotFoundName_exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
