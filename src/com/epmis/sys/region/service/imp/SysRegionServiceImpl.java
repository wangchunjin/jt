package com.epmis.sys.region.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.region.service.SysRegionService;
import com.epmis.sys.region.vo.Region;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

@Transactional
@Service("sysRegionService")
public class SysRegionServiceImpl implements SysRegionService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
  
	public List<Map<String, Object>> regionTree(String parentId){
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		String sql = "";
		if("0".equals(parentId)){
			//国家
			sql = "SELECT WID,AREA_EN,AREA_NAME,AREA_TYPE,around FROM CM_REGION  WHERE PARENT_ID='0' ORDER BY SEQ_NUM ASC";
			List<Map<String, Object>> regionList  = baseJdbcDao.queryListMap(sql);
			
			if(DataTypeUtil.validate(regionList)){
				for(Map<String,Object> item:regionList){
					//类型 0：国家 1：省份 2：城市 3：区县 4:街道
					item.put("iconCls", "icon-cnmt");
					sql = "SELECT COUNT(*) FROM CM_REGION WHERE PARENT_ID='"+item.get("WID")+"'";
					if(this.baseJdbcDao.getCount(sql)>0){
						item.put("state", "closed");
					}
					items.add(item);
				}
			}
		}else{
			sql = "SELECT WID,AREA_EN,AREA_NAME,AREA_TYPE,around FROM CM_REGION  WHERE PARENT_ID='"+parentId+"' ORDER BY SEQ_NUM ASC";
			List<Map<String, Object>> htList  = baseJdbcDao.queryListMap(sql);
			
			if(DataTypeUtil.validate(htList)){
				String type = "";
				for(Map<String,Object> item:htList){
					type = DataTypeUtil.formatDbColumn(item.get("AREA_TYPE"));
					//类型 0：国家 1：省份 2：城市 3：区县 4:街道
					if("0".equals(type)){
						item.put("iconCls", "icon-chmt"); 
					}else if("1".equals(type)){
						item.put("iconCls", "icon-chmt"); 
					}else if("2".equals(type)){
						item.put("iconCls", "icon-chmt"); 
					}else if("3".equals(type)){
						item.put("iconCls", "icon-chmt"); 
					}else if("4".equals(type)){
						item.put("iconCls", "icon-chmt"); 
					}
					sql = "SELECT COUNT(*) FROM CM_REGION WHERE PARENT_ID='"+item.get("WID")+"'";
					if(this.baseJdbcDao.getCount(sql)>0){
						item.put("state", "closed");
					}
					items.add(item);
				}
			}
		}
		return items;
	}
	
	/**
	 * 增加
	 */
	public String save(Region region,UserInfo userInfo){
		if(DataTypeUtil.validate(region)){
			String sql = "INSERT INTO CM_REGION(WID,AREA_NAME,AREA_EN,AREA_TYPE,"
					+ "PARENT_ID,SEQ_NUM,CREATOR,CREATE_DATE) VALUES ("
						+ "'"+Guid.getGuid()+"',"
						+ "'"+region.getAreaName()+"',"
						+ "'"+region.getAreaEn()+"',"
						+ "'"+region.getAreaType()+"',"
						+ "'"+region.getParentId()+"',"
						+ "'"+region.getSeqNum()+"',"
						+ "'"+userInfo.getUserId()+"',now())";
			baseJdbcDao.insert(sql);
		}
		return "success";
	}
	
	/**
	 * 详情
	 */
	public Map<String,Object> getInfoById(String wid){
		String sql =  "SELECT * FROM CM_REGION WHERE WID='"+wid+"'";
		Map<String,Object> map = baseJdbcDao.queryMap(sql);
		return map;
	}
	
	/**
	 * 修改
	 */
	public String update(Region region,UserInfo userInfo){
		if(DataTypeUtil.validate(region)){
			String sql = "UPDATE CM_REGION SET AREA_NAME='"+region.getAreaName()+"',"
					+ "AREA_EN='"+region.getAreaEn()+"',SEQ_NUM='"+region.getSeqNum()+"' WHERE WID='"+region.getWid()+"'";
			baseJdbcDao.insert(sql);
		}
		return "success";
	}
	
	/**
	 * 删除
	 */
	public String delete(String wid){
		if(DataTypeUtil.validate(wid)){
			String sql = "delete from CM_REGION WHERE WID='"+wid+"'";
			baseJdbcDao.delete(sql);
		}
		return "success";
	}

	/**
	 * 根据id进行查找
	 */
	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from CM_REGION where wid='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String updatezb(Region region) {
		if(DataTypeUtil.validate(region)){
			String sql = "UPDATE CM_REGION SET around='"+region.getAround()+"'  WHERE WID='"+region.getWid()+"'";
			baseJdbcDao.insert(sql);
		}
		return "success";
	}

	@Override
	public String deletezb(String wid) {
		if(DataTypeUtil.validate(wid)){
			String sql = "update CM_REGION set around='0' WHERE WID='"+wid+"'";
			baseJdbcDao.delete(sql);
		}
		return "success";
	}
	
} 