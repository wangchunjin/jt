package com.epmis.sys.util.tag;

import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.SpringContextHolder;
import com.epmis.sys.util.UserInfo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class EpmisButtonTag extends TagSupport
{
  private static final long serialVersionUID = -7811902545513255473L;
  private String datactrCode = "";
  private String datactrSql = "";

  private String action = "";

  private String imageCss = "";

  private String value = "";

  public int doStartTag()
    throws JspTagException
  {
    return 1;
  }

  public int doEndTag()
    throws JspTagException
  {
    Boolean b = Boolean.valueOf(true);

    JspWriter out = this.pageContext.getOut();
    HttpSession pagination = this.pageContext.getSession();
    UserInfo userInfo = (UserInfo)pagination.getAttribute("UserInfo");
    if (b.booleanValue()) {
      try {
        boolean show = true;
        BaseJdbcService baseJdbcService = 
          (BaseJdbcService)SpringContextHolder.getBean("baseJdbcService");
        String DatactrCode = "";
        if (DataTypeUtil.validate(userInfo)) {
          DatactrCode = userInfo.getDatactrCode();
        }
        if ((DataTypeUtil.validate(this.datactrCode)) && (DatactrCode.indexOf(this.datactrCode) == -1)) {
          show = false;
        }
        if (DataTypeUtil.validate(this.datactrSql)) {
          List a = baseJdbcService.OpBySql(this.datactrSql);
          if (a.size() > 0) {
            show = true;
          }
        }

        if (show)
          out.println("<a href='javascript:void(0)'  class='l-btn l-btn-plain' id='" + this.id + "' onclick=\"" + this.action + "\"><span class='l-btn-left'><span class='l-btn-text " + this.imageCss + " l-btn-icon-left'>" + this.value + "</span></span></a>");
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }

    return 0;
  }

  public String getDatactrSql() {
    return this.datactrSql;
  }

  public void setDatactrSql(String datactrSql)
  {
    this.datactrSql = datactrSql;
  }

  public void release()
  {
    super.release();
  }

  public String getImageCss()
  {
    return this.imageCss;
  }

  public void setImageCss(String imageCss)
  {
    this.imageCss = imageCss;
  }

  public String getDatactrCode()
  {
    return this.datactrCode;
  }

  public void setDatactrCode(String datactrCode)
  {
    this.datactrCode = datactrCode;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getAction()
  {
    return this.action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }
}