package com.epmis.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.app.service.AuthcodeService;
import com.epmis.app.service.HrExamService;
import com.epmis.app.vo.Authcode;
import com.epmis.app.vo.User;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DESEncrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SqlUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller 
public class UserOpera {
	 @Autowired
	 @Qualifier("baseJdbcDao")
	 private BaseJdbcDao baseJdbcDao;
	// 由于是提交数据所以我们这里使用POST请求

	 
	 @Autowired
	 private AuthcodeService authcodeService;
	 
	 
	 
	 @ResponseBody	
		@RequestMapping(value = "/api/user/login.do")
		public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
	    	
			Map<String, Object> resObj = new HashMap<String, Object>();
			
			try {
			
				String tele =  request.getParameter("tele"); 
				String password =  request.getParameter("password"); 
				String qqid =  request.getParameter("qqid"); 
				String weixinid =  request.getParameter("weixinid"); 
				
				String sinaid =  request.getParameter("sinaid"); 
				String sql =	" SELECT WID, USERID,SCOREID,COLLECTIONSID,CARTID,USERNAME,PASSWORD,USERTYPE,SEX,"+
						" 	DATE_FORMAT(BIRTHDAY,'%Y-%m-%d')  BIRTHDAY,TELE,WEIXINID,"+
						" 	QQID,SINAID,DATE_FORMAT(REGISTERTIME,'%Y-%m-%d %H:%i:%s') REGISTERTIME,"+
						" 	NICKNAME,PARAMETERID,CREATEBY, DATE_FORMAT(CREATETIME,'%Y-%m-%d %H:%i:%s') CREATETIME,UPDATEBY,"+
						" 	DATE_FORMAT(UPDATETIME,'%Y-%m-%d %H:%i:%s') UPDATETIME,"+
						"	SORTNUMBER  FROM AJT_USERS WHERE TELE='"+tele+"' and `PASSWORD`='"+password+"'";
			
				
				List<Map<String, Object>> userList =  baseJdbcDao.queryListMap(sql); 

				if(DataTypeUtil.validate(userList)){
					resObj.put("RESULT", "1");
					resObj.put("userList",userList);
					
			  	}else{
					resObj.put("RESULT", "10011");//密码错误
					
				}
			} catch (Exception e) {
				resObj.put("RESULT", "10010");
				e.printStackTrace();
			}	

