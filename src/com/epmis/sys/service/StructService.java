package com.epmis.sys.service;

import com.epmis.sys.vo.StructLanguage;
import com.epmis.sys.vo.StructOfficetpl;
import java.io.File;
import java.util.List;
import java.util.Map;

public abstract interface StructService
{
  public abstract List<Map<String, Object>> ShowStructTable();

  public abstract List<Map<String, Object>> ShowStructVersionTable(String paramString1, String paramString2);

  public abstract Map<String, Object> GetStruct(String paramString);

  public abstract String AddStruct(StructOfficetpl paramStructOfficetpl, File paramFile);

  public abstract String delStruct(String paramString);

  public abstract String UpdateStruct(StructOfficetpl paramStructOfficetpl);

  public abstract String saveDoc(File paramFile, String paramString1, String paramString2);

  public abstract List LinkStructTable(String paramString);

  public abstract List AddLinkStructTable(String paramString);

  public abstract String AddLinkStruct(String paramString1, String paramString2);

  public abstract String DelLinkStruct(String paramString1, String paramString2);

  public abstract String CreateDoc(File paramFile, String paramString1, String paramString2);

  public abstract List OfficetplKeyInfo(String paramString);

  public abstract String addLanguage(StructLanguage paramStructLanguage);

  public abstract List<Map<String, Object>> showLanguageTree(String paramString);

  public abstract String deleteLanguage(String paramString);

  public abstract Map<String, Object> lableLanguage(String paramString);

  public abstract String updateLanguage(StructLanguage paramStructLanguage);

  public abstract List<Map<String, Object>> showOwnLanguageTree(String paramString1, String paramString2);

  public abstract String checkNode(String paramString);
}