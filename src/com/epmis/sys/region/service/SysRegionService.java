package com.epmis.sys.region.service;

import java.util.List;
import java.util.Map;

import com.epmis.sys.region.vo.Region;
import com.epmis.sys.util.UserInfo;

public abstract interface SysRegionService{
  public abstract List<Map<String, Object>> regionTree(String ParentId);
  public abstract String save(Region region,UserInfo userInfo);
  public abstract String update(Region region,UserInfo userInfo);
  public abstract String updatezb(Region region);
  public abstract Map<String, Object> getInfoById(String cnmt_id);
  public abstract String delete(String wid);
  /**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	public abstract String deletezb(String wid);
}