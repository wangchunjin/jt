package com.epmis.acmuser.service;

import com.epmis.acmuser.vo.Cmuser;

public interface CmuserService {
	
	 public abstract String AddUser(Cmuser paramCmUsers, String paramString);
	 
	 public abstract String UpdateUser(Cmuser paramCmUsers);
	  
	  public abstract String UpdateUserOther(Cmuser paramCmUsers);

}
