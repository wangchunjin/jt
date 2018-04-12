package com.epmis.sys.util.tag;

import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.SpringContextHolder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class EpmisTextTag extends TagSupport
{
  private static final long serialVersionUID = -7811902545513255473L;
  private String id = "";

  private String define = "";

  private String value = "";
  private String attr = "";
  private String displaytypeedit = null;

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
        String html = "";
        if ((DataTypeUtil.validate(this.displaytypeedit)) && (this.displaytypeedit.equals("1"))) {
          HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
          String datebase = request.getContextPath();

          if ((DataTypeUtil.validate(this.value)) && (DataTypeUtil.validate(this.define))) {
            this.define = this.define.replace("?", "'" + this.value + "'");
            List listMap = baseJdbcService.OpBySql(this.define);
            if (DataTypeUtil.validate(listMap)) {
              Map map = (Map)listMap.get(0);
              Iterator keys = map.keySet().iterator();
              if (map.size() == 1) {
                String Keyvalue = "";
                while (keys.hasNext())
                {
                  String key = (String)keys.next();

                  Keyvalue = DataTypeUtil.formatDbColumn(map.get(key));
                }
                html = "<table width='100%' ><tr><td width='90%'><input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + " value='" + Keyvalue + "' >" + 
                  " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> </td><td width='10%'> <img style='margin-left: 20px;cursor: pointer;' onclick=\"openWin('" + this.id + "')\" src='" + datebase + "/img/button/bg_edit_but.gif'></td></tr></table>";
              }
            } else {
              html = "<table width='100%'><tr><td width='90%'><input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + " value='" + this.value + "' >" + 
                " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> </td><td width='10%'> <img style='margin-left: 20px;cursor: pointer;' onclick=\"openWin('" + this.id + "')\" src='" + datebase + "/img/button/bg_edit_but.gif'></td></tr></table>";
            }
          } else {
            html = "<table width='100%'><tr><td width='90%'><input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + "  >" + 
              " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> </td><td width='10%'> <img style='margin-left: 20px;cursor: pointer;' onclick=\"openWin('" + this.id + "')\" src='" + datebase + "/img/button/bg_edit_but.gif'></td></tr></table>";
          }

        }
        else if (DataTypeUtil.validate(this.define)) {
          if (DataTypeUtil.validate(this.value)) {
            if ((this.define.toUpperCase().trim().startsWith("SELECT")) && (this.define.trim().endsWith("?"))) {
              this.define = this.define.replace("?", "'" + this.value + "'");
              List listMap = baseJdbcService.OpBySql(this.define);
              if (DataTypeUtil.validate(listMap)) {
                Map map = (Map)listMap.get(0);
                Iterator keys = map.keySet().iterator();
                if (map.size() == 1) {
                  String Keyvalue = "";
                  while (keys.hasNext())
                  {
                    String key = (String)keys.next();

                    Keyvalue = DataTypeUtil.formatDbColumn(map.get(key));
                  }
                  html = "<input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + " value='" + Keyvalue + "' >" + 
                    " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> ";
                }
              } else {
                html = "<input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + " value='" + this.value + "' >" + 
                  " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> ";
              }
            } else {
              String[] str = this.define.split(";");
              for (int i = 0; i < str.length; i++)
                if (this.value.equals(str[i].split("=")[0]))
                  html = "<input class='label_text' readOnly=true   id='" + this.id + "_NAME' " + this.attr + " value='" + str[i].split("=")[1] + "' >" + 
                    " <input type='hidden' id='" + this.id + "' name='" + this.id + "' value='" + this.value + "'> ";
            }
          }
          else
          {
            List listMap = new ArrayList();
            if ((this.define.toUpperCase().trim().startsWith("SELECT")) && (!this.define.toUpperCase().trim().endsWith("?"))) {
              listMap = baseJdbcService.OpBySql(this.define);
            }
            if (DataTypeUtil.validate(listMap)) {
              Map map = (Map)listMap.get(0);
              Iterator keys = map.keySet().iterator();
              if (map.size() == 1) {
                String Keyvalue = "";
                while (keys.hasNext())
                {
                  String key = (String)keys.next();

                  Keyvalue = DataTypeUtil.formatDbColumn(map.get(key));
                }
                html = "<input class='label_text' name='" + this.id + "' id='" + this.id + "' " + this.attr + " value='" + Keyvalue + "' />";
              }
            } else {
              html = "<input class='label_text' name='" + this.id + "' id='" + this.id + "' " + this.attr + " value='' >";
            }

          }

        }

        out.println(html);
      } catch (Exception e) {
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

  public String getDisplaytypeedit()
  {
    return this.displaytypeedit;
  }

  public void setDisplaytypeedit(String displaytypeedit)
  {
    this.displaytypeedit = displaytypeedit;
  }
}