/**
 * 
 */
package com.b510.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;






import com.epmis.car.vo.Ecar;
import com.epmis.car.vo.Tcar;
import com.epmis.jt.orderinfo.vo.Orderinfo;





/**
 * @author Hongten
 * @created 2014-5-18
 */
public class ReadExcel {
	
	/**
	 * 订单导入修改数据
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<Orderinfo> readXlsorder(String path) throws IOException {
		
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		Orderinfo ecar = null;
		List<Orderinfo> list = new ArrayList<Orderinfo>();
		System.out.println(hssfWorkbook.getNumberOfSheets());
		// 循环工作表Sheett
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			//hssfSheet = hssfWorkbook.getSheetAt(1);
			// 循环行Row
			if(numSheet==0){
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					
					System.out.println("hssfSheet.getLastRowNum():"+hssfSheet.getLastRowNum());
					
					
					if (hssfRow.getCell(0) != null ) {
						Orderinfo order =new Orderinfo();
//						HSSFCell order_id = hssfRow.getCell(0);
						HSSFCell bank_id = hssfRow.getCell(1);
//						HSSFCell client_id = hssfRow.getCell(2);
//						HSSFCell lend_id = hssfRow.getCell(3);
//						HSSFCell amount = hssfRow.getCell(4);
//						HSSFCell bank_name = hssfRow.getCell(5);
//						HSSFCell bank_id = hssfRow.getCell(6);
						HSSFCell transfer_status = hssfRow.getCell(7);
//						HSSFCell approve_status = hssfRow.getCell(8);
//						HSSFCell transfer_status = hssfRow.getCell(9);
//						HSSFCell down_ms = hssfRow.getCell(14);
//						HSSFCell month_ms = hssfRow.getCell(15);
//						System.out.println(id+","+naked_price+","+official_price+",jiangjia:"+jiangjia);
						
						
						if(null!=bank_id&&!"".equals(bank_id.toString())){
							
//							order.setOrder_id(Integer.parseInt(getValue(order_id).substring(0,getValue(order_id).indexOf("."))));
//							order.setOrder_id(getValue(order_id));							
//							order.setOrder_number( getValue(order_number));
//							order.setClient_id(getValue(client_id));
//							order.setLend_id(getValue(lend_id));							
//							order.setAmount( getValue(amount));
//							
//							order.setBank_name(getValue(bank_name));
							order.setBank_id(getValue(bank_id));
//							order.setCreatetime(getValue(createtime));
//							order.setApprove_status(getValue(approve_status));
							order.setTransfer_status(getValue(transfer_status));
//							car1.setDown_ms(getValue(down_ms));
//							car1.setMonth_ms(getValue(month_ms));
//							System.out.println("kakaka:"+car1.getId());
//							System.out.println("Jiangjia:"+car1.getJiangjia());
							
							list.add(order);
						}
					}else{
						break;
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 只修改车型数据（各种价格）
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<Ecar> readXlscar(String path) throws IOException {
		
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		Ecar ecar = null;
		List<Ecar> list = new ArrayList<Ecar>();		
		// 循环工作表Sheett
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			//hssfSheet = hssfWorkbook.getSheetAt(1);
			// 循环行Row
			if(numSheet==0){
			for (int rowNum = 3; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					
					System.out.println("hssfSheet.getLastRowNum():"+hssfSheet.getLastRowNum());
					
					
					if (hssfRow.getCell(4) != null ) {
						Ecar car1 =new Ecar();
						HSSFCell id = hssfRow.getCell(4);
						HSSFCell naked_price = hssfRow.getCell(5);
						HSSFCell official_price = hssfRow.getCell(6);
						HSSFCell down_payment = hssfRow.getCell(7);
						HSSFCell monthly_payments = hssfRow.getCell(8);
						HSSFCell service_charge = hssfRow.getCell(9);
						HSSFCell purchase_tax = hssfRow.getCell(10);
						HSSFCell insurance = hssfRow.getCell(11);
						HSSFCell jiangjia = hssfRow.getCell(12);
						HSSFCell subsidy = hssfRow.getCell(13);
						HSSFCell down_ms = hssfRow.getCell(14);
						HSSFCell month_ms = hssfRow.getCell(15);
						System.out.println(id+","+naked_price+","+official_price+",jiangjia:"+jiangjia);
						
						
						if(null!=id&&!"".equals(id.toString())){
							car1.setId(Integer.parseInt(getValue(id).substring(0,getValue(id).indexOf("."))));							
							car1.setNaked_price( getValue(naked_price));
							car1.setOfficial_price(getValue(official_price));
							car1.setDown_payment(getValue(down_payment));							
							car1.setMonthly_payments( getValue(monthly_payments));
							car1.setService_charge(getValue(service_charge));
							car1.setPurchase_tax(getValue(purchase_tax));
							car1.setInsurance(getValue(insurance));
							car1.setJiangjia(getValue(jiangjia));
							car1.setSubsidy(getValue(subsidy));
							car1.setDown_ms(getValue(down_ms));
							car1.setMonth_ms(getValue(month_ms));
							System.out.println("kakaka:"+car1.getId());
							System.out.println("Jiangjia:"+car1.getJiangjia());
							
							list.add(car1);
						}
					}else{
						break;
					}
				}
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if(hssfCell==null){
			return "-";
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}
