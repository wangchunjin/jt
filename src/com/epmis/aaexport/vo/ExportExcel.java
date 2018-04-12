package com.epmis.aaexport.vo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {

	public static void export(HSSFWorkbook wb, HttpServletResponse response, String name){
		OutputStream os = null;
		try {
			
			response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(name, "utf-8")+".xls");  
			os = response.getOutputStream();
			
			wb.write(os);
			//2222222  直接下载到项目所在的c盘
			//D:/User/ .metadata /.me_tcat7 /webapps /jietiao/upload
			
			os = new FileOutputStream("//data//file//order//"+name+".xls");
			wb.write(os);
			os.flush();
			os.close();
			//2222222
			System.out.println("导出:"+name);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null!=os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