			WriterJsonArray.AppWriterJSONArray(resObj,response);
		}
	    

	 

	 
	@ResponseBody
	@RequestMapping(value = "/api/user/registered.do")
	public void registered(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String tele=  request.getParameter("tele"); 
			String codenumber=  request.getParameter("codenumber"); 	
			String password=  request.getParameter("password"); 	
			String password1=  request.getParameter("password1"); 
			String usertype=  request.getParameter("usertype"); 
				//先查询验证码表中有没有手机号
				String sql = "SELECT * FROM AJT_AUTHCODE WHERE  TELE = '"+tele+"' ORDER BY CODETIME DESC";
				//执行sql
				Map<String,Object> mapCode=baseJdbcDao.queryMap(sql);
				//如果有手机号
				if (DataTypeUtil.validate(tele)) {
					
					//获取验证码，手机号，验证码生成时间
					String vcCode = (String)mapCode.get("CODENUMBER");
					String vTele = (String)mapCode.get("TELE");
					Date vcTime = (Date)mapCode.get("CODETIME");
					//
					if (vcCode== null&&vcCode=="" || vcTime == null || vTele==null&&vTele=="") {
						resObj.put("RESULT", "3");//，没有获取到验证码
					}
					if (!vTele.equals(tele)) {
						resObj.put("RESULT", "4");//，手机号输入错误
						
					}
					
					if (password!=null&&password!="" && !password1.equals(password)) {
						resObj.put("RESULT", "5");//，两次密码不一致。
					}
				//如果输入验证码等于数据库验证码
				
					if (codenumber.equals(vcCode)) {
						
						try {
							
							String sql1 = "SELECT * FROM AJT_USERS WHERE TELE = '"+tele+"'";
							Map<String,Object> map=baseJdbcDao.queryMap(sql1);
							if(DataTypeUtil.validate(map)){
								String vTeles = (String)map.get("TELE");
							   if(tele.equals(vTeles)){
									resObj.put("RESULT", "2");//手机号已存在
								}
						   }
							//添加用户
							
							String sql2="INSERT INTO AJT_USERS(WID,USERID,TELE,PASSWORD,USERTYPE)"
									+ "VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+tele+"','"+ password+"','"+usertype+"')";
							this.baseJdbcDao.insert(sql2);
							resObj.put("RESULT", "1");
							//到时时间删除验证码
							String sql3 = "DELETE FROM AJT_AUTHCODE WHERE CODENUMBER = '"+codenumber+"' ORDER BY CODETIME DESC";
							//执行
							this.baseJdbcDao.delete(sql3);
							
						} catch (Exception e) {
							resObj.put("RESULT", "0");
							e.printStackTrace();
						}	
				
				}else {
					resObj.put("RESULT", "6");//6，验证码错误
				}
			} 
		}catch (Exception e) {
					resObj.put("RESULT", "0");
					e.printStackTrace();
				}		
				WriterJsonArray.AppWriterJSONArray(resObj,response);
				
				
	}
	//短信验证码
	@ResponseBody
	@RequestMapping(value = "/api/user/message.do")
	public  void duanxin(HttpServletRequest request,HttpServletResponse response)throws IOException{
		Map<String, Object> resObj = new HashMap<String, Object>();
		
	   String tele= request.getParameter("tele");
	    String code = (int) (Math.random() * 900000 + 100000) + "";
	    
	    Authcode auth=new Authcode();
	    auth.setTele(tele);
	    auth.setCodenumber(code);
	    String codeid= authcodeService.AddCode(auth);
		resObj.put("RESULT", "1");
	//	resObj.put("CODEID", codeid);
	    
		HttpClient client = new HttpClient();
	    
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		
		NameValuePair[] data ={ new NameValuePair("Uid", "809789392@qq.com"),new NameValuePair("Key", "a4b5d0b1eb1cca8b4b46"),new NameValuePair("smsMob",tele),new NameValuePair("smsText",code)};
		post.setRequestBody(data);
		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		
		
		System.out.println(result); //打印返回消息状态
	
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		post.releaseConnection();

	}
	//绑定亲属
	@ResponseBody
	@RequestMapping(value = "/api/user/relations.do")
	public  void relations(HttpServletRequest request,HttpServletResponse response)throws IOException{
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
			
		    String ownid= request.getParameter("ownid");
		    String relativesTeles =  request.getParameter("relativesTeles");
		    
		    String sql="SELECT * FROM AJT_USERS WHERE TELE='"+relativesTeles+"'";
		    Map<String,Object> mapUser=baseJdbcDao.queryMap(sql);
		    String otherid=(String)mapUser.get("USERID");
		    String rtele = (String)mapUser.get("TELE");
		   if(!ownid.equals(otherid)){
			    if (DataTypeUtil.validate(relativesTeles)) {
			    	
			    	if(rtele.equals(relativesTeles)){ 
							 String sql2="INSERT INTO AJT_RELATIONS(WID,RELATIONID,OWNID,OTHERID)"
										+ "VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ownid+"','"+otherid+"')";
								this.baseJdbcDao.insert(sql2);
					}
			    }else{
			    	resObj.put("RESULT", "空");
			    }			
		   }else{
			   resObj.put("RESULT", "不能绑定自己");
		   }		
	    }catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	    }
	
		
	//设置个人信息
		@ResponseBody
		@RequestMapping(value = "/api/user/information.do")
		public void information(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try{
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				String userid=request.getParameter("userid");
				String nickname= request.getParameter("nickname");
				String sex=request.getParameter("sex");
				String birthday=request.getParameter("birthday");
				String tele=request.getParameter("tele");
				String inforSql="SELECT NICKNAME,SEX,BIRTHDAY,TELE FROM AJT_USERS WHERE USERID='"+userid+"'";
				Map<String,Object> inforMap=baseJdbcDao.queryMap(inforSql);
				if(DataTypeUtil.validate(inforMap)){
					String updateSql="UPDATE AJT_USERS SET NICKNAME='"+nickname+"',SEX='"+sex+"',BIRTHDAY='"+birthday+"' WHERE USERID='"+userid+"'";
					this.baseJdbcDao.update(updateSql);
						resObj.put("RESULT", "1");//修改成功
						
				}

			}catch(Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		
			WriterJsonArray.AppWriterJSONArray(resObj,response);
		}
		
/*		@Autowired
	    private PicTableService picTableService;
		//上传头像
		@ResponseBody
		@RequestMapping(value = "/api/AddPic.do")
		public void AddPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	  response.setContentType("text/html");
    	  response.setCharacterEncoding("UTF-8");   
	    	Map<String, Object> resObj = new HashMap<String, Object>();  
	    	ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory()); 
	    	UserInfo userInfo = new UserInfo();  
	    	String jsonString="";
			try {
				List  fileList = sfu.parseRequest(request);
				for(Object object:fileList){ 
				    FileItem fileItem = (FileItem) object; 
				    if (fileItem.isFormField()&&fileItem.getFieldName().equals("jsonString")) { 
				     	jsonString = fileItem.getString();//
				    	break;
				    } 
				}
			//    String jsonString=  request.getParameter("jsonString");    
		        JSONObject jsonObj = JSONObject.fromObject(jsonString);
			 	String userId =  Base64Encrypt.decode(jsonObj.getString("userId"));			 	
			 	String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
			 	JSONArray dateList = jsonObj.getJSONArray("dataList"); 
			 	
			 	
			 
			 	String actualName = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT NICKNAME FROM CM_USERS WHERE USERSID = '"+userId+"'"));
			 	String projName = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID = '"+projId+"'"));
  
			 	userInfo.setUserId(userId);
			 	userInfo.setProjId(projId);
			 	userInfo.setActualName(actualName);
				if(DataTypeUtil.validate(userId)){
					String path = AppSetting.PROJECT_PATH+"/KM/temp/"+userId; 
					 File file = new File(path);
					 if(!file.exists()){
						 file.mkdirs();
					 }else{
						 FileUtil.delAllFile(path);
					 }
					  
							   
							   String sourceName = "";
							   String inputName = "";
							   for (int i = 0; i < fileList.size(); i++) {
								   try{
				 					    FileItem fi = (FileItem) fileList.get(i);
									    if (!fi.isFormField()) {
										     sourceName = fi.getName();
										     inputName = fi.getFieldName();
					 					     if (sourceName == null || "".equals(sourceName.trim())) {
										      continue;
										     }			
										     File saveSourceFile = new File(path +"/" + inputName+"_"+sourceName);
										     fi.write(saveSourceFile);
										     
							            }
						         }catch(Exception e){
								    e.printStackTrace();
								    continue;
							     }
					           }
							   PicTable picTable = new PicTable();
							   List<Map<String,Object>> list  =  new ArrayList<Map<String,Object>>();
							   for (int j = 0; j < dateList.size(); j++) {
								   Map<String, Object> res = new HashMap<String, Object>();
								   try{						    
									    String imgName =  Base64Encrypt.decode(dateList.getJSONObject(j).getString("imgName")); 
									    String name = Base64Encrypt.decode(dateList.getJSONObject(j).getString("name"));
									    res.put(name, "error");
								 	    String content =  Base64Encrypt.decode(dateList.getJSONObject(j).getString("content")); 
									 	String imgTime =   Base64Encrypt.decode(dateList.getJSONObject(j).getString("imgTime")); 
									 	String shootpart = Base64Encrypt.decode(dateList.getJSONObject(j).getString("shootpart")); 
									 	String depart = Base64Encrypt.decode(dateList.getJSONObject(j).getString("mark")); 
									 	File tempfile = new File(path+"/"+name+"_"+imgName);
									 	 if(tempfile.exists()&&tempfile.isFile()){		
									     	 picTable.setImgName(imgName);
									    	 picTable.setUploadPerson(actualName);
									         picTable.setContent(content);
									    	 picTable.setProjName(projName);
									    	 picTable.setImgTime(null);
									         picTable.setImgTime(DataTypeUtil.validate(imgTime)? java.sql.Date.valueOf(imgTime):null);
									         picTable.setShootpart(shootpart);
									         picTable.setDepart(depart);
									         picTableService.AddPic(userInfo,tempfile,picTable);
										 	 res.put(name, "success");
										 	 list.add(res);
							             } 

								   }catch(Exception e){
									    e.printStackTrace();
									    list.add(res);
									    continue;
								   }
						       }
							   if(DataTypeUtil.validate(list)){
								   resObj.put("RESULT", "1");
								   resObj.put("LIST", list);
							   }
					 
				}else{
					resObj.put("RESULT", "0");
				}    
			} catch (Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		   
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			
		}*/ 
		
}

	




