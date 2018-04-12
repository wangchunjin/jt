package com.epmis.al.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.al.service.OtherService;
import com.epmis.al.vo.Other;
import com.epmis.sys.dao.BaseJdbcDao;


@Transactional
@Service("otherService")
public class OtherServiceImpl implements OtherService{

	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Override
	public List<Map<String, Object>> showOther(int page, int rows,String creattime,String hyly,String rzpc,String szdq, int hid,int rid,int sid) {
		int start = (page-1)*rows;
		int end =page*rows;
		String where ="";
			if (hid != 0) {
				where += " and o.hyly = "+hid+"";
				
			}
			if(rid != 0){
				where += " and o.rzpc ="+rid+"";
				
			}
			if(sid != 0){
				where += " and o.szdq ="+sid+"";
			}
			if(hid != 0 && rid != 0 && sid != 0){
				where += " and o.hyly = "+hid+" and o.rzpc ="+rid+" and o.szdq ="+sid+"";
			}
			String sql ="SELECT o.id,o.img,o.title,o.introduce,o.content,o.hyly,o.rzpc,o.szdq,"
					
				+ "DATE_FORMAT(from_unixtime(o.creattime),'%Y-%m-%d ') creattime,t.serviceName "
				
				+ "FROM t_other_finance AS o INNER JOIN t_servicetype AS t ON o.rzpc = t.id  where 1 = 1 "+where+"";
			
				sql +=  " ORDER BY creattime DESC LIMIT "+start+","+end;
				
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String addOther(Other other,String content) {
		try {
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			other.setCreattime(format.format(new Date()));
			String time = other.getCreattime();  
			Date date = (Date) format.parse(time);  
			other.setCreattime(String.valueOf(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if(this.baseJdbcDao.getCount("select count(id) from t_other_finance where title = '"+other.getTitle()+"'") > 0){
			return "该标题已存在";
		}
		/*
		 * 1
		 */
		String string = null;
		String sql1 = "select serviceName from t_servicetype where id = '"+other.getHyly()+"'";
		Map<String, Object> map1 = this.baseJdbcDao.queryMap(sql1);
		Set<String> keSet1=map1.keySet();  
        for (Iterator<String> iterator = keSet1.iterator(); iterator.hasNext();) {  
            string = iterator.next();  
        }
        String name1 = (String) map1.get(string);
        /*
		 * 2
		 */
		String sql2 = "select serviceName from t_servicetype where id = '"+other.getRzpc()+"'";
		Map<String, Object> map2 = this.baseJdbcDao.queryMap(sql2);
		Set<String> keSet2=map2.keySet();  
        for (Iterator<String> iterator = keSet2.iterator(); iterator.hasNext();) {  
            string = iterator.next();  
        } 
        String name2 = (String) map2.get(string);
        /*
		 * 3
		 */
		String sql3 = "select serviceName from t_servicetype where id = '"+other.getSzdq()+"'";
		Map<String, Object> map3 = this.baseJdbcDao.queryMap(sql3);
		Set<String> keSet3=map3.keySet();  
        for (Iterator<String> iterator = keSet3.iterator(); iterator.hasNext();) {  
            string = iterator.next();  
        } 
        String name3 = (String) map3.get(string);
		String sql ="INSERT INTO t_other_finance(img,title,introduce,content,hyly,rzpc,szdq,label,hylyname,rzpcname,szdqname,creattime) VALUES("
				+ "'"+other.getImg()+"',"
				+ "'"+other.getTitle()+"',"
				+ "'"+other.getIntroduce()+"',"
				+ "'"+other.getContent()+"',"
				+ "'"+other.getHyly()+"',"
				+ "'"+other.getRzpc()+"',"
				+ "'"+other.getSzdq()+"',"
				+ "'"+other.getLabel()+"',"
				+ "'"+name1+"',"
				+ "'"+name2+"',"
				+ "'"+name3+"',"
				+ other.getCreattime().substring(0,10) +")";
			this.baseJdbcDao.insert(sql);
		return "success";
	}


	@Override
	public String delOther(String id) {
		String sql ="DELETE FROM t_other_finance WHERE id ='"+id+"'";
		this.baseJdbcDao.delete(sql);
		return "success";
	}

	@Override
	public String updateOther(Other other,String content) {
		String sql="UPDATE t_other_finance SET title='"+other.getTitle()+"',"
				+ "img = '"+other.getImg()+"',"
				+ "introduce = '"+other.getIntroduce()+"',"
				+ "hyly = '"+other.getHyly()+"',"
				+ "rzpc = '"+other.getRzpc()+"',"
				+ "szdq = '"+other.getSzdq()+"',"
				+ "label = '"+other.getLabel()+"',"
				+ "content = '"+other.getContent()+"'"
				+ " WHERE id='"+other.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

	@Override
	public Map<String, Object> OtherById(String id) {
		String sql ="SELECT id,img,title,introduce,hyly,rzpc,szdq,label,content,creattime FROM t_other_finance WHERE id ='"+id+"'";
		Map<String, Object> map = this.baseJdbcDao.queryMap(sql);
		return map;
	}

	@Override
	public int getOtherCount() {
		String sql ="SELECT COUNT(1) FROM t_other_finance ";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> showHylyName() {
		return this.baseJdbcDao.queryListMap("select id,serviceName from t_servicetype where paterid=10;");
	}

	@Override
	public List<Map<String, Object>> showRzPcName() {
		return this.baseJdbcDao.queryListMap("select id,serviceName from t_servicetype where paterid=11;");
	}

	@Override
	public List<Map<String, Object>> showSzDqName() {
		return this.baseJdbcDao.queryListMap("select id,serviceName from t_servicetype where paterid=12;");
	}

	@Override
	public String SelOther(Other other, String content) {
		String sql="UPDATE t_other_finance SET title='"+other.getTitle()+"',"
				+ "img = '"+other.getImg()+"',"
				+ "introduce = '"+other.getIntroduce()+"',"
				+ "hyly = '"+other.getHyly()+"',"
				+ "rzpc = '"+other.getRzpc()+"',"
				+ "szdq = '"+other.getSzdq()+"',"
				+ "label = '"+other.getLabel()+"',"
				+ "content = '"+other.getContent()+"'"
				+ " WHERE id='"+other.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
