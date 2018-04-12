package com.epmis.car.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epmis.car.service.CarService;
import com.epmis.car.vo.Tcar;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;
@SuppressWarnings("serial")
public class CarAction extends ActionSupport implements ServletRequestAware {
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private CarService carService;
	private Tcar car;
	
	public Tcar getCar() {
		return car;
	}

	public void setCar(Tcar car) {
		this.car = car;
	}

	private int page;
	private int rows;
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	HttpServletRequest request;
	HttpServletResponse response= ServletActionContext.getResponse();
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		
	}
	
	public void findAllCar(){
		System.out.println("page:"+page+" rows:"+rows);
		String title = request.getParameter("title");
		String status = request.getParameter("status");
		String isdel = request.getParameter("isdel");
		String xin = request.getParameter("xin");
		String bid = request.getParameter("bid");
		String jid = request.getParameter("jid");
		String gid = request.getParameter("gid");
		String carsysid = request.getParameter("carsysid");
		String type = request.getParameter("type");
		String csid=request.getParameter("csid");
		
		
		
		
		List<Map<String, Object>> result = this.carService.findAllCar(title,status,isdel,xin,bid,jid,gid,carsysid,type,csid,page,rows);
		for(Map<String, Object> map:result){
			
				map.put("ids", map.get("id"));
				if("0".equals(String.valueOf(map.get("isdel")))&& "0".equals(String.valueOf(map.get("type")))){
					
					String wzsql="select count(1)+1 wz from t_car where isdel='0' and type='0' and orderflag<'"+String.valueOf(map.get("orderflag"))+"'";
					List<Map<String, Object>> wzs=this.baseJdbcDao.queryListMap(wzsql);
					map.put("wzs", wzs.get(0).get("wz"));					
					
				}else{
					map.put("wzs", '-');
				}
				
				
				
				
				
			
		}
		
		
		int count=this.carService.count(title,status,isdel,xin,bid,jid,gid,carsysid,type,csid);
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("rows", result);
		jsonMap.put("total", count);
		JSONObject json = JSONObject.fromObject(jsonMap);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Charset","utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 删除数据
	 */
	public void delete(){
		String id = request.getParameter("id");
		System.out.println(id);
		String result="";
		String [] stringArr= id.split(",");
		for(int i=0;i<stringArr.length;i++){
			result = this.carService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 * 彻底删除数据（物理删除）
	 */
	public void deletez(){
		String id = request.getParameter("id");
		System.out.println(id);
		String result="";
		String [] stringArr= id.split(",");
		for(int i=0;i<stringArr.length;i++){
			result = this.carService.deletez(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 * 恢复删除
	 */
	public void hf(){
		String id = request.getParameter("id");
		
		String result = this.carService.hf(id);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 * 显示--->隐藏
	 */
	public void xs(){
		String id = request.getParameter("id");
		
		String result = this.carService.xs(id);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 * 隐藏--->显示
	 */
	public void yc(){
		String id = request.getParameter("id");
		
		String result = this.carService.yc(id);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 位置
	 */
	public void wz(){
		
//		String sql="select id from t_car where isdel='0' and type='0' ORDER BY orderflag asc ";
		String result = this.carService.wz(car);
		
//		int shu=0;		
//		List<Map<String, Object>> lihai =this.baseJdbcDao.queryListMap(sql);
//		for(Map<String, Object> map:lihai){
//			shu=shu+1;
//			String sql1="update t_car set orderflag='"+shu+"' where id="+map.get("id").toString();
//			this.baseJdbcDao.update(sql1);
//		}
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 查询单个汽车的销量
	 * @return
	 */
	public String xiaoliang(){
		String ids=request.getParameter("cid");
		List<Map<String, Object>> xl=this.carService.findById(ids);
		request.setAttribute("xl", xl);
		return "success";
	}
	
	
	public void upxl(){
		String result=this.carService.upxl(car);
		WriterJsonArray.writerJSONArray(result, response);
		
		
	}
	
	
	
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save() throws Exception{
		System.out.println("jin ru wen jian");
		String fuzhi=request.getParameter("fuzhi");
		car.setPic(this.execute(file));
		car.setIco(this.executexiao(file1));
		car.setVedioPic(this.executedi(file3));
		car.setCar_vedio(this.executeTwo(file2));
		String result =carService.save(car,fuzhi);
		
		
		
		
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询汽车列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		String fuzhi=request.getParameter("fuzhi");
		List<Map<String, Object>> result = this.carService.findById(ids);
		String ss="success";
		if("19491001".equals(fuzhi)){
			ss="fuzhi";			
			
		}
		request.setAttribute("car", result);
		return ss;
		
	}
	
	/**
	 * 修改(没有用到)
	 * @throws Exception 
	 */
	public void update() throws Exception{
		System.out.println(car.getPic());
		System.out.println("zhu"+file);
		System.out.println("xiao"+file1);
		System.out.println("diyi"+file3);
		car.setPic(this.execute(file));
		System.out.println("-----"+car.getPic());
		car.setIco(this.executexiao(file1));
		System.out.println("-----xiao"+car.getIco());
		
		car.setVedioPic(this.executedi(file3));
		car.setCar_vedio(this.executeTwo(file2));
		String result = this.carService.update(car);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	/**
	 * 复制
	 * @throws Exception 
	 */
	public void fuzhi() throws Exception{
		
		car.setPic(this.execute(file));
		
		car.setIco(this.executexiao(file1));		
		car.setVedioPic(this.executedi(file3));
		car.setCar_vedio(this.executeTwo(file2));
		String result = this.carService.update(car);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	//视频第一幁图片
	private File file3;
	private String file3FileName;
	private String fileFileContentType3;
	


	public File getFile3() {
		return file3;
	}

	public void setFile3(File file3) {
		this.file3 = file3;
	}

	

	public String getFile3FileName() {
		return file3FileName;
	}

	public void setFile3FileName(String file3FileName) {
		this.file3FileName = file3FileName;
	}

	public String getFileFileContentType3() {
		return fileFileContentType3;
	}

	public void setFileFileContentType3(String fileFileContentType3) {
		this.fileFileContentType3 = fileFileContentType3;
	}
	
	//视频上传
	private File file2;
	private String fileFileName2;
	private String fileFileContentType2;
	
	
public File getFile2() {
		return file2;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public String getFileFileName2() {
		return fileFileName2;
	}

	public void setFileFileName2(String fileFileName2) {
		this.fileFileName2 = fileFileName2;
	}

	public String getFileFileContentType2() {
		return fileFileContentType2;
	}

	public void setFileFileContentType2(String fileFileContentType2) {
		this.fileFileContentType2 = fileFileContentType2;
	}

private File file;
private File file1;
private String fileFileName1;
private String fileFileContentType1;

public String getFileFileName1() {
	return fileFileName1;
}

public void setFileFileName1(String fileFileName1) {
	this.fileFileName1 = fileFileName1;
}

public String getFileFileContentType1() {
	return fileFileContentType1;
}

public void setFileFileContentType1(String fileFileContentType1) {
	this.fileFileContentType1 = fileFileContentType1;
}

private String fileFileName;
private String file1FileName;
private String file2FileName;
private String fileFileContentType;


public String getFile1FileName() {
	return file1FileName;
}

public void setFile1FileName(String file1FileName) {
	this.file1FileName = file1FileName;
}

public String getFile2FileName() {
	return file2FileName;
}

public void setFile2FileName(String file2FileName) {
	this.file2FileName = file2FileName;
}

//图片上传
public String execute(File file) throws Exception{
		String root = ServletActionContext.getServletContext().getRealPath("/");
		String dirName ="upload";
		String dirPath = root+dirName;
		//拼接文件夹
   	 	File dirFile = new File(dirPath);
			if(!dirFile.exists()){
				dirFile.mkdir();	//创建文件夹
			}
        if(file!=null&&!"".equals(file)){
        	InputStream is = new FileInputStream(file);
        	 //中文乱码处理
			String uu = UUID.randomUUID().toString().replace("-", "");
			System.out.println("fileFileName="+fileFileName);
			System.out.println("fileFileName1="+fileFileName1);
			System.out.println("file1FileName="+file1FileName);
			System.out.println("fileFileName3="+file3FileName);
			String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
			System.out.println("lastName="+lastName);
			fileFileName = uu+""+lastName;
			System.out.println("fileFileName="+fileFileName);
        	String filePath = dirPath+"/"+fileFileName;
        	System.out.println("filePath="+filePath);
 	        OutputStream os = new FileOutputStream(filePath);
 	        System.out.println("os="+os);
	        String src="/"+dirName+"/"+fileFileName;
	        System.out.println("src"+src);
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

public String executexiao(File file) throws Exception{
	String root = ServletActionContext.getServletContext().getRealPath("/");
	String dirName ="upload";
	String dirPath = root+dirName;
	//拼接文件夹
	 	File dirFile = new File(dirPath);
		if(!dirFile.exists()){
			dirFile.mkdir();	//创建文件夹
		}
    if(file!=null&&!"".equals(file)){
    	InputStream is = new FileInputStream(file);
    	 //中文乱码处理
		String uu = UUID.randomUUID().toString().replace("-", "");
		
		String lastName = file1FileName.substring(file1FileName.lastIndexOf("."));
		System.out.println("lastName="+lastName);
		fileFileName = uu+""+lastName;
		
    	String filePath = dirPath+"/"+fileFileName;
    	
	        OutputStream os = new FileOutputStream(filePath);
	        
        String src="/"+dirName+"/"+fileFileName;
        
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


public String executedi(File file) throws Exception{
	String root = ServletActionContext.getServletContext().getRealPath("/");
	String dirName ="upload";
	String dirPath = root+dirName;
	//拼接文件夹
	 	File dirFile = new File(dirPath);
		if(!dirFile.exists()){
			dirFile.mkdir();	//创建文件夹
		}
    if(file!=null&&!"".equals(file)){
    	InputStream is = new FileInputStream(file);
    	 //中文乱码处理
		String uu = UUID.randomUUID().toString().replace("-", "");
		
		String lastName = file3FileName.substring(file3FileName.lastIndexOf("."));
		System.out.println("lastName="+lastName);
		fileFileName = uu+""+lastName;
		
    	String filePath = dirPath+"/"+fileFileName;
    	
	        OutputStream os = new FileOutputStream(filePath);
	        
        String src="/"+dirName+"/"+fileFileName;
        
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



//视频上传
public String executeTwo(File file) throws Exception{
	String root = ServletActionContext.getServletContext().getRealPath("/");
	String dirName ="upload";
	String dirPath = root+dirName;
	//拼接文件夹
	 	File dirFile = new File(dirPath);
		if(!dirFile.exists()){
			dirFile.mkdir();	//创建文件夹
		}
    if(file!=null&&!"".equals(file)){
    	InputStream is = new FileInputStream(file);
    	 //中文乱码处理
		String uu = UUID.randomUUID().toString().replace("-", "");
		String lastName = file2FileName.substring(file2FileName.lastIndexOf("."));
		fileFileName = uu+""+lastName;
    	String filePath = dirPath+"/"+fileFileName;
	        OutputStream os = new FileOutputStream(filePath);
        String src="/"+dirName+"/"+fileFileName;
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

public File getFile1() {
	return file1;
}

public void setFile1(File file1) {
	this.file1 = file1;
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
