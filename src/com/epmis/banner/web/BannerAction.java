package com.epmis.banner.web;

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

import com.epmis.banner.service.BannerService;
import com.epmis.banner.vo.Banner;
import com.epmis.car.service.CarService;
import com.epmis.car.vo.Tcar;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BannerAction extends ActionSupport implements ServletRequestAware{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private BannerService bannerService;
	private Banner banner;
	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
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
	
	public void findAllBanner(){
	
		String isdel = request.getParameter("isdel");	
		
		List<Map<String, Object>> result = this.bannerService.findAllBanner(isdel,page,rows);
		for(Map<String, Object> map:result){
			String sta = map.get("type").toString();
			if("2".equals(sta)){//链接
//				System.out.println("进来1");
//				System.out.println(map.get("url"));
				map.put("contentandurl", map.get("url"));
			}else if("1".equals(sta)){//文本
				
				map.put("contentandurl", map.get("content"));
			}
		}		
		int count=this.bannerService.count(isdel);
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
			result = this.bannerService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	

	
	/**
	 * 位置
	 */
	public void wz(){
		String result = this.bannerService.wz(banner);
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	
	
	
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save() throws Exception{
		
		banner.setPic(this.execute(file));
		String result =bannerService.save(banner);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询汽车列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		String cid = request.getParameter("cid");
		String id=ids;
		if(null==ids||"".equals(ids)){
			id=cid;
		}
		
		String sss="success";
		List<Map<String, Object>> result = this.bannerService.findById(id);
		request.setAttribute("banner", result);
		if(null!=cid && !"".equals(cid)){
			sss= "look";
		}
		return sss;
		
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update() throws Exception{
		banner.setPic(this.execute(file));
		String result = this.bannerService.update(banner);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	private File file;
	private String fileFileName;
	private String fileFileContentType;

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
				String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
				fileFileName = uu+""+lastName;
	        	String filePath = dirPath+"/"+fileFileName;
	 	        OutputStream os = new FileOutputStream(filePath);
		        String src="/upload/"+fileFileName;
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
