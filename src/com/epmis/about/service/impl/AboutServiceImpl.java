package com.epmis.about.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.about.service.AboutService;
import com.epmis.about.vo.About;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("AboutsService")
public class AboutServiceImpl implements AboutService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_about where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}
	//处理文本编辑器斜杠，拼接处理代码
			public static String replaceAll(String str,String in,String out){
				int index = 0;
				int bakindex = -1;
				while((index = str.indexOf(in,index)) != -1 && index > bakindex){
					bakindex = index;
					index++;
					str = str.substring(0,index - 1) + out + str.substring(index+in.length() - 1,str.length());
				}
				return str;
			}

	@Override
	public String update(About about) {
//		String sqlwhere="";
//		if(about.getPic()==null || about.getPic()==""){
//			sqlwhere="";
//		}else{
//			sqlwhere= " pic='"+about.getPic()+"', ";
//		}
		
		String result2 = replaceAll(about.getContent(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_about set "
				+ "content='"+result2+"', "	
				+ "servicephone='"+about.getServicephone()+"', "
				+ "weixinname='"+about.getWeixinname()+"', "
//						+ sqlwhere
//						+ " xy_content='"+result2+"', "
						+ "url='"+about.getUrl()+"' "						
				 +" where id='"+about.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}

}
