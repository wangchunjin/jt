package com.epmis.webService.service;

import java.util.List;
import java.util.Map;

import com.epmis.sys.service.imp.BaseJdbcServiceImpl;

public interface ILicenseService {
    public String getIpAddress(); 
    public String downLicense() throws Exception;
    public boolean createLicense(String mac,String projName);
    public String ConnectTest(String mac,String projName);
}
    