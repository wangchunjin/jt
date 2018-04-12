package com.epmis.jt.agree.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.agree.service.AgreeService;
import com.epmis.jt.agree.vo.Agree;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("agreesService")
public class AgreeServiceImpl implements AgreeService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_agree where id='"+id+"'";
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
	public String updateReg(Agree agree) {

		
		String result2 = replaceAll(agree.getRegister_content(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_agree set "
				+ "register_content='"+result2+"' "										
				 +" where id='"+agree.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}
	
	@Override
	public String updateLoan(Agree agree) {		
		String result2 = replaceAll(agree.getLoan_content(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_agree set "
				+ "loan_content='"+result2+"' "
				 +" where id='"+agree.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}
	@Override
	public String updateInsurance(Agree agree) {
		String result2 = replaceAll(agree.getInsurance_content(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_agree set "
				+ "Insurance_content='"+result2+"' "
				 +" where id='"+agree.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}
	@Override
	public String updatelender(Agree agree) {
		String result2 = replaceAll(agree.getLender_content(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_agree set "
				+ "lender_content='"+result2+"' "
				 +" where id='"+agree.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}
	
	
	
	
	

}
