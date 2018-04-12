package com.epmis.app.service;

import java.util.List;
import java.util.Map;

import com.epmis.app.vo.User;

public abstract interface UserHealthService {

	public abstract Map<String, Object> queryHealth(String userid);

	public abstract boolean   addHealth(  String userid,String bloodpress, 
			String bloodsugar, String bloodrt, String temperature,
			String weight, String height);
}
