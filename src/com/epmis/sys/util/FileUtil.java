package com.epmis.sys.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil
{
  private static final int _$1 = 4096;

  public static boolean validate(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1, paramString2);
    return localFile.exists();
  }

  public static boolean validate(String paramString)
  {
    File localFile = new File(paramString);
    return localFile.exists();
  }

  public static void createFile(String paramString1, String paramString2)
    throws IOException
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      localFile1.mkdirs();
    File localFile2 = new File(paramString1, paramString2);
    if (!localFile2.exists())
      localFile2.createNewFile();
  }

  public static void appendFile(String paramString1, String paramString2)
    throws IOException
  {
    FileWriter localFileWriter = new FileWriter(paramString1, true);
    BufferedWriter localBufferedWriter = new BufferedWriter(localFileWriter);
    localFileWriter.write(paramString2);
    localBufferedWriter.close();
    localFileWriter.close();
  }

  public static void writerFile(String paramString1, String paramString2, boolean paramBoolean)
    throws IOException
  {
    OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(paramString1, paramBoolean), "UTF-8");
    localOutputStreamWriter.write(paramString2);
    localOutputStreamWriter.close();
  }

  public static void writerFile(String paramString1, String paramString2, boolean paramBoolean, String paramString3)
    throws IOException
  {
    OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(paramString1, paramBoolean), paramString3);
    localOutputStreamWriter.write(paramString2);
    localOutputStreamWriter.close();
  }

  public static synchronized void delFolder(String folderPath)
  {
    try
    {
      delAllFile(folderPath);
      String filePath = folderPath;
      filePath = filePath.toString();
      File myFilePath = new File(filePath);
      myFilePath.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static synchronized boolean delAllFile(String path)
  {
    boolean flag = false;
    File file = new File(path);
    if (!file.exists()) {
      return flag;
    }
    if (!file.isDirectory()) {
      return flag;
    }
    String[] tempList = file.list();
    File temp = null;
    for (int i = 0; i < tempList.length; i++) {
      if (path.endsWith(File.separator))
        temp = new File(path + tempList[i]);
      else {
        temp = new File(path + File.separator + tempList[i]);
      }
      if (temp.isFile()) {
        temp.delete();
      }
      if (temp.isDirectory()) {
        delAllFile(path + "/" + tempList[i]);
        delFolder(path + "/" + tempList[i]);
        flag = true;
      }
    }
    return flag;
  }

  public static void uploadFile(File uploadfile, String Path) {
    InputStream is = null;
    OutputStream os = null;
    try
    {
      is = new FileInputStream(uploadfile);

      File toFile = new File(Path);

      os = new FileOutputStream(toFile);

      byte[] buffer = new byte[1024];

      int length = 0;

      while ((length = is.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        os.close();
        is.close();
      }
      catch (IOException a) {//修改（e）
        a.printStackTrace();//修改（e）
      }
    }
    finally
    {
      try
      {
        os.close();
        is.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static String readerFile(String paramString)
    throws IOException
  {
    if (DataTypeUtil.validate(paramString))
    {
      String localArrayList = "";
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramString), "UTF-8"));
      for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
        localArrayList = localArrayList + str;
      localBufferedReader.close();
      return localArrayList;
    }
    return null;
  }

  public static byte[] readFileBinary(String paramString)
    throws IOException
  {
    if (DataTypeUtil.validate(paramString))
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      DataInputStream localDataInputStream = new DataInputStream(localFileInputStream);
      byte[] arrayOfByte = new byte[localDataInputStream.available()];
      localDataInputStream.read(arrayOfByte);
      localDataInputStream.close();
      localFileInputStream.close();
      return arrayOfByte;
    }
    return null;
  }

  public static void writerFileBinary(String paramString, byte[] paramArrayOfByte, boolean paramBoolean)
    throws IOException
  {
    if ((DataTypeUtil.validate(paramString)) && (DataTypeUtil.validate(paramArrayOfByte)))
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString, paramBoolean);
      DataOutputStream localDataOutputStream = new DataOutputStream(localFileOutputStream);
      localDataOutputStream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
      localDataOutputStream.close();
      localFileOutputStream.close();
    }
  }

  public static void zip(String sourceDir, String zipFile)
  {
    try {
      OutputStream os = new FileOutputStream(zipFile);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      ZipOutputStream zos = new ZipOutputStream(bos);
      File file = new File(sourceDir);

      String basePath = null;
      if (file.isDirectory())
        basePath = file.getPath();
      else {
        basePath = file.getParent();
      }

      zipFile(file, basePath, zos);

      zos.closeEntry();
      zos.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void zipFile(File source, String basePath, ZipOutputStream zos)
  {
    File[] files = new File[0];

    if (source.isDirectory()) {
      files = source.listFiles();
    } else {
      files = new File[1];
      files[0] = source;
    }

    byte[] buf = new byte[1024];
    int length = 0;
    try {
      for (File file : files)
        if (file.isDirectory()) {
          String pathName = file.getPath().substring(basePath.length() + 1) + 
            "/";
          zos.putNextEntry(new ZipEntry(pathName));
          zipFile(file, basePath, zos);
        } else {
          String pathName = file.getPath().substring(basePath.length() + 1);
          InputStream is = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(is);
          zos.putNextEntry(new ZipEntry(pathName));
          while ((length = bis.read(buf)) > 0) {
            zos.write(buf, 0, length);
          }
          is.close();
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void unZip(String zipfile, String destDir)
  {
    destDir = destDir + "//";
    byte[] b = new byte[1024];
    try
    {
      ZipFile zipFile = new ZipFile(new File(zipfile));
      Enumeration enumeration = zipFile.getEntries();
      ZipEntry zipEntry = null;

      while (enumeration.hasMoreElements()) {
        zipEntry = (ZipEntry)enumeration.nextElement();
        File loadFile = new File(destDir + zipEntry.getName());

        if (zipEntry.isDirectory())
        {
          loadFile.mkdirs();
        } else {
          if (!loadFile.getParentFile().exists()) {
            loadFile.getParentFile().mkdirs();
          }
          OutputStream outputStream = new FileOutputStream(loadFile);
          InputStream inputStream = zipFile.getInputStream(zipEntry);
          int length;
          while ((length = inputStream.read(b)) > 0)
          {
//            int length;  修改
            outputStream.write(b, 0, length);
          }
        }
      }
      System.out.println(" 文件解压成功");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean copyFile(String oldPath, String newPath) {
    boolean flag = false;
    try {
      int bytesum = 0;
      int byteread = 0;
      File oldfile = new File(oldPath);
      if (oldfile.exists()) {
        InputStream inStream = new FileInputStream(oldPath);
        FileOutputStream fs = new FileOutputStream(newPath);
        byte[] buffer = new byte[1444];

        while ((byteread = inStream.read(buffer)) != -1) {
          bytesum += byteread;
          System.out.println(bytesum);
          fs.write(buffer, 0, byteread);
        }
        inStream.close();
      }
      flag = true;
    }
    catch (Exception e) {
      System.out.println("复制单个文件操作出错");
      e.printStackTrace();
    }
    finally {
      return flag;
    }
  }

  public static void main(String[] args)
  {
  }
}