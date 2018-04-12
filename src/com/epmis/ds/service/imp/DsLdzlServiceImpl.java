package com.epmis.ds.service.imp;

import com.epmis.ds.service.DsLdzlService;
import com.epmis.sys.dao.BaseJdbcDao;

import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;

import com.epmis.sys.util.UserInfo;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dsLdzlService")
public class DsLdzlServiceImpl implements DsLdzlService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

@Override
public String DsLdzlTable(String moduleCode, UserInfo userInfo) {
	String userId = userInfo.getUserId();
	String projId = userInfo.getProjId(); 
	String max_time = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(created_time) created_time from ds_log_hz t where proj_id = '"+projId+"' and type = 'OPEN' and user_id = '"+userId+"' and module_code='"+moduleCode+"' "));
	if(!DataTypeUtil.validate(max_time)){
	    max_time = "2000-01-01 10:55:17";
    }
	String content="";
	if(moduleCode.equals("DS_DAILY_HZ")){
		String sql = "   select wid, daily_id, (select code_name from cm_code where id = t.name) name, proj_id,case when  created_date  > '"+max_time+"' then '1' else '0' end as type, (select code_name from cm_code where id = t.status) status, remark, created_date, created_by, daily_link_id from ds_daily   t  where t.proj_id = '"+projId+"' and t.status not in (select max(id) from cm_code where code_type= 'DAILY_STATUS' and code_name ='已完成')  order by created_date asc ";
        List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
		 if(DataTypeUtil.validate(ListMap)){
        	for(Map<String, Object> map :ListMap){
        		String daily_id = DataTypeUtil.formatDbColumn(map.get("DAILY_ID"));
                String type = DataTypeUtil.formatDbColumn(map.get("TYPE"));
        		String name = DataTypeUtil.formatDbColumn(map.get("NAME"));
        		String status = DataTypeUtil.formatDbColumn(map.get("STATUS"));
        		String remark = DataTypeUtil.formatDbColumn(map.get("REMARK"));
        		String daily_link_id =DataTypeUtil.formatDbColumn(map.get("DAILY_LINK_ID"));
        		String bgColor="";
        		if(type.equals("1")){
        			bgColor = "color:red;";
        		}
         content = content+"   <tr style='height: 30px;"+bgColor+"'>"+
	                   "      <td width='*' align='left' style='border-bottom: 1px solid #cccbcc;'>&nbsp;"+name+"</td>"+
		               "      <td  style='width:100px;' align='center' class='TdClass'>&nbsp;"+status+"&nbsp;</td>"+                                                                                              
		               "      <td  style='width:400px;' align='center' class='TdClass'>"+GetStringById(daily_link_id,daily_id)+"&nbsp;</td>"+
		               "      <td  align='left' class='TdClass'>&nbsp;"+remark+"</td>"+
		        	   "   </tr>";		                 
	             }

             }
	}else if(moduleCode.equals("DS_JSCX_HZ")){
		String sql = "select wid,case when  created_date  > '"+max_time+"' then '1' else '0' end as type, jscx_id, sgxk_link_id, sgts_link_id, zjba_link_id, ajba_link_id, zjgh_link_id, sgzz_link_id, proj_id, remark, created_by, created_date, (select code_name from cm_code where id = t.name) name  from ds_jscx t where proj_id = '"+projId+"'  and status not in (select max(id) from cm_code where code_type= 'JSCX_STATUS' and code_name ='已完成') order by created_date asc ";
        List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
		 if(DataTypeUtil.validate(ListMap)){
        	for(Map<String, Object> map :ListMap){
        		String jscx_id = DataTypeUtil.formatDbColumn(map.get("JSCX_ID"));
        		String name = DataTypeUtil.formatDbColumn(map.get("NAME"));
        		String type = DataTypeUtil.formatDbColumn(map.get("TYPE"));
        		String sgxk_link_id =DataTypeUtil.formatDbColumn(map.get("SGXK_LINK_ID"));
        		String sgts_link_id =DataTypeUtil.formatDbColumn(map.get("SGTS_LINK_ID"));
        		String zjba_link_id =DataTypeUtil.formatDbColumn(map.get("ZJBA_LINK_ID"));
        		String ajba_link_id =DataTypeUtil.formatDbColumn(map.get("AJBA_LINK_ID"));
        		String zjgh_link_id =DataTypeUtil.formatDbColumn(map.get("ZJGH_LINK_ID"));
        		String sgzz_link_id =DataTypeUtil.formatDbColumn(map.get("SGZZ_LINK_ID"));
        		String bgColor="";
        		if(type.equals("1")){
        			bgColor = "color:red;";
        		}
        		content = content+"   <tr style='height: 30px;"+bgColor+"'>"+
	                   "      <td width='*' align='center' style='border-bottom: 1px solid #cccbcc;'>"+name+"</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(sgxk_link_id,jscx_id)+"&nbsp;</td>"+                                                                                              
		               "      <td  align='center' class='TdClass'>"+GetStringById(sgts_link_id,jscx_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(zjba_link_id,jscx_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(ajba_link_id,jscx_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(zjgh_link_id,jscx_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(sgzz_link_id,jscx_id)+"&nbsp;</td>"+
	            	   "   </tr>";		                 
	             }

             }
	}else if(moduleCode.equals("DS_WXY_HZ")){
		String sql = " select wid,case when  created_date  > '"+max_time+"' then '1' else '0' end as type, wxy_id, (select company_name from cm_vnmt   where vnmt_id = t.vnmt_id) vnmt_id, (select code_name from cm_code where id = t.name) name, scale, fasc_link_id, zjlz_link_id, jlxz_link_id, proj_id, plan_startdate, plan_enddate, (select code_name from cm_code where id = t.status) status, remark, deal_stage, created_date from ds_wxy t where t.proj_id = '"+projId+"'   and t.status not in (select max(id) from cm_code where code_type= 'WXY_STATUS' and code_name ='已完成')  order by created_date asc ";
        List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
		 if(DataTypeUtil.validate(ListMap)){
        	for(Map<String, Object> map :ListMap){
        		String wxy_id = DataTypeUtil.formatDbColumn(map.get("WXY_ID"));
        		String vnmt_id = DataTypeUtil.formatDbColumn(map.get("VNMT_ID"));
        		String name = DataTypeUtil.formatDbColumn(map.get("NAME"));
        		String type = DataTypeUtil.formatDbColumn(map.get("TYPE"));
        		String status = DataTypeUtil.formatDbColumn(map.get("STATUS"));
        		String deal_stage = DataTypeUtil.formatDbColumn(map.get("DEAL_STAGE"));
        		String remark = DataTypeUtil.formatDbColumn(map.get("REMARK"));
        		String fasc_link_id =DataTypeUtil.formatDbColumn(map.get("FASC_LINK_ID"));
        		String zjlz_link_id =DataTypeUtil.formatDbColumn(map.get("ZJLZ_LINK_ID"));
        		String jlxz_link_id =DataTypeUtil.formatDbColumn(map.get("JLXZ_LINK_ID"));
        		String bgColor="";
        		if(type.equals("1")){
        			bgColor = "color:red;";
        		}
         content = content+"   <tr style='height: 30px;"+bgColor+"'>"+
	                   "      <td width='*' align='left' style='border-bottom: 1px solid #cccbcc;'>&nbsp;"+vnmt_id+"</td>"+
		               "      <td  style='width:180px;' align='left' class='TdClass'>&nbsp;"+name+"&nbsp;</td>"+                                                                                              
		               "      <td  align='center' class='TdClass'>"+GetStringById(fasc_link_id,wxy_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(zjlz_link_id,wxy_id)+"&nbsp;</td>"+
		               "      <td  align='center' class='TdClass'>"+GetStringById(jlxz_link_id,wxy_id)+"&nbsp;</td>"+
		               "      <td  style='width:80px;' align='center' class='TdClass'>"+status+"&nbsp;</td>"+
		               "      <td  style='width:80px;' align='left' class='TdClass'>&nbsp;"+deal_stage+"&nbsp;</td>"+
		               "      <td  style='width:80px;' align='left' class='TdClass'>&nbsp;"+remark+"&nbsp;</td>"+
	            	   "   </tr>";		                 
	             }

             }
	}else if(moduleCode.equals("DS_XMSB_HZ")){
		String sql = "  select wid, xmsb_id,quantity,case when  created_date  > '"+max_time+"' then '1' else '0' end as type, (select code_name from cm_code where id = t.name) name, spec, install_dept, install_date, check_date_no, use_date_no, remove_date, proj_id, (select code_name from cm_code where id = t.status) status, remark, created_date, created_by, sb_link_id,sb_use_link_id,sb_remove_link_id,MAINTE_UNIT,DETECTION_UNIT,PROPERTY_DATE_NO from ds_xmsb t  where t.proj_id = '"+projId+"' order by created_date asc ";
        List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
		 if(DataTypeUtil.validate(ListMap)){
        	for(Map<String, Object> map :ListMap){
        		String xmsb_id = DataTypeUtil.formatDbColumn(map.get("XMSB_ID"));
                String type = DataTypeUtil.formatDbColumn(map.get("TYPE"));
        		String spec = DataTypeUtil.formatDbColumn(map.get("SPEC"));
        		String name = DataTypeUtil.formatDbColumn(map.get("NAME"));
        		String status = DataTypeUtil.formatDbColumn(map.get("STATUS"));
        		String remark = DataTypeUtil.formatDbColumn(map.get("REMARK"));
        		String quantity = DataTypeUtil.formatDbColumn(map.get("QUANTITY"));
        		String sb_link_id =DataTypeUtil.formatDbColumn(map.get("SB_LINK_ID"));
        		String sb_use_link_id =DataTypeUtil.formatDbColumn(map.get("SB_USE_LINK_ID"));
        		String sb_remove_link_id =DataTypeUtil.formatDbColumn(map.get("SB_REMOVE_LINK_ID"));
        		String mainteUnit =DataTypeUtil.formatDbColumn(map.get("MAINTE_UNIT"));
        		String detectionUnit =DataTypeUtil.formatDbColumn(map.get("DETECTION_UNIT"));
        		String propertyDateNo =DataTypeUtil.formatDbColumn(map.get("PROPERTY_DATE_NO"));
        		String installDept =DataTypeUtil.formatDbColumn(map.get("INSTALL_DEPT"));//安装单位
        		String installDate =DataTypeUtil.formatDbColumn(map.get("INSTALL_DATE"));//安装日期
        		String checkDateNo =DataTypeUtil.formatDbColumn(map.get("CHECK_DATE_NO"));//检查合格日期
        		String useDateNo =DataTypeUtil.formatDbColumn(map.get("USE_DATE_NO"));//使用登记日期/证号
        		String removeDate =DataTypeUtil.formatDbColumn(map.get("REMOVE_DATE"));//拆除日期
        		

        		
        		String bgColor="";
        		if(type.equals("1")){
        			bgColor = "color:red;";
        		}
         content = content+"<tr style='height: 30px;"+bgColor+"'>"+ //2110
	                   "      <td  align='left' style='border-bottom: 1px solid #cccbcc;width:120px'>&nbsp;"+name+"</td>"+
		               "      <td  style='width:80px;' align='center' class='TdClass'>&nbsp;"+status+"&nbsp;</td>"+                                                                                              
		               "      <td  style='width:200px;' align='left' class='TdClass'>&nbsp;"+spec+"</td>"+
		               "      <td  style='width:70px;' align='right' class='TdClass'>&nbsp;"+quantity+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='center' class='TdClass'>"+GetStringById(sb_link_id,xmsb_id)+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='center' class='TdClass'>"+GetStringById(sb_use_link_id,xmsb_id)+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='center' class='TdClass'>"+GetStringById(sb_remove_link_id,xmsb_id)+"&nbsp;</td>"+
		               "      <td  style='width:150px;' align='left' class='TdClass'>"+installDept+"&nbsp;</td>"+
		               "      <td  style='width:150px;' align='center' class='TdClass'>"+installDate+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='left' class='TdClass'>"+checkDateNo+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='left' class='TdClass'>"+useDateNo+"&nbsp;</td>"+
		               "      <td  style='width:120px;' align='center' class='TdClass'>"+removeDate+"&nbsp;</td>"+
		               "      <td  style='width:180px;' align='left' class='TdClass'>"+mainteUnit+"&nbsp;</td>"+
		               "      <td  style='width:180px;' align='left' class='TdClass'>"+detectionUnit+"&nbsp;</td>"+
		               "      <td  style='width:180px;' align='left' class='TdClass'>"+propertyDateNo+"&nbsp;</td>"+
		               "      <td  style='width:280px;' align='left' class='TdClass'>&nbsp;"+remark+"</td>"+
		        	   "   </tr>";		                 
	             }

             }
	}else if(moduleCode.equals("DS_ZDSG_HZ")){
		String sql = "   select wid,  case when  created_date  > '"+max_time+"' then '1' else '0' end as type, zdsg_id, (select actual_name  from cm_users where user_id = t.director) director, report_date, (select code_name from cm_code where id = t.deal_status) deal_status, report_detail, created_date, created_by, proj_id, deal_detail, sx_link_id, happen_date, location, dept, peopel_detail from ds_zdsg t   where t.proj_id = '"+projId+"'   and t.deal_status not in (select max(id) from cm_code where code_type= 'DEAL_STATUS' and code_name ='已完成') order by created_date asc ";
        List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
		 if(DataTypeUtil.validate(ListMap)){
        	for(Map<String, Object> map :ListMap){
        		String zdsg_id = DataTypeUtil.formatDbColumn(map.get("ZDSG_ID"));
                String type = DataTypeUtil.formatDbColumn(map.get("TYPE"));
        		String director = DataTypeUtil.formatDbColumn(map.get("DIRECTOR"));
        		String deal_status = DataTypeUtil.formatDbColumn(map.get("DEAL_STATUS"));
        		String report_detail = DataTypeUtil.formatDbColumn(map.get("REPORT_DETAIL"));
        		String report_date = DataTypeUtil.formatDbColumn(map.get("REPORT_DATE"));
        		String sx_link_id =DataTypeUtil.formatDbColumn(map.get("SX_LINK_ID"));
        		String deal_detail =DataTypeUtil.formatDbColumn(map.get("DEAL_DETAIL"));
        		String happen_date =DataTypeUtil.formatDbColumn(map.get("HAPPEN_DATE"));
        		String location =DataTypeUtil.formatDbColumn(map.get("LOCATION"));
        		String dept =DataTypeUtil.formatDbColumn(map.get("DEPT"));
        		String peopel_detail =DataTypeUtil.formatDbColumn(map.get("PEOPEL_DETAIL"));
        		String bgColor="";
        		if(type.equals("1")){
        			bgColor = "color:red;";
        		}
         content = content+"   <tr style='height: 30px;"+bgColor+"'>"+
	                   "      <td width='*' align='center' style='border-bottom: 1px solid #cccbcc;'>&nbsp;"+director+"</td>"+
		               "      <td  style='width:80px;' align='center' class='TdClass'>&nbsp;"+deal_status+"&nbsp;</td>"+  
		               "      <td  style='width:80px;' align='center' class='TdClass'>&nbsp;"+report_date+"&nbsp;</td>"+
		               "      <td  style='width:210px;' align='left' class='TdClass'>&nbsp;"+report_detail+"&nbsp;</td>"+
		               "      <td  style='width:80px;' align='center' class='TdClass'>&nbsp;"+happen_date+"&nbsp;</td>"+
		               "      <td  style='width:100px;' align='left' class='TdClass'>&nbsp;"+location+"&nbsp;</td>"+
		               "      <td  style='width:100px;' align='left' class='TdClass'>&nbsp;"+dept+"&nbsp;</td>"+
		               "      <td  style='width:100px;' align='left' class='TdClass'>&nbsp;"+peopel_detail+"&nbsp;</td>"+
		               "      <td  style='width:210px;' align='left' class='TdClass'>&nbsp;"+deal_detail+"</td>"+
		               "      <td  style='width:120px;' align='center' class='TdClass'>&nbsp;"+GetStringById(sx_link_id,zdsg_id)+"</td>"+
		        	   "   </tr>";		                 
	             }

             }
	}
	return content;
}

public   String GetStringById(String base_master_key ,String base_link_id){
	String res = "";
	String 	sql = "select doc_id,doc_number,title from km_doc where BASE_MASTER_KEY = '"+base_master_key+"' and (BASE_LINK_ID = '"+base_link_id+"' OR BASE_LINK_ID IS NULL OR BASE_LINK_ID='') and deleted_flag ='0'";
List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
 if(DataTypeUtil.validate(ListMap)){
   	for(Map<String, Object> map :ListMap){
   		String docId = DataTypeUtil.formatDbColumn(map.get("DOC_ID"));
   		String doc_number = DataTypeUtil.formatDbColumn(map.get("DOC_NUMBER"));
   		String title = DataTypeUtil.formatDbColumn(map.get("TITLE"));
   		res = res+"<img class='DocClass' onMouseOver=\"className='ss1'\" onMouseOut=\"className='ss2'\" border=\"0\" src=\""+AppSetting.PROJECT_NAME+"/img/button/bg_fileview.gif\" style=\"cursor: pointer\" onclick=\"openw('" + docId + "')\" title='"+doc_number+" "+title+"'>&nbsp;"; //
   	}
 }
return res;

	
}

@Override
public String DsLdzlIndex(String ds_xmjz_hz_select_id, String ds_jscx_hz_select_id,
		String ds_wxy_hz_select_id, String ds_xmsb_hz_select_id,
		String ds_daily_hz_select_id, String ds_zdsg_hz_select_id, UserInfo userInfo ,String groupId) {
	String where ="";
	String projId = "";
	String projName = "";
	String reStr = "";
	String module_code="";
	String open_time="";
	String ignore_time="";
	String openClass="buttonClass";
	String ignoreClass="buttonClass";
	String max_time="";
	
	if(DataTypeUtil.validate(ds_xmjz_hz_select_id)){
		where = where + "AND PROJ_CMPT_PCT ='"+ds_xmjz_hz_select_id+"'";
	}
	if(DataTypeUtil.validate(ds_jscx_hz_select_id)){
		where =where +"AND PROJ_ID NOT IN (select j.proj_id from ds_jscx j where  (select count(wid) from  km_doc where deleted_flag ='0' and (base_link_id = j.jscx_id  or base_link_id is null ) and base_master_key = j."+ds_jscx_hz_select_id+") >0  )";
	}
	if(DataTypeUtil.validate(ds_wxy_hz_select_id)){
		where = where + "AND PROJ_ID IN (SELECT PROJ_ID FROM ds_wxy where NAME in (select id from cm_code where code_name = '" +ds_wxy_hz_select_id+"' and  CODE_TYPE='WXY_NAME' )and  status  not in (select id from cm_code where  code_name in ('已完成','未开始','未施工')))";												
	}
	if(DataTypeUtil.validate(ds_xmsb_hz_select_id)){
		where = where + "AND PROJ_ID IN (SELECT PROJ_ID FROM ds_xmsb where STATUS='"+ds_xmsb_hz_select_id+"')";								
	}
	if(DataTypeUtil.validate(ds_daily_hz_select_id)){
		where = where + "AND PROJ_ID IN (SELECT PROJ_ID FROM ds_daily where NAME in (select id from cm_code where code_name = '"+ds_daily_hz_select_id+"' and  CODE_TYPE='DAILY_NAME' ) and  status  not in (select id from cm_code where  code_name in ('已完成','未开始','未施工')) )";				
	}
	if(DataTypeUtil.validate(ds_zdsg_hz_select_id)){
		where = where + "AND PROJ_ID IN (SELECT PROJ_ID FROM ds_zdsg where DEAL_STATUS='"+ds_zdsg_hz_select_id+"'  and deal_status  not in (select id from cm_code where  code_name in ('已完成','未开始','未施工') ))";
	}
 
		String userId = userInfo.getUserId();
		List<Map<String, Object>>  ldzlListMap =  baseJdbcDao.queryListMap("SELECT MODULE_CODE,MODULE_NAME FROM CM_MODULE WHERE  ENABLED='1' AND PARENT_MODULE_CODE = 'DS_LDZL' AND MODULE_CODE !='DS_LDZL_SHOW' AND (MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE USER_ID='"+userId+"') OR MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+userId+"'))) ORDER BY SEQ_NUM");
	
	String sql = " select proj_id,concat(concat(eps_name,' -- '),proj_name) proj_name from ( select proj_id  ,proj_name ,(select proj_name from cm_proj  where proj_id = t.parent_proj_id) eps_name from cm_proj t  where PROJ_NODE_FLAG='Y' "+where+" and IS_ZJLR_FLAG ='1'   and (CONTRACT_TITLE='未完工' or CONTRACT_TITLE is null) and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"'))) tt order by eps_name ";
    List<Map<String, Object>>  ListMap = baseJdbcDao.queryListMap(sql);
	 if(DataTypeUtil.validate(ListMap)){
    	for(Map<String, Object> map :ListMap){ 
    		projName = DataTypeUtil.formatDbColumn(map.get("PROJ_NAME"));
    		projId = DataTypeUtil.formatDbColumn(map.get("PROJ_ID"));
    		reStr = reStr+" <tr style='height: 30px;'> "+
            	   "   <td  class='tdProjClass'>"+projName+"</td>";
             for(Map<String, Object> ldzlmap :ldzlListMap){
               module_code = DataTypeUtil.formatDbColumn(ldzlmap.get("MODULE_CODE"));
/*		               if(DBUtil.getResultCountBySql(conn, "select wid from  ds_log_hz t where proj_id = '"+projId+"' and type = 'IGNORE' and module_code='"+module_code+"' and group_id = '"+groupId+"'")>0){
            	   ignoreClass = "buttonClass_read";
               }else{
            	   ignoreClass = "buttonClass";
               }*/
/*		               if(DBUtil.getResultCountBySql(conn, "select wid from  ds_log_hz t where proj_id = '"+projId+"' and type = 'OPEN' and module_code='"+module_code+"' and group_id = '"+groupId+"'")>0){
            	   openClass = "buttonClass_read";
               }else{*/
        		ignore_time = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(created_time) created_time from ds_log_hz t where proj_id = '"+projId+"' and type = 'IGNORE' and user_id = '"+userId+"' and module_code='"+module_code+"'"));
        		open_time = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(created_time)  created_time from ds_log_hz t where proj_id = '"+projId+"' and type = 'OPEN' and user_id = '"+userId+"' and module_code='"+module_code+"' "));
                max_time =  open_time;
                if(!DataTypeUtil.validate(max_time)){
            	    max_time = "2004-06-10 10:55:17";
                }
            	   if(!module_code.equals("DS_XMJZ_HZ")){
            		   String columeStatus="";
            		   if(module_code.equals("DS_JSCX_HZ")||module_code.equals("DS_WXY_HZ")||module_code.equals("DS_DAILY_HZ")){
                          columeStatus = "and status  not in (select id from cm_code where  code_name in ('已完成','未开始','未施工') )";
            		   }else if(module_code.equals("DS_ZDSG_HZ")){
                    	   columeStatus="and deal_status  not in (select id from cm_code where  code_name in ('已完成','未开始','未施工') )";
                       }
	            	   if(baseJdbcDao.getCount("select count(wid) num from "+module_code.replaceAll("_HZ", "")+"  where date_format(created_date,'%Y-%m-%d %H:%i:%s' ) > '"+max_time+"' and proj_id = '"+projId+"' "+columeStatus)>0){
	            		   openClass = "buttonClass_new";
	            	   }else{
	            		   openClass = "buttonClass"; 
	            	   }
            	   }else{
            		   
	            	   if(baseJdbcDao.getCount("select count(wid) num from plan_task  where date_format(create_date,'%Y-%m-%d %H:%i:%s' ) > '"+max_time+"' and proj_id = '"+projId+"'")>0){
	            		   openClass = "buttonClass_new";
	            	   }else{
	            		   openClass = "buttonClass"; 
	            	   }
            	   }
            	   
              // }
            	   open_time =  !open_time.equals("")?open_time.substring(0, 10):open_time;
            	   ignore_time =  !ignore_time.equals("")?ignore_time.substring(0, 10):ignore_time;
     reStr = reStr+"   <td class='tdClass'>"+
                   "      <div style='width: 100%;height: 30px;float: left;'>"+
	               "         <div id='"+module_code+"_OPEN'   class='"+openClass+"' onclick=OpenDetal(this,'"+projId+"','"+module_code+"','"+max_time.replace(" ", "X")+"') >进入 </div>"+                                                                                              
	               "         <div id='"+module_code+"_IGNORE'   class='"+ignoreClass+"' onclick=IgnoreDetal(this,'"+projId+"','"+module_code+"')>忽略</div>"+     
	               "      </div>"+
                   "      <div style='width: 100%;height: 20px;float: left;'>"+
                   "         <div id='"+projId+"_"+module_code+"_OPEN_DATE' class='divTime'>"+ open_time+"&nbsp;</div>"+
                   "         <div id='"+projId+"_"+module_code+"_IGNORE_DATE' class='divTime' >"+ignore_time+"&nbsp;</div>"+
                   "      </div>"+
            	   "   </td>";		                 
             }
   
     reStr = reStr+     "  </tr>";
         }
    }
   return reStr;
    
}

@Override
public List<Map<String, Object>> LdzlModuleByUserId(String userId) {
	
 	return baseJdbcDao.queryListMap("SELECT MODULE_CODE,MODULE_NAME FROM CM_MODULE WHERE  ENABLED='1' AND PARENT_MODULE_CODE = 'DS_LDZL' AND MODULE_CODE !='DS_LDZL_SHOW' AND (MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE USER_ID='"+userId+"') OR MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+userId+"'))) ORDER BY SEQ_NUM");

}

@Override
public List<Map<String, Object>> ZjlrModuleByUserId(String userId) {
	
 	return baseJdbcDao.queryListMap("  SELECT MODULE_CODE,MODULE_NAME   FROM CM_MODULE WHERE  ENABLED='1' AND PARENT_MODULE_CODE = 'DS_ZJLR' AND MODULE_CODE !='DS_ZJLR_INDEX' AND (MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE USER_ID='"+userId+"') OR MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+userId+"'))) ORDER BY SEQ_NUM");

}

@Override
public String CheckUser(String user_id) {
	String reStr = "";
	 String sql = "select count(wid) num    from CM_PROJ   where PROJ_NODE_FLAG='Y' and IS_ZJLR_FLAG ='1'   and (CONTRACT_TITLE='未完工' or CONTRACT_TITLE is null) and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+user_id+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+user_id+"')) order by proj_name ";
	if(baseJdbcDao.getCount(sql)==0){
		reStr="0";	
		return reStr;
	}
	 sql ="  SELECT count(MODULE_CODE) num  FROM CM_MODULE WHERE  ENABLED='1' AND PARENT_MODULE_CODE = 'DS_ZJLR' AND MODULE_CODE !='DS_ZJLR_INDEX' AND (MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE USER_ID='"+user_id+"') OR MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+user_id+"'))) ORDER BY SEQ_NUM";
	 if(baseJdbcDao.getCount(sql)>0){
	   	reStr="1"; 
	  	return reStr;
    }
    sql =" SELECT count(MODULE_CODE) num FROM CM_MODULE WHERE  ENABLED='1' AND PARENT_MODULE_CODE = 'DS_LDZL' AND MODULE_CODE !='DS_LDZL_SHOW' AND (MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE USER_ID='"+user_id+"') OR MODULE_CODE IN (SELECT MODULE_CODE FROM CM_USERMDL WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+user_id+"'))) ORDER BY SEQ_NUM";
    if(baseJdbcDao.getCount(sql)>0){
     	reStr="2"; 
     	return reStr;
    }
    return reStr;
}

@Override
public List<Map<String, Object>> ProjPlanTaskCodeId(String userId,String parentId,String startdate, String enddate, String codeId, String key,String nodeType, int start, int number) {
	    String where = "";
	    String CodeSql="";
		if(DataTypeUtil.validate(codeId)){ 
			CodeSql = "SELECT '"+codeId+"'";
	       String sql="SELECT CODE_SHORT_NAME FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE_HZ' AND ID='"+codeId+"'";
	       String CODE_SHORT_NAME = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
	       if(DataTypeUtil.validate(CODE_SHORT_NAME)){
	    	   CodeSql="SELECT ID FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE' AND CODE_SHORT_NAME IN ('"+CODE_SHORT_NAME.replaceAll(",", "','")+"')";
	       }
		}
		if(DataTypeUtil.validate(startdate)){
			where = " and  ( date_format(created_date,'%Y-%m-%d')  > '"+startdate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+startdate+"')  ";
		}
		if(DataTypeUtil.validate(enddate)){
			where = where +  " and  ( date_format(created_date,'%Y-%m-%d')  < '"+enddate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+enddate+"')  ";		
		}	
		String keyWhere="";
		if(DataTypeUtil.validate(key)){
			keyWhere = "AND (PROJ_SHORT_NAME LIKE '%"+key+"%'  OR PROJ_NAME LIKE '%"+key+"%') ";
		}
		String sql ="";
		List<Map<String, Object>>  items = new ArrayList<Map<String, Object>>();
		if(parentId.equals("0")){
			    sql = "select PROJ_ID ID ,'"+nodeType+"' NODE_TYPE,CONCAT(PROJ_SHORT_NAME,'--',PROJ_NAME) PROJ_NAME,DOC_COUNT from ("
					+ "SELECT proj_id ,"
					+ "(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID  = PP.PROJ_ID )PROJ_NAME,"
					+ "(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID  = PP.PROJ_ID )PROJ_SHORT_NAME,"
					+ "SUM(DOC_COUNT) DOC_COUNT "
					+ "FROM ("
					+ "SELECT PLAN_ID, PROJ_ID, MODULE_CODE, "
					+ "(SELECT COUNT(*) FROM KM_DOC WHERE"
					+ " BASE_MASTER_KEY = TT.PLAN_ID  AND STATUS='APPROVED' "
					+ "AND DELETED_FLAG = '0' AND PROJ_ID = TT.PROJ_ID "+where+" ) DOC_COUNT "
					+ "FROM ("
					+ "SELECT PROJ_ID,PLAN_ID,'DS_PLAN' MODULE_CODE  FROM  DS_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+"  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,'SM_PLAN' MODULE_CODE  FROM  SM_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,'SM_TEST' MODULE_CODE  FROM  SM_TEST WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,'SYS_PLAN' MODULE_CODE  FROM  SYS_PLAN WHERE CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL"
					+ ") TT "
					+ ") PP GROUP BY  PP.PROJ_ID"
					+ ") PO  ORDER BY PROJ_SHORT_NAME ASC LImit  "+start+","+ number;			
				 items =  baseJdbcDao.queryListMap(sql);
				 for (Map<String, Object> item : items) {
				  
				        item.put("state", "closed");
				        
				        item.put("iconCls", "icon-proj");  	    
			     } 
		}else if(nodeType.equals("PROJ")&& !parentId.equals("0")){
		  sql = "select '' PROJ_NAME,CONCAT(CODE_ID,'_"+parentId+"') ID ,'CODEID' NODE_TYPE,CONCAT(CODE_SHORT_NAME,'--',CODE_NAME) CODE_NAME,DOC_COUNT from ("
					+ "SELECT CODE_ID ,"
					+ "(SELECT CODE_NAME FROM CM_CODE WHERE ID  = PP.CODE_ID )CODE_NAME,"
					+ "(SELECT CODE_SHORT_NAME FROM CM_CODE WHERE ID  = PP.CODE_ID )CODE_SHORT_NAME,"
					+ "SUM(DOC_COUNT) DOC_COUNT "
					+ "FROM ("
					+ "SELECT PLAN_ID, PROJ_ID, MODULE_CODE,CODE_ID, "
					+ "(SELECT COUNT(*) FROM KM_DOC WHERE"
					+ " BASE_MASTER_KEY = TT.PLAN_ID AND STATUS='APPROVED' "
					+ "AND DELETED_FLAG = '0' AND PROJ_ID = TT.PROJ_ID "+where+" ) DOC_COUNT "
					+ "FROM ("
					+ "SELECT PROJ_ID,PLAN_ID,CODE_ID,'DS_PLAN' MODULE_CODE  FROM  DS_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+parentId+"'  AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,CODE_ID,'SM_PLAN' MODULE_CODE  FROM  SM_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+parentId+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,CODE_ID,'SM_TEST' MODULE_CODE  FROM  SM_TEST WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+parentId+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PROJ_ID,PLAN_ID,CODE_ID,'SYS_PLAN' MODULE_CODE  FROM  SYS_PLAN WHERE CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+parentId+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL"
					+ ") TT "
					+ ") PP GROUP BY  PP.CODE_ID"
					+ ") PO ORDER BY CODE_SHORT_NAME";
		     items =  baseJdbcDao.queryListMap(sql);
			 for (Map<String, Object> item : items) {
			  
			        item.put("state", "closed");
			        
			        item.put("iconCls", "icon-wbs");  	    
		     } 
		}else if(nodeType.equals("CODEID")&& !parentId.equals("0")){
			       sql = "SELECT PLAN_ID ID,'' PROJ_NAME,CONCAT(PLAN_SHORT_NAME,'--',PLAN_NAME)PLAN_NAME,'TASK' NODE_TYPE,"
						+ "PROJ_ID,"
						+ " MODULE_CODE,"
						+ "CODE_ID, "
						+ "(SELECT COUNT(*) FROM KM_DOC WHERE"
						+ " BASE_MASTER_KEY = TT.PLAN_ID  AND STATUS='APPROVED' "
						+ "AND DELETED_FLAG = '0' AND PROJ_ID = TT.PROJ_ID "+where+" ) DOC_COUNT "
						+ "FROM ("
						+ "SELECT PROJ_ID,PLAN_ID,PLAN_NAME,PLAN_SHORT_NAME,CODE_ID,'DS_PLAN' MODULE_CODE  FROM  DS_PLAN WHERE   CODE_ID ='"+parentId.split("_")[0]+"'  AND PROJ_ID ='"+parentId.split("_")[1]+"'  AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PROJ_ID,PLAN_ID,PLAN_NAME,PLAN_SHORT_NAME,CODE_ID,'SM_PLAN' MODULE_CODE  FROM  SM_PLAN WHERE   CODE_ID ='"+parentId.split("_")[0]+"'  AND PROJ_ID ='"+parentId.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PROJ_ID,PLAN_ID,PLAN_NAME,PLAN_SHORT_NAME,CODE_ID,'SM_TEST' MODULE_CODE  FROM  SM_TEST WHERE   CODE_ID ='"+parentId.split("_")[0]+"'  AND PROJ_ID ='"+parentId.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PROJ_ID,PLAN_ID,PLAN_NAME,PLAN_SHORT_NAME,CODE_ID,'SYS_PLAN' MODULE_CODE  FROM  SYS_PLAN WHERE CODE_ID ='"+parentId.split("_")[0]+"'  AND PROJ_ID ='"+parentId.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL"
						+ ") TT ORDER BY PLAN_SHORT_NAME " ;
			     items =  baseJdbcDao.queryListMap(sql);
				 for (Map<String, Object> item : items) {
				  
				    //    item.put("state", "closed");
				        
				        item.put("iconCls", "icon-task");  	    
			     } 
			}
	 return items;
}

@Override
public Object getProjPlanTaskCodeIdCount(String userId,String parentId, String startdate,
		String enddate, String codeId, String key, String nodeType) {
	String where = "";
	String CodeSql="";
	if(DataTypeUtil.validate(codeId)){
		CodeSql = "SELECT '"+codeId+"'";
       String sql="SELECT CODE_SHORT_NAME FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE_HZ' AND ID='"+codeId+"'";
       String CODE_SHORT_NAME = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
       if(DataTypeUtil.validate(CODE_SHORT_NAME)){
    	   CodeSql="SELECT ID FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE' AND CODE_SHORT_NAME IN ('"+CODE_SHORT_NAME.replaceAll(",", "','")+"')";
       }
	}
	if(DataTypeUtil.validate(startdate)){
		where = " and  ( date_format(created_date,'%Y-%m-%d')  > '"+startdate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+startdate+"')  ";
	}
	if(DataTypeUtil.validate(enddate)){
		where = where +  " and  ( date_format(created_date,'%Y-%m-%d')  < '"+enddate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+enddate+"')  ";		
	}	
	String keyWhere="";
	if(DataTypeUtil.validate(key)){
		keyWhere = "AND (PROJ_SHORT_NAME LIKE '%"+key+"%'  OR PROJ_NAME LIKE '%"+key+"%') ";
	}
	
	String sql = "select count(*)NUM from ("
			+ "SELECT proj_id ,"
			+ "(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID  = PP.PROJ_ID )PROJ_NAME,"
			+ "(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID  = PP.PROJ_ID )PROJ_SHORT_NAME,"
			+ "SUM(DOC_COUNT) "
			+ "FROM ("
			+ "SELECT PLAN_ID, PROJ_ID, MODULE_CODE, "
			+ "(SELECT COUNT(*) FROM KM_DOC WHERE"
			+ " BASE_MASTER_KEY = TT.PLAN_ID  AND STATUS='APPROVED' "
			+ "AND DELETED_FLAG = '0' AND PROJ_ID = TT.PROJ_ID "+where+" ) DOC_COUNT "
			+ "FROM ("
			+ "SELECT PROJ_ID,PLAN_ID,'DS_PLAN' MODULE_CODE  FROM  DS_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+"  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
			+ "UNION "
			+ "SELECT PROJ_ID,PLAN_ID,'SM_PLAN' MODULE_CODE  FROM  SM_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
			+ "UNION "
			+ "SELECT PROJ_ID,PLAN_ID,'SM_TEST' MODULE_CODE  FROM  SM_TEST WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL "
			+ "UNION "
			+ "SELECT PROJ_ID,PLAN_ID,'SYS_PLAN' MODULE_CODE  FROM  SYS_PLAN WHERE CODE_ID IN ("+CodeSql+")  AND PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y'  AND CONTRACT_TITLE = '未完工' "+keyWhere+" AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) )AND CODE_ID !='' AND CODE_ID IS NOT NULL"
			+ ") TT "
			+ ") PP GROUP BY  PP.PROJ_ID"
			+ ") PO   ";
			
    return baseJdbcDao.getCount(sql);
}

@Override
public List<Map<String, Object>> ShowDocDetail(String id, String startdate,String enddate, String codeId, String nodeType) {
	    String where = "";
	    String CodeSql="";
		if(DataTypeUtil.validate(codeId)){
			CodeSql = "SELECT '"+codeId+"'";
	       String sql="SELECT CODE_SHORT_NAME FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE_HZ' AND ID='"+codeId+"'";
	       String CODE_SHORT_NAME = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
	       if(DataTypeUtil.validate(CODE_SHORT_NAME)){
	    	   CodeSql="SELECT ID FROM CM_CODE WHERE CODE_TYPE ='PLAN_CODE' AND CODE_SHORT_NAME IN ('"+CODE_SHORT_NAME.replaceAll(",", "','")+"')";
	       }
		}
		if(DataTypeUtil.validate(startdate)){
			where = " and  ( date_format(created_date,'%Y-%m-%d')  > '"+startdate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+startdate+"')  ";
		}
		if(DataTypeUtil.validate(enddate)){
			where = where +  " and  ( date_format(created_date,'%Y-%m-%d')  < '"+enddate+"' or   date_format(created_date,'%Y-%m-%d')  = '"+enddate+"')  ";		
		}
		String sql="";
		if(nodeType.equals("PROJ")){
			sql="SELECT '' IMG, K.DOC_ID,DOC_NUMBER,TITLE,FILE_NAME,DATE_FORMAT(K.CREATED_DATE,'%Y-%m-%d') CREATED_DATE,  (SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID = CREATED_BY) AS CREATED_BY , K.DOC_GRADE, K.PROJ_ID, K.FOLDER_ID, K.FILE_NAME FROM KM_DOC K  WHERE 1 = 1  AND BASE_MASTER_KEY IN ("
					+ "SELECT PLAN_ID  FROM  DS_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+id+"'  AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PLAN_ID  FROM  SM_PLAN WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+id+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PLAN_ID  FROM  SM_TEST WHERE   CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+id+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
					+ "UNION "
					+ "SELECT PLAN_ID  FROM  SYS_PLAN WHERE CODE_ID IN ("+CodeSql+")  AND PROJ_ID ='"+id+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL"
					+ ") "+where+"  AND K.DOC_GRADE = '2' AND PROJ_ID= '"+id+"'     ORDER BY K.DOC_NUMBER ASC ";
		}else if(nodeType.equals("CODEID")){
			sql="SELECT '' IMG, K.DOC_ID,DOC_NUMBER,TITLE,FILE_NAME,DATE_FORMAT(K.CREATED_DATE,'%Y-%m-%d') CREATED_DATE,  (SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID = CREATED_BY) AS CREATED_BY , K.DOC_GRADE, K.PROJ_ID, K.FOLDER_ID, K.FILE_NAME FROM KM_DOC K  WHERE 1 = 1  AND BASE_MASTER_KEY IN ("
						+ "SELECT PLAN_ID  FROM  DS_PLAN WHERE   CODE_ID ='"+id.split("_")[0]+"'  AND PROJ_ID ='"+id.split("_")[1]+"'  AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PLAN_ID  FROM  SM_PLAN WHERE   CODE_ID ='"+id.split("_")[0]+"'  AND PROJ_ID ='"+id.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PLAN_ID  FROM  SM_TEST WHERE   CODE_ID ='"+id.split("_")[0]+"'  AND PROJ_ID ='"+id.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL "
						+ "UNION "
						+ "SELECT PLAN_ID  FROM  SYS_PLAN WHERE  CODE_ID ='"+id.split("_")[0]+"'  AND PROJ_ID ='"+id.split("_")[1]+"'AND CODE_ID !='' AND CODE_ID IS NOT NULL"
						+ ")  "+where+"  AND K.DOC_GRADE = '2' AND PROJ_ID= '"+id.split("_")[1]+"'      ORDER BY  K.DOC_NUMBER ASC ";
		}else if(nodeType.equals("TASK")){
			sql="SELECT '' IMG, K.DOC_ID,DOC_NUMBER,TITLE,FILE_NAME,DATE_FORMAT(K.CREATED_DATE,'%Y-%m-%d') CREATED_DATE,  (SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID = CREATED_BY) AS CREATED_BY , K.DOC_GRADE, K.PROJ_ID, K.FOLDER_ID, K.FILE_NAME FROM KM_DOC K  WHERE 1 = 1  AND BASE_MASTER_KEY ='"+id+"'  "+where+"  AND K.DOC_GRADE = '2'        ORDER BY  K.DOC_NUMBER ASC ";
		}
	    List<Map<String, Object>> list =  baseJdbcDao.queryListMap(sql);		   
        if(DataTypeUtil.validate(list)){
    	   for(Map<String,Object> map:list){ 
    		   String img = getFileImage(DataTypeUtil.formatDbColumn(map.get("FILE_NAME")));
    		   map.put("IMG", "<span style='width:17px' class='"+img+"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
    	   }
	    }
	return list;
}

public static String getFileImage(String paramString)
{
  String str1 = "";
  String str2 = "icon-others";
  if ( DataTypeUtil.validate(paramString) && (paramString.indexOf(".") >= 0))
  {
    str1 = paramString.substring(paramString.lastIndexOf(".") + 1);
    if ((str1.equalsIgnoreCase("doc")) || (str1.equalsIgnoreCase("docx")))
      str2 = "icon-word";
    else if (str1.equalsIgnoreCase("xls") || (str1.equalsIgnoreCase("xlsx")))
      str2 = "icon-excel";
    else if (str1.equalsIgnoreCase("ppt"))
      str2 = "icon-ppt";
    else if (str1.equalsIgnoreCase("txt"))
      str2 = "icon-txt";
    else if (str1.equalsIgnoreCase("dwg"))
      str2 = "icon-dwg";
    else if ((str1.equalsIgnoreCase("gif")) || (str1.equalsIgnoreCase("jpg")) || (str1.equalsIgnoreCase("png")) || (str1.equalsIgnoreCase("bmp")))
      str2 = "icon-gif";
    else if (str1.equalsIgnoreCase("pdf"))
      str2 = "icon-pdf";
    else if ((str1.equalsIgnoreCase("zip")) || (str1.equalsIgnoreCase("rar")))
      str2 = "icon-zip";
  }
  return str2;
}

 
 
   
} 