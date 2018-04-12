package com.epmis.sys.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil
{
  public static Document setElementByDataXml(Document paramDocument, String paramString1, String paramString2)
  {
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    Iterator localIterator2;
    for (; localIterator1.hasNext(); 
      localIterator2.hasNext())
    {
      Element localElement1 = (Element)localIterator1.next();
      localIterator2 = localElement1.elementIterator();
      continue;
    }
    return paramDocument;
  }

  public static String getElementByDataXml(Document paramDocument, String paramString)
  {
    if (validateNode(paramDocument, "//record"))
    {
      Node localNode = paramDocument.selectSingleNode("//" + paramString);
      if (DataTypeUtil.validate(localNode))
        return localNode.getText();
      return "";
    }
    return null;
  }

  public static ArrayList<String> getElementListByDataXml(Document paramDocument, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      List localList = paramDocument.selectNodes(paramString);
      for (int i = 0; i < localList.size(); i++)
      {
        Element localElement = (Element)localList.get(i);
        localArrayList.add(localElement.getText());
      }
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return localArrayList;
  }

  public static Document setAttributeByDataXml(Document paramDocument, String paramString1, String paramString2, String paramString3)
  {
    List localList = paramDocument.selectNodes(paramString1);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Element localElement = (Element)localIterator.next();
      Attribute localAttribute = localElement.attribute(paramString2);
      if (localAttribute == null)
        localElement.addAttribute(paramString2, paramString3);
      else
        localAttribute.setData(paramString3);
    }
    return paramDocument;
  }

  public static String getAttributeByDataXml(Document paramDocument, String paramString1, String paramString2)
  {
    Element localElement = (Element)paramDocument.selectSingleNode(paramString1);
    Attribute localAttribute = localElement.attribute(paramString2);
    if (DataTypeUtil.validate(localAttribute))
      return localAttribute.getText();
    return null;
  }

  public static String getValueByXml(Document[] paramArrayOfDocument, String paramString)
  {
    try
    {
      if ((DataTypeUtil.validate(paramArrayOfDocument)) && (DataTypeUtil.validate(paramString)))
        for (int i = 0; i < paramArrayOfDocument.length; i++)
        {
          Node localNode = paramArrayOfDocument[i].selectSingleNode(paramString);
          if (DataTypeUtil.validate(localNode))
            return localNode.getText();
        }
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return null;
  }

  public static String getValueByXml(Document paramDocument, String paramString)
  {
    try
    {
      if ((DataTypeUtil.validate(paramDocument)) && (DataTypeUtil.validate(paramString)))
      {
        Node localNode = paramDocument.selectSingleNode(paramString);
        if (DataTypeUtil.validate(localNode))
          return localNode.getText();
      }
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return null;
  }

  public static Document parse(String paramString)
  {
    try
    {
      return DocumentHelper.parseText(paramString);
    }
    catch (Exception localDocumentException)
    {
      localDocumentException.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    String xml2 = "<?xml version='1.0' encoding='gb2312'?><document><record><weighTime>00-21-5D-9B-EC-68</weighTime><StartDate>2014-09-04</StartDate><EndDate>2015-09-03</EndDate><ModuleCode>DS,SM,CC,QC,KM,CO,TM,GM,CN,SYS</ModuleCode><ProjNum></ProjNum></record></document>";

    String xml = "<?xml version= '1.0' encoding='gb2312'?><weighData><weighTime>2012-12-2 12:23:12</weighTime><cardNum>2</cardNum><cfid>123</cfid></weighData>";
    parse(xml2);
  }

  public static Document parse(StringBuffer paramStringBuffer)
  {
    try
    {
      return DocumentHelper.parseText(paramStringBuffer.toString());
    }
    catch (DocumentException localDocumentException)
    {
      localDocumentException.printStackTrace();
    }
    return null;
  }

  public static String parseMetaXML(Document paramDocument, String paramString1, String paramString2)
  {
    try
    {
      List localList = paramDocument.selectNodes("//FIELD");
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Element localElement = (Element)localIterator.next();
        if (localElement.attribute("Ename").getText().equalsIgnoreCase(paramString1))
        {
          paramString2 = paramString2.toLowerCase();
          paramString2 = paramString2.substring(0, 1).toUpperCase() + paramString2.substring(1, paramString2.length()).toLowerCase();
          Attribute localAttribute = localElement.attribute(paramString2);
          if (DataTypeUtil.validate(localAttribute))
            return localAttribute.getText();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static Document createDocumnet(String paramString)
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.addElement(paramString);
    return localDocument;
  }

  public static Document createDocmentByFilepath(String paramString)
  {
    SAXReader localSAXReader = new SAXReader();
    Document localDocument = null;
    try
    {
      localDocument = localSAXReader.read(paramString);
    }
    catch (DocumentException localDocumentException)
    {
      Logger.error(localDocumentException);
    }
    return localDocument;
  }

  public static Document deleteElementByDataXml(Document paramDocument, String paramString)
  {
    List localList = paramDocument.selectNodes(paramString);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Element localElement = (Element)localIterator.next();
      localElement.getParent().remove(localElement);
    }
    return paramDocument;
  }

  public static ArrayList<Object> getObjectByXml(Document paramDocument, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject1 = null;
    try
    {
      List localList = paramDocument.selectNodes("/document/record");
      for (int i = 0; i < localList.size(); i++)
      {
        Element localElement = (Element)localList.get(i);
        localObject1 = Class.forName(paramString).newInstance();
        for (int j = 0; j < localElement.nodeCount(); j++)
        {
          Node localNode = localElement.node(j);
          Method[] arrayOfMethod = localObject1.getClass().getDeclaredMethods();
          for (int k = 0; k < arrayOfMethod.length; k++)
          {
            String str = arrayOfMethod[k].getName();
            if (str.equalsIgnoreCase("set" + localNode.getName()))
            {
              Class[] arrayOfClass = arrayOfMethod[k].getParameterTypes();
              if ((arrayOfClass.length > 0) && (DataTypeUtil.validate(localNode.getText())))
              {
                Object localObject2 = null;
                for (int m = 0; m < arrayOfClass.length; m++)
                  if (arrayOfClass[m].getName().equals("java.lang.String"))
                  {
                    localObject2 = localNode.getText();
                  }
                  else if (arrayOfClass[m].getName().equals("int"))
                  {
                    Double localDouble = new Double(Double.valueOf(localNode.getText()).doubleValue());
                    localObject2 = Integer.valueOf(localDouble.intValue());
                  }
                  else if (arrayOfClass[m].getName().equals("double"))
                  {
                    localObject2 = Double.valueOf(localNode.getText());
                  }
                  else if (arrayOfClass[m].getName().equals("float"))
                  {
                    localObject2 = Float.valueOf(localNode.getText());
                  }
                  else if (arrayOfClass[m].getName().equals("boolean"))
                  {
                    localObject2 = Boolean.valueOf(localNode.getText());
                  }
                  else if (arrayOfClass[m].getName().equals("java.util.Date"))
                  {
                    localObject2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(localNode.getText());
                  }
                arrayOfMethod[k].invoke(localObject1, new Object[] { localObject2 });
              }
            }
          }
        }
        localArrayList.add(localObject1);
      }
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return localArrayList;
  }

  public static Document[] getXmlArrByDocmentstring(String paramString)
  {
    try
    {
      Document[] arrayOfDocument = null;
      Document localDocument = parse(paramString);
      if ((paramString != null) && (paramString.length() > 0))
      {
        ArrayList localArrayList = getElementListByDataXml(localDocument, "//table");
        if (localArrayList.size() > 0)
        {
          arrayOfDocument = new Document[localArrayList.size()];
          for (int i = 0; i < localArrayList.size(); i++)
          {
            String str = localArrayList.get(i).toString();
            arrayOfDocument[i] = DocumentHelper.parseText(str);
          }
          return arrayOfDocument;
        }
      }
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return null;
  }

  public static Document[] getXmlArrByNode(Document paramDocument, String paramString)
  {
    List localList = paramDocument.selectNodes(paramString);
    if (DataTypeUtil.validate(localList))
    {
      Document[] arrayOfDocument = new Document[localList.size()];
      Document localDocument = null;
      for (int i = 0; i < localList.size(); i++)
      {
        localDocument = createDocumnet(paramDocument.getRootElement().getName());
        Element localElement1 = localDocument.getRootElement();
        Element localElement2 = (Element)localList.get(i);
        localElement1.add((Element)localElement2.clone());
        arrayOfDocument[i] = localDocument;
      }
      return arrayOfDocument;
    }
    return null;
  }

  public static String getDocumentstringByDocumentArr(Document[] paramArrayOfDocument)
  {
    Document localDocument = createDocumnet("coument");
    for (int i = 0; i < paramArrayOfDocument.length; i++)
      addElementByXml(localDocument, "//coument", "table", paramArrayOfDocument[i].asXML());
    return localDocument.asXML();
  }

  public static boolean validateElementByDataXml(Document paramDocument, String paramString)
  {
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    Iterator localIterator2;
    for (; localIterator1.hasNext(); 
      localIterator2.hasNext())
    {
      Element localElement1 = (Element)localIterator1.next();
      localIterator2 = localElement1.elementIterator();
      continue;
    }
    return false;
  }

  public static boolean validateElementByDataXml(Document paramDocument, String paramString1, String paramString2)
  {
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    Iterator localIterator2;
    for (; localIterator1.hasNext(); 
      localIterator2.hasNext())
    {
      Element localElement1 = (Element)localIterator1.next();
      localIterator2 = localElement1.elementIterator();
      continue;
    }
    return false;
  }

  public static boolean validateAttributeByDataXml(Document paramDocument, String paramString)
  {
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Element localElement = (Element)localIterator.next();
      Attribute localAttribute = localElement.attribute(paramString);
      if (DataTypeUtil.validate(localAttribute.getText()))
        return false;
    }
    return true;
  }

  public static boolean validateNode(Document paramDocument, String paramString)
  {
    return (paramDocument != null) && (paramDocument.selectNodes(paramString).size() > 0);
  }

  public static boolean validate(String paramString)
  {
    if (DataTypeUtil.validate(paramString))
      try
      {
        DocumentHelper.parseText(paramString);
        return true;
      }
      catch (DocumentException localDocumentException)
      {
      }
    return false;
  }

  public static boolean validateValue(Document paramDocument, String paramString)
  {
    return (paramDocument != null) && (paramDocument.selectSingleNode(paramString) != null) && (DataTypeUtil.validate(paramDocument.selectSingleNode(paramString).getText()));
  }

  public static void writerDocument(Document paramDocument, HttpServletResponse paramHttpServletResponse)
  {
    if (DataTypeUtil.validate(paramDocument))
    {
      paramHttpServletResponse.setCharacterEncoding("UTF-8");
      paramHttpServletResponse.setContentType("text/xml; charset=UTF-8");
      try
      {
        paramHttpServletResponse.getWriter().print(paramDocument.asXML());
        paramHttpServletResponse.getWriter().flush();
        paramHttpServletResponse.getWriter().close();
      }
      catch (IOException localIOException)
      {
        Logger.error(localIOException);
      }
    }
  }

  public static String getFormatXml(Document paramDocument)
  {
    if (DataTypeUtil.validate(paramDocument))
      try
      {
        StringWriter localStringWriter = new StringWriter();
        XMLWriter localXMLWriter = null;
        OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
        localOutputFormat.setEncoding("UTF-8");
        localXMLWriter = new XMLWriter(localOutputFormat);
        localXMLWriter.setWriter(localStringWriter);
        localXMLWriter.write(paramDocument);
        localXMLWriter.close();
        return localStringWriter.toString();
      }
      catch (Exception localException)
      {
        Logger.error(localException);
      }
    return "";
  }

  public static String displayXml(Document paramDocument)
  {
    if (DataTypeUtil.validate(paramDocument))
      try
      {
        StringWriter localStringWriter = new StringWriter();
        XMLWriter localXMLWriter = null;
        OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
        localOutputFormat.setEncoding("UTF-8");
        localXMLWriter = new XMLWriter(localOutputFormat);
        localXMLWriter.setWriter(localStringWriter);
        localXMLWriter.write(paramDocument);
        localXMLWriter.close();
        return localStringWriter.toString();
      }
      catch (Exception localException)
      {
        Logger.error(localException);
        return paramDocument.asXML();
      }
    return "";
  }

  public static String displayXml(String paramString)
  {
    try
    {
      Document localDocument = DocumentHelper.parseText(paramString);
      StringWriter localStringWriter = new StringWriter();
      XMLWriter localXMLWriter = null;
      OutputFormat localOutputFormat = OutputFormat.createPrettyPrint();
      localOutputFormat.setEncoding("UTF-8");
      localXMLWriter = new XMLWriter(localOutputFormat);
      localXMLWriter.setWriter(localStringWriter);
      localXMLWriter.write(localDocument);
      localXMLWriter.close();
      return localStringWriter.toString();
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return paramString;
  }

  public static Document createAjaxToDocumnet()
  {
    Document localDocument = DocumentHelper.createDocument();
    Element localElement = localDocument.addElement("DOCUMENT");
    localElement.addElement("INFO");
    localElement.addElement("ERROR");
    localElement.addElement("TEXT");
    localElement.addElement("SQL");
    return localDocument;
  }

  public static Element createRootDocument(String paramString)
  {
    Document localDocument = DocumentHelper.createDocument();
    localDocument.addElement(paramString);
    return localDocument.getRootElement();
  }

  static int _$2(String paramString)
  {
    Hashtable localHashtable = new Hashtable();
    localHashtable.put("WID", "0");
    localHashtable.put("BOWID", "1");
    localHashtable.put("ENAME", "2");
    localHashtable.put("CNAME", "3");
    localHashtable.put("COLUMNTYPE", "4");
    localHashtable.put("COLUMNLEN", "5");
    localHashtable.put("DEFAULTVAL", "6");
    localHashtable.put("ISPK", "7");
    localHashtable.put("ISNULLABLE", "8");
    localHashtable.put("DISPLAYTYPE", "9");
    localHashtable.put("DISPLAYWIDTH", "10");
    localHashtable.put("SUBSQL", "11");
    localHashtable.put("PROMPT", "12");
    localHashtable.put("FILTER", "13");
    localHashtable.put("HIDE", "14");
    localHashtable.put("READONLY", "15");
    localHashtable.put("ORDERBY", "16");
    localHashtable.put("SFQY", "17");
    localHashtable.put("RSDATATYPE", "18");
    localHashtable.put("ISCONDITIONCOLUMN", "19");
    localHashtable.put("USERDEFINEVAL", "20");
    localHashtable.put("USERDEFINECONSTRAINT", "21");
    localHashtable.put("DISPLAYTYPEEDIT", "22");
    Object localObject = localHashtable.get(paramString);
    if (DataTypeUtil.validate(localObject))
      return Integer.valueOf(((String)localHashtable.get(paramString)).toString()).intValue();
    return -1;
  }

  public static String getElementArrByDataXml(Document paramDocument, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    Iterator localIterator2;
    for (; localIterator1.hasNext(); 
      localIterator2.hasNext())
    {
      Element localElement1 = (Element)localIterator1.next();
      localIterator2 = localElement1.elementIterator();
      continue;
    }
    return localStringBuffer.toString();
  }

  private static Document _$1(Document paramDocument, String paramString){
	return paramDocument;/*
    Document localDocument = DocumentHelper.createDocument();
    Element localElement1 = localDocument.addElement("document");
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    String[] arrayOfString =null;
    int j=0; 
    for (; localIterator1.hasNext(); j < arrayOfString.length)
    {
      Element localElement2 = (Element)localIterator1.next();
      int i = 0;
      arrayOfString = paramString.split(" and ");
      j = 0; continue;

      String str1 = _$1(arrayOfString[j].substring(0, arrayOfString[j].indexOf("=")));
      String str2 = _$1(arrayOfString[j].substring(arrayOfString[j].indexOf("=") + 1, arrayOfString[j].length()));
      Iterator localIterator2 = localElement2.elementIterator();
      while (localIterator2.hasNext())
      {
        Element localElement3 = (Element)localIterator2.next();
        if ((localElement3.getName().equalsIgnoreCase(str1)) && (localElement3.getText().equalsIgnoreCase(str2)))
          i++;
        if (i == arrayOfString.length)
        {
          Element localElement4 = localElement1.addElement("record");
          localElement4.addAttribute("state", "");
          localElement4.addAttribute("bizobj", "");
          Iterator localIterator3 = localElement3.getParent().elementIterator();
          while (localIterator3.hasNext())
          {
            Element localElement5 = (Element)localIterator3.next();
            Element localElement6 = localElement4.addElement(localElement5.getName());
            localElement6.setText(localElement5.getText());
            i = 0;
          }
        }
      }
      j++;
    }

    return localDocument;
  */}

  public static Document getDataXmlByCondition(Document paramDocument, String paramString)
  {
    Document localDocument1 = DocumentHelper.createDocument();
    Element localElement1 = localDocument1.addElement("document");
    if (paramString.contains(" or "))
    {
      String[] arrayOfString = paramString.split(" or ");
      for (int i = 0; i < arrayOfString.length; i++)
      {
        Element localElement2 = localElement1.addElement("record");
        localElement2.addAttribute("state", "");
        localElement2.addAttribute("bizobj", "");
        Document localDocument2 = _$1(paramDocument, _$1(arrayOfString[i]));
        List localList = localDocument2.selectNodes("//record");
        Iterator localIterator1 = localList.iterator();
        Iterator localIterator2;
        for (; localIterator1.hasNext(); 
          localIterator2.hasNext())
        {
          Element localElement3 = (Element)localIterator1.next();
          localIterator2 = localElement3.elementIterator();
          continue;
        }
      }

      return localDocument1;
    }
    localDocument1 = _$1(paramDocument, paramString);
    return localDocument1;
  }

  public static String getDefaultvalByParseMetaXML(Document paramDocument, String paramString)
  {
    return parseMetaXML(paramDocument, paramString, "DEFAULTVAL");
  }

  private static String _$1(String paramString)
  {
    if (!DataTypeUtil.validate(paramString))
      return null;
    String str = paramString.trim();
    if (str.contains("'"))
      str = str.replaceAll("'", "");
    if ((str.contains("(")) && (str.contains(")")))
    {
      str = str.replaceAll("\\(", "");
      str = str.replaceAll("\\)", "");
    }
    return str;
  }

  public static Document updateObjectByXml(Document paramDocument, String paramString, Object paramObject)
  {
    try
    {
      Node localNode = paramDocument.selectSingleNode(paramString);
      localNode.setText(paramObject.toString());
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return paramDocument;
  }

  public static Document addElementByXml(Document paramDocument, String paramString1, String paramString2, Object paramObject)
  {
    try
    {
      List localList = paramDocument.selectNodes(paramString1);
      for (int i = 0; i < localList.size(); i++)
      {
        Element localElement1 = (Element)localList.get(i);
        Element localElement2 = localElement1.addElement(paramString2);
        if (DataTypeUtil.validate(paramObject))
        {
          localElement2.setText(paramObject.toString());
        }
      }
    }
    catch (Exception localException) {
      Logger.error(localException);
    }
    return paramDocument;
  }

  public static Document getUppercaseDocument(Document paramDocument)
  {
    Document localDocument = DocumentHelper.createDocument();
    List localList = paramDocument.selectNodes("//record");
    Iterator localIterator1 = localList.iterator();
    if (localIterator1.hasNext())
    {
      Element localElement1 = localDocument.addElement("record");
      Element localElement2 = (Element)localIterator1.next();
      Iterator localIterator2 = localElement2.attributeIterator();

      while (localIterator2.hasNext())
      {
        Object localObject = (Attribute)localIterator2.next();
        localElement1.addAttribute(((Attribute)localObject).getName(), ((Attribute)localObject).getText());
      }
      localIterator2 = localElement2.elementIterator();
      while (localIterator2.hasNext())
      {
        Object localObject = (Element)localIterator2.next();
        Element localElement3 = localElement1.addElement(((Element)localObject).getName().toUpperCase());
        localElement3.addText(((Element)localObject).getText());
      }
    }
    return localDocument;
  }

  public static int getCountNode(Document paramDocument, String paramString)
  {
    List localList = paramDocument.selectNodes(paramString);
    return localList.size();
  }

  public static Document getDocumentByDocuments(Document[] paramArrayOfDocument, String paramString1, String paramString2)
  {
    if ((paramArrayOfDocument != null) && (paramArrayOfDocument.length > 0))
      for (int i = 0; i < paramArrayOfDocument.length; i++)
      {
        List localList = paramArrayOfDocument[i].selectNodes("//record");
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          Element localElement = (Element)localIterator.next();
          if ((localElement != null) && (localElement.attributeValue("bizsys").equals(paramString1)) && (localElement.attributeValue("bizobj").equals(paramString2)))
            return paramArrayOfDocument[i];
        }
      }
    return null;
  }

  public static boolean validate(Document[] paramArrayOfDocument)
  {
    return (paramArrayOfDocument != null) && (paramArrayOfDocument.length != 0);
  }

  public static boolean validate(Document paramDocument)
  {
    try
    {
      Element localElement = (Element)paramDocument.selectSingleNode("//record");
      if (localElement.elements().size() > 0)
        return true;
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }

  public static boolean validate(Document paramDocument1, Document paramDocument2)
  {
	return false;/*
    if ((paramDocument1 == null) || (paramDocument2 == null))
      return true;
    if ((paramDocument1 != null) && (paramDocument2 != null) && (validateNode(paramDocument1, "//record")) && (validateNode(paramDocument2, "//record")))
    {
      Document localDocument1 = (Document)paramDocument1.clone();
      Document localDocument2 = (Document)paramDocument2.clone();
      List localList1 = localDocument1.selectNodes("//record");
      Iterator localIterator = localList1.iterator();
      List localList2;
      for (; localIterator.hasNext(); localList2.size() > 0)
      {
        Element localElement = (Element)localIterator.next();
        localList2 = localElement.attributes();
        int i = 0; continue;
        localElement.remove((Attribute)localList2.get(0));

        i++;
      }

      localList1 = localDocument2.selectNodes("//record");
      localIterator = localList1.iterator();
      List localList21;
      for (; localIterator.hasNext();localList21.size() > 0)
      {
        Element localElement = (Element)localIterator.next();
        localList21 = localElement.attributes();
        int i = 0; continue;
        localElement.remove((Attribute)localList21.get(0));

        i++;
      }

      if (localDocument1.asXML().equals(localDocument2.asXML()))
        return true;
    }
    return false;
  */}

  public static String getNodeValueString(Node paramNode)
  {
    if (DataTypeUtil.validate(paramNode))
      return String.valueOf(paramNode.getText());
    return "";
  }

  public static Map<String, Object> getMapByXml(Document paramDocument)
  {
    HashMap localHashMap = null;
    if (validateNode(paramDocument, "//record"))
    {
      List localList = paramDocument.selectNodes("//record");
      Iterator localIterator1 = localList.iterator();
      if (localIterator1.hasNext())
      {
        localHashMap = new HashMap();
        Element localElement1 = (Element)localIterator1.next();
        Iterator localIterator2 = localElement1.elementIterator();
        while (localIterator2.hasNext())
        {
          Element localElement2 = (Element)localIterator2.next();
          localHashMap.put(localElement2.getName(), localElement2.getText());
        }
        return localHashMap;
      }
    }
    return localHashMap;
  }

  public static List<Map<String, Object>> getListMapByXml(Document paramDocument)
  {
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = null;
    if (validateNode(paramDocument, "//record"))
    {
      List localList = paramDocument.selectNodes("//record");
      Iterator localIterator1 = localList.iterator();
      while (localIterator1.hasNext())
      {
        localHashMap = new HashMap();
        Element localElement1 = (Element)localIterator1.next();
        Iterator localIterator2 = localElement1.elementIterator();
        while (localIterator2.hasNext())
        {
          Element localElement2 = (Element)localIterator2.next();
          if (DataTypeUtil.validate(localElement2.getText()))
            localHashMap.put(localElement2.getName(), localElement2.getText());
        }
        localArrayList.add(localHashMap);
      }
    }
    return localArrayList;
  }

  public static Document getXmlByMap(Map<String, Object> paramMap)
  {
    if (DataTypeUtil.validate(paramMap))
    {
      Document localDocument = createDocumnet("document");
      Element localElement1 = localDocument.getRootElement();
      Element localElement2 = localElement1.addElement("record");
      Set localSet = paramMap.entrySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localElement2.addElement(DataTypeUtil.formatDbColumn(localEntry.getKey())).addText(DataTypeUtil.formatDbColumn(localEntry.getValue()));
      }
      return localDocument;
    }
    return null;
  }

  public static Document getXmlByMap(List<Map<String, Object>> paramList)
  {
    if (DataTypeUtil.validate(paramList))
    {
      Document localDocument = createDocumnet("document");
      Element localElement1 = localDocument.getRootElement();
      Iterator localIterator1 = paramList.iterator();
      Iterator localIterator2;
      for (; localIterator1.hasNext(); 
        localIterator2.hasNext())
      {
        Map localMap = (Map)localIterator1.next();
        Element localElement2 = localElement1.addElement("record");
        Set localSet = localMap.entrySet();
        localIterator2 = localSet.iterator();
        continue;
      }

      return localDocument;
    }
    return null;
  }
}