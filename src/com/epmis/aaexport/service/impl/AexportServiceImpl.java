package com.epmis.aaexport.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;











import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.aaexport.service.AexportService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("aexpService")
public class AexportServiceImpl implements AexportService{
	@Autowired
	  @Qualifier("baseJdbcDao")
	  private BaseJdbcDao baseJdbcDao;
	
	public void cc(String uuid,String lend_id){
		//对一定时段的订单进行绑定操作
				String sql_bd="update order_info set bd='"+uuid+"' where lend_id='"+lend_id+"' and approve_status='1' and transfer_status='0' and fun_change='1' ";
				this.baseJdbcDao.update(sql_bd);
				
	}
	
	@Override
	public void FWQKexport(HttpServletResponse response,String cuid,String lend_id,String lender_name,String batch_id) {
		Date day=new Date();    

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			this.cc(uuid,lend_id);
//		uuid="999858";
		
		//获取出借人
		String sql1="select lend_id from order_info where bd='"+uuid+"' group BY lend_id ORDER BY lend_id ";
		List<Map<String, Object>> result=this.baseJdbcDao.queryListMap(sql1);
		
		//获取数据准备   插入批量转账表
		String sql2="select lend_id, count(lend_id)num ,sum(real_amt)amount from order_info where 1=1  and bd='"+uuid+"' group BY lend_id ORDER BY lend_id";
		List<Map<String, Object>> cr=this.baseJdbcDao.queryListMap(sql2);
		
		
		JSONArray jsonlist =new JSONArray();
//		JSONObject json=new JSONObject();
		for(Map<String, Object> map:result){
			
//			map.put("lend_id", map.get("lend_id"));
			
			String sql="select (select name from client_info c where c.client_id=o.client_id)client_id,real_amt,bank_id,bank_name from order_info o where o.lend_id='"+map.get("lend_id")+"' and o.bd='"+uuid+"'  ";
			List<Map<String,Object>> jl = this.baseJdbcDao.queryListMap(sql);	
//			content.add(jl);
//			System.out.println(content.add(jl));
//			json.put("", jl);
			jsonlist.add(jl);
			System.out.println(jl.toString());
	}
		System.out.println(jsonlist);
		
		
//		String sql="select order_id,createtime,approve_status,order_number,(select name from client_info c where c.client_id=o.client_id)client_id,(select name from lender l where l.lender_id=o.lend_id)lend_id,real_amt,transfer_status,bank_id,bank_name from order_info o   ";
//		List<Map<String,Object>> content = this.baseJdbcDao.queryListMap(sql);	
		
		
		String title = batch_id+"-批量转账导出-"+lender_name;
		String[] line1 = {"银行名称","银行账户","借款人","打款金额 "};//第二行
		String[] s = {"bank_name","bank_id","client_id","real_amt"};
		export(response,title,line1,s,jsonlist,3,batch_id+"-批量转账导出-"+lender_name,cr,uuid,cuid);
		
		
	}

	
	
