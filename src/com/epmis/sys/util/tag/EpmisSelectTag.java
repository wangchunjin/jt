package com.epmis.sys.util.tag;

import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.SpringContextHolder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class EpmisSelectTag extends TagSupport
{
  private String id = "";

  private String define = "";

  private String value = "";
  private String attr = "";
  private String onChange = "";

  public String getOnChange()
  {
    return this.onChange;
  }

  public void setOnChange(String onChange)
  {
    this.onChange = onChange;
  }

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
    if (b.booleanValue()) {
      try {
        BaseJdbcService baseJdbcService = 
          (BaseJdbcService)SpringContextHolder.getBean("baseJdbcService");
        String optionStr = "";
        optionStr = "<option value=''  >--请选择--</option>";
        if (DataTypeUtil.validate(this.define)) {
          if (this.define.toUpperCase().trim().startsWith("SELECT")) {
            List<Map<String, Object>> listMap = baseJdbcService.OpBySql(this.define);
            if (DataTypeUtil.validate(listMap))
            	
              for (Map<String,Object> map : listMap) {
                String id = "";
                String value = "";
                String selected = "";
                Iterator keys = map.keySet().iterator();
                int i = 0;
                while (keys.hasNext()) {
                  if (i == 0) {
                    String key = (String)keys.next();
                    id = DataTypeUtil.formatDbColumn(map.get(key));
                  } else if (i == 1) {
                    String key = (String)keys.next();
                    value = DataTypeUtil.formatDbColumn(map.get(key));
                  }
                  i++;
                }
                if ((DataTypeUtil.validate(this.value)) && (this.value.equals(id))) {
                  selected = "selected";
                }
                optionStr = optionStr + "<option value='" + id + "' " + selected + " >" + value + "</option>";
              }
          }
          else {
            String[] maps = this.define.split(";");

            for (int i = 0; i < maps.length; i++) {
              String id = maps[i].split("=")[0];
              String value = maps[i].split("=")[1];
              String selected = "";
              if ((DataTypeUtil.validate(this.value)) && (this.value.equals(id))) {
                selected = "selected";
              }
              optionStr = optionStr + "<option value='" + id + "' " + selected + " >" + value + "</option>";
            }
          }
        }

        String html = "<select id='" + this.id + "' class='select' name='" + this.id + "' " + this.attr + " onchange=\"javacript:$('#labelBottomDiv').css('display','');" + this.onChange + "\"    >" + optionStr + "</select>";
        out.println(html);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return 0;
  }

  public void release()
  {
    super.release();
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  public String getDefine()
  {
    return this.define;
  }

  public void setDefine(String define)
  {
    this.define = define;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getAttr()
  {
    return this.attr;
  }

  public void setAttr(String attr)
  {
    this.attr = attr;
  }
}