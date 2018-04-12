package com.epmis.sys.util.PicWord;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;

public class ChartGraphics
{
  BufferedImage image;

  void createImage(String fileLocation)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(fileLocation);
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
      encoder.encode(this.image);
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void graphicsGeneration(String userName, String certName, String imgurl)
  {
    int imageWidth = 160;

    int imageHeight = 80;

    this.image = new BufferedImage(imageWidth, imageHeight, 1);
    Graphics graphics = this.image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, imageWidth, imageHeight);
    graphics.setColor(Color.BLACK);
    graphics.drawString(userName, 80, 22);

    int len = certName.length();

    if (len <= 6) {
      graphics.drawString(certName, 80, 40);
    } else if ((len > 6) && (len <= 12)) {
      graphics.drawString(certName.substring(0, 6), 80, 40);
      graphics.drawString(certName.substring(6), 80, 55);
    } else if (len > 12) {
      graphics.drawString(certName.substring(0, 6), 80, 40);
      graphics.drawString(certName.substring(6, 12), 80, 55);
      graphics.drawString(certName.substring(12), 80, 70);
    }

    BufferedImage bimg = null;
    try {
      bimg = ImageIO.read(new File(imgurl));
    } catch (Exception localException) {
    }
    if (bimg != null)
      graphics.drawImage(bimg, 0, 0, null);
    graphics.dispose();

    createImage(imgurl);
  }

  public static void main(String[] args)
  {
    ChartGraphics cg = new ChartGraphics();
    try {
      cg.graphicsGeneration("周杰伦", "国家注册监理工程师岗位证", "C:/7.jpg");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}