	public void export(HttpServletResponse response,String title,String[] line1,String[] s,JSONArray jsonlist,int celles,String filename,List<Map<String,Object>> cr,String uuid,String cuid){
		HSSFWorkbook wb = new HSSFWorkbook();//创建excel
		HSSFCellStyle cellStyle = wb.createCellStyle();//创建表格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		Date day=new Date();    
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		//循环插入批量转账表
		for(Map<String, Object> map:cr){
			String sql="INSERT INTO `batch_transfer`(operator_id,down_time,down_file,down_orders,down_amount,lender_id,batch_status,bd) VALUES ("			
					+ "'"+cuid+"'"		
					+ ",'"+df.format(day)+"'"		
					+ ",'/data/file/order/"+filename+".xls'"	
					+ ",'"+map.get("num")+"'"
					+ ",'"+map.get("amount")+"'"	
					+ ",'"+map.get("lend_id")+"'"		
					+ ",'0'"	
					+ ",'"+uuid+"'"	
					+ ");";
			this.baseJdbcDao.insert(sql);
			

			
			
			
		
	}
		//循环生成多个sheet
		for(int i=0;i<jsonlist.size();i++){
			JSONArray aa=(JSONArray) jsonlist.get(i);			
		
			HSSFSheet sheet = wb.createSheet("sheet"+(i+1));//sheet
			sheet.addMergedRegion(new Region(0, (short) 0, 0,  (short)celles));
			int rowIndex = 0;
			int cellindex = 0;
			//设置列宽
			sheet.setDefaultColumnWidth(4);
			sheet.setColumnWidth(0,10120);
		    sheet.setColumnWidth(1,10120);
		    sheet.setColumnWidth(2,5120);
		    sheet.setColumnWidth(3,5120);
		    sheet.setColumnWidth(4,5120);
		    
		    
		    
		    
		    
		    
		    HSSFRow row = sheet.createRow(rowIndex++);
		    Cell cell = row.createCell(0,Cell.CELL_TYPE_STRING);
			cell.setCellValue(title);
			cell.setCellStyle(cellStyle);
			
			row = sheet.createRow(rowIndex++);
			for(String n:line1){
				cell = row.createCell(cellindex,Cell.CELL_TYPE_STRING);
				cell.setCellValue(n);
				cell.setCellStyle(cellStyle);
				cellindex++;
			}
		    
		    for(int j=0;j<aa.size();j++){
		    	row = sheet.createRow(rowIndex++);
		    	cellindex = 0;
		    	JSONObject map=new JSONObject();
		    	map=aa.getJSONObject(j);

//		    	Map<String,Object> map = (Map<String, Object>) aa.get(j);
		    	for(int m=0;m<map.size();m++){
		    		cell = row.createCell(cellindex,Cell.CELL_TYPE_STRING);
		    		if(DataTypeUtil.validate(map.get(s[cellindex]))){
		    			cell.setCellValue(map.get(s[cellindex]).toString());
		    		}else{
		    			cell.setCellValue("0");
		    		}
					cell.setCellStyle(cellStyle);
					cellindex++;
		    	}
		    }
		    
		}
//	    HSSFSheet sheet2 = wb.createSheet("sheet2");//sheet
//		sheet2.addMergedRegion(new Region(0, (short) 0, 0,  (short)celles));
//		int rowIndex2 = 0;
//		int cellindex2 = 0;
//		//设置列宽
//		sheet2.setDefaultColumnWidth(10);
//		sheet2.setColumnWidth(0,5120);
//		sheet2.setColumnWidth(1,5120);
//		sheet2.setColumnWidth(2,5120);
//		sheet2.setColumnWidth(3,5120);
//		sheet2.setColumnWidth(4,5120);
//		sheet2.setColumnWidth(5,5120);
//		sheet2.setColumnWidth(6,5120);
//		sheet2.setColumnWidth(7,5120);
//		sheet2.setColumnWidth(8,5120);
//		sheet2.setColumnWidth(9,5120);
//		sheet2.setColumnWidth(10,5120);
//	    
//	    HSSFRow row2 = sheet2.createRow(rowIndex2++);
//	    Cell cell2 = row2.createCell(0,Cell.CELL_TYPE_STRING);
//		cell2.setCellValue(title);
//		cell2.setCellStyle(cellStyle);
//		
//		row2 = sheet2.createRow(rowIndex2++);
//		for(String n:line1){
//			cell2 = row2.createCell(cellindex2,Cell.CELL_TYPE_STRING);
//			cell2.setCellValue(n);
//			cell2.setCellStyle(cellStyle);
//			cellindex2++;
//		}
//	    
//	    for(int i=0;i<content.size();i++){
//	    	row2 = sheet2.createRow(rowIndex2++);
//	    	cellindex2 = 0;
//	    	Map<String,Object> map = content.get(i);
//	    	for(int j=0;j<map.size();j++){
//	    		cell2 = row2.createCell(cellindex2,Cell.CELL_TYPE_STRING);
//	    		if(DataTypeUtil.validate(map.get(s[cellindex2]))){
//	    			cell2.setCellValue(map.get(s[cellindex2]).toString());
//	    		}else{
//	    			cell2.setCellValue("0");
//	    		}
//				cell2.setCellStyle(cellStyle);
//				cellindex2++;
//	    	}
//	    }
//	    
	    
	    
	    
		String sql_history="INSERT INTO `history_record`(cuid,content,action_type) VALUES ("			
				+ "'"+cuid+"'"				
				+ ",'打款批量导出'"	
				+ ",'0'"
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		com.epmis.aaexport.vo.ExportExcel.export(wb,response,filename);
		
	}
	
