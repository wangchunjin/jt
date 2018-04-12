package com.epmis.aexcel.web;

  

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.b510.excel.ReadExcel;
import com.df.jiguang.JiguangPush_yonghu;
import com.epmis.car.service.CarService;
import com.epmis.car.vo.Ecar;
import com.epmis.car.vo.Tcar;



import com.epmis.jt.batchtransfe.service.BatchtransfeService;
import com.epmis.jt.batchtransfe.vo.Batchtransfer;
import com.epmis.jt.orderinfo.service.OrderinfoService;
import com.epmis.jt.orderinfo.vo.Orderinfo;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ExcelAction extends ActionSupport implements ServletRequestAware{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	HttpServletRequest request;
	HttpServletResponse response= ServletActionContext.getResponse();


	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;

	}
	
	
	
	
	
	
	
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private OrderinfoService orderinfoService;
	
	@Autowired
	private BatchtransfeService batchtransfeService;
	
	
	public void del(){
		String del=request.getParameter("del");
		System.out.println("del:"+del);
		String result="";
		//清空购车方案（针对车型）
		if("2".equals(del)){
			String sql="truncate table t_cardemo;";
			System.out.println("sql:"+sql);
			 boolean a=this.baseJdbcDao.delete(sql);
			 System.out.println("a:"+a);
			 result="success";
			
		}
		//清空购车方案（针对设备下的车型）
		if("3".equals(del)){
			String sql="truncate table t_ecardemo;";
			System.out.println("sql:"+sql);
			 this.baseJdbcDao.delete(sql);
			 boolean a=this.baseJdbcDao.delete(sql);
			 System.out.println("a:"+a);
			 result="success";
		}
		
		
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	
	
	
	
	public void edmt() throws Exception{
		String cuid=request.getParameter("cuid");
		String sql_history="INSERT INTO `history_record`(cuid,content,action_type) VALUES ("			
				+ "'"+cuid+"'"				
				+ ",'打款批量导入'"	
				+ ",'1'"	
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		//获取订单绑定的唯一值 bd
		String bd=request.getParameter("bd");
		String batch_id=request.getParameter("batch_id");
		String lender_id=request.getParameter("lender_id");
		String a=this.execute(file,batch_id,lender_id);
		System.out.println("a:"+a);
		String result="";
		String type=request.getParameter("type");
		//获取路径
	String path=request.getParameter("path");
	path=a;
	
//	String path="E:/Program Files/Apache Software Foundation/Tomcat 7.0/webapps/guan_zhong/易车购 车型_参数_完整版.xls";
//	path="C:/tomcat80/webapps/jietiao"+path;//本地的地址
	path="/data/file/order/"+path;//线上服务器的地址
	ReadExcel xlsMain = new ReadExcel();
	System.out.println(path);
	
	
	//当type==1时，批量导入打款订单 修改状态
	if("1".equals(type)){
		System.out.println("type:"+type);
		//玻璃配置
		result="导入失败！";
			Orderinfo car=null;
//			List<Ecar> listcar = xlsMain.readXlscar(path);
			List<Orderinfo> listcar=null;
			try {
			 listcar = xlsMain.readXlsorder(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			for (int i = 0; i < listcar.size(); i++) {
				car = listcar.get(i);
				orderinfoService.update(car,bd);
			}
			Batchtransfer b=new Batchtransfer();			
			//获取时间
			Date day=new Date();		
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			
			//批量导入时间  导入地址  导入订单数   平帐订单数   导入总金额  操作状态
			String sql_order="SELECT count(order_id)num,sum(real_amt)real_amt FROM order_info where bd='"+bd+"' and transfer_status='1'";
			//查看是否有异常订单
			String sql_status="select count(1)sum from order_info where bd='"+bd+"' and transfer_status <> '1'";
			 List<Map<String, Object>> batch=this.baseJdbcDao.queryListMap(sql_order);
			 List<Map<String, Object>> statu=this.baseJdbcDao.queryListMap(sql_status);			 
			 
			 b.setUp_time(df.format(day).toString());
			 b.setUp_file(path);
			 b.setUp_orders(Integer.toString(listcar.size()));
			 b.setUp_amount(batch.get(0).get("real_amt").toString());
			 b.setBalance_orders(batch.get(0).get("num").toString());
			 if("0".equals(statu.get(0).get("sum").toString())){
				 b.setBatch_status("1");
			 }
			 b.setBatch_status("2");
			 b.setBatch_id(batch_id);
			 batchtransfeService.update(b);
			 //改掉所有导入订单不成功的状态 为7 (打款异常)
			 String sql_uo="update  order_info  set transfer_status='7' where   bd='"+bd+"' and transfer_status <> '1'";
			 this.baseJdbcDao.update(sql_uo);
			 
			 //<以下为推送内容
			 //查询此次导入订单打款状态为1的订单
//			 String sql_success_count="select count(1) from order_info where   bd='"+bd+"' and transfer_status='1'";
//			 int s=this.baseJdbcDao.getCount(sql_success_count);
//			 if(s>0){
//				 String sql_success="select mobile telephone from client_info where client_id in (select client_id from order_info where   bd='"+bd+"' and transfer_status='1' )";
//				 List<Map<String,Object>> results1 =this.baseJdbcDao.queryListMap(sql_success);
//				 List<String> phoneList = new ArrayList<String>();
//				 for (Map<String, Object> map : results1) {
//						phoneList.add(map.get("telephone").toString());
//					}
//				JiguangPush_yonghu.sendAlias("您的借款已到账,请注意查收！",phoneList);					
//			 }
			 //查询此次导入订单打款状态为7的订单
//			 String sql_fail_count="select count(1) from order_info where   bd='"+bd+"' and transfer_status='7'";
//			 int f=this.baseJdbcDao.getCount(sql_fail_count);
//			 if(f>0){
//				 String sql_fail="select mobile telephone from client_info where client_id in (select client_id from order_info where   bd='"+bd+"' and transfer_status='7' )";
//				 List<Map<String,Object>> results2 =this.baseJdbcDao.queryListMap(sql_fail);
//				 List<String> phoneList = new ArrayList<String>();
//				 for (Map<String, Object> map : results2){
//						phoneList.add(map.get("telephone").toString());
//					}
//				JiguangPush_yonghu.sendAlias("您的借款失败！",phoneList);			 
//			 }
			 //>  推送内容  闭合
			 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//当type==4时，导入车型(修改)
			if("4".equals(type)){
				System.out.println("type:"+type);
				//玻璃配置
				result="导入失败！";
					Ecar car=null;
//					List<Ecar> listcar = xlsMain.readXlscar(path);
					List<Ecar> listcar=null;
					try {
					 listcar = xlsMain.readXlscar(path);
					} catch (Exception e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
					}
					
					for (int i = 0; i < listcar.size(); i++) {
						car = listcar.get(i);
						
						int count=carService.count(car.getId());
						System.out.println("count:"+count);
			//			List l = DbUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + student.getName() + "%'", student);
						if (count<1) {//判断是否存在，不存在就新增数据
							System.out.println("count:"+count);
//							carService.save(car,"110");
			//				DbUtil.insert(Common.INSERT_STUDENT_SQL, student);
						} else {//判断是否存在，不存在就  更新  数据
							System.out.println("car:"+car.getId());
							carService.updateExcel(car);
//							System.out.println("The Record was Exist : No. = " + car.getId() + " , and has been throw away!");
						}
					}
				
				
			}
	
	}
	
	
	private File file;
	private String fileFileName;
	private String fileFileContentType;

	public String execute(File file,String batch_id,String lender_id) throws Exception{
		Date day=new Date();    

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 
		System.out.println(file);
			String root = ServletActionContext.getServletContext().getRealPath("/");
			System.out.println("root="+root);
			String dirName ="upload";
			String dirPath = root+dirName;
			System.out.println("dirPath="+dirPath);
			System.out.println("fileFileName="+fileFileName);
			System.out.println("fileFileContentType="+fileFileContentType);
			//拼接文件夹
	   	 	File dirFile = new File(dirPath);
				if(!dirFile.exists()){
					dirFile.mkdir();	//创建文件夹
				}
	        if(file!=null&&!"".equals(file)){
	        	InputStream is = new FileInputStream(file);
	        	 //中文乱码处理
//				String uu = UUID.randomUUID().toString().replace("-", "");
	        	String uu=batch_id+"-批量导入-"+lender_id;
				String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
				fileFileName = uu+""+lastName;
	        	String filePath = dirPath+"/"+fileFileName;
//	 	        OutputStream os = new FileOutputStream(filePath);
	 	       OutputStream os = new FileOutputStream("//data//file//order//"+fileFileName);
//		        String src="/"+dirName+"/"+fileFileName;
		        String src="/"+fileFileName;
		        byte[] buffer = new byte[500];
		        int length = 0;
	        
	        while(-1 != (length = is.read(buffer, 0, buffer.length)))
	        {
	            os.write(buffer);
	        }
		        os.close();
		        is.close();
		        return src ;
	        }
	        return "";
	    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