	/**
	 *获取相对时间已还款订单    导出功能 
	 */
	@Override
	public void Orderexport(HttpServletResponse response, String rel_time,String cuid) {
		
		String sql_sum="select sum(rel_money) sum from order_info where DATE_FORMAT(rel_time, '%Y-%m-%d')='"+rel_time+"' and  transfer_status in(2,4) ";
		List<Map<String,Object>> smoney = this.baseJdbcDao.queryListMap(sql_sum);
		
		
		String sql="select order_id,(select name from client_info c where c.client_id=o.client_id) name,(select idcard from client_info c where c.client_id=o.client_id) idcard,bank_id,borrow_time,repay_time,rel_time,rel_money from order_info o where DATE_FORMAT(rel_time, '%Y-%m-%d')='"+rel_time+"' and transfer_status in(2,4)";
		List<Map<String,Object>> content = this.baseJdbcDao.queryListMap(sql);	
		
//		String title = batch_id+"-批量转账导出-"+lender_name;
//		String[] line1 = {"银行名称","银行账户","借款人","打款金额 "};//第二行
//		String[] s = {"bank_name","bank_id","client_id","real_amt"};
//		export(response,title,line1,s,jsonlist,3,batch_id+"-批量转账导出-"+lender_name,cr,uuid,cuid);
		
		String title = rel_time+"已还款订单";
		String[] line1 = {"订单编号","用户名","身份证号码","银行卡号 ","借款日期","应还款日期","还款日期","还款金额"};//第二行
		String[] s = {"order_id","name","idcard","bank_id","borrow_time","repay_time","rel_time","rel_money"};
		export1(response,title,line1,s,content,7,rel_time+"已还款订单",smoney,cuid);
	}

	
	
	public void export1(HttpServletResponse response,String title,String[] line1,String[] s,List<Map<String,Object>> content,int celles,String filename,List<Map<String,Object>> smoney,String cuid){
		HSSFWorkbook wb = new HSSFWorkbook();//创建excel
		HSSFCellStyle cellStyle = wb.createCellStyle();//创建表格样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		HSSFSheet sheet = wb.createSheet("sheet1");//sheet
		sheet.addMergedRegion(new Region(0, (short) 0, 0,  (short)celles));
		int rowIndex = 0;
		int cellindex = 0;
		//设置列宽
		sheet.setDefaultColumnWidth(10);
		sheet.setColumnWidth(0,6120);
	    sheet.setColumnWidth(1,6120);
	    sheet.setColumnWidth(2,6120);
	    sheet.setColumnWidth(3,6120);
	    sheet.setColumnWidth(4,6120);
	    sheet.setColumnWidth(5,6120);
	    sheet.setColumnWidth(6,6120);
	    sheet.setColumnWidth(7,6120);
	    sheet.setColumnWidth(8,6120);
	    
	   
	    
	    HSSFRow row = sheet.createRow(rowIndex++);
	    Cell cell = row.createCell(0,Cell.CELL_TYPE_STRING);
		cell.setCellValue(title);
		cell.setCellStyle(cellStyle);
		
		row = sheet.createRow(rowIndex++);
		for(String n:line1){
			cell = row.createCell(cellindex,Cell.CELL_TYPE_STRING);
			cell.setCellValue(n);
			cell.setCellStyle(cellStyle);
			cellindex++;
		}
	    
	    for(int i=0;i<content.size();i++){
	    	row = sheet.createRow(rowIndex++);
	    	cellindex = 0;
	    	Map<String,Object> map = content.get(i);
	    	for(int j=0;j<map.size();j++){
	    		cell = row.createCell(cellindex,Cell.CELL_TYPE_STRING);
	    		if(DataTypeUtil.validate(map.get(s[cellindex]))){
	    			cell.setCellValue(map.get(s[cellindex]).toString());
	    		}else{
	    			cell.setCellValue("0");
	    		}
				cell.setCellStyle(cellStyle);
				cellindex++;
	    	}
	    	
	    }
	    row = sheet.createRow(rowIndex++);
	    cell = row.createCell(7,Cell.CELL_TYPE_STRING);
	    cell.setCellValue("总计："+smoney.get(0).get("sum").toString()+"元");
	    String sql_history="INSERT INTO `history_record`(cuid,content,action_type) VALUES ("			
				+ "'"+cuid+"'"				
				+ ",'已还款订单导出'"
				+ ",'2'"
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		com.epmis.aaexport.vo.ExportExcel.export(wb,response,filename);
		
		
	}

	
	
	
	
	
	
}
