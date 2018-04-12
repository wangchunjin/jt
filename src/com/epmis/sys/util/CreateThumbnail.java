package com.epmis.sys.util;

import com.jhlabs.image.InvertFilter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class CreateThumbnail
{
  public static void main(String[] args)
    throws Exception
  {
    saveImageAsJpg("C:/a.jpg", "C:/b.jpg", 300, 300);
  }

  public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight)
    throws Exception
  {
    BufferedImage srcImage = null;

    String imgType = "JPEG";
    if (fromFileStr.toLowerCase().endsWith(".png"))
      imgType = "PNG";
    else if (fromFileStr.toLowerCase().endsWith(".bmp"))
      imgType = "BMP";
    else if (fromFileStr.toLowerCase().endsWith(".gif")) {
      imgType = "GIF";
    }

    File saveFile = new File(saveToFileStr);
    File fromFile = new File(fromFileStr);
    try
    {
      srcImage = ImageIO.read(fromFile);
    }
    catch (IIOException e) {
      try {
        String rgbFile = cmyk2rgb(fromFileStr);
        fromFile = new File(rgbFile);
        srcImage = ImageIO.read(fromFile);
        if ((width > 0) && (hight > 0) && (width * hight < srcImage.getWidth() * srcImage.getHeight()))
        {
          srcImage = resize(srcImage, width, hight);
          ImageIO.write(srcImage, imgType, saveFile);
        }
        else if ((width > 0) && (hight > 0) && (width * hight > srcImage.getWidth() * srcImage.getHeight()))
        {
          FileUtil.copyFile(fromFileStr, saveToFileStr);
        }
      }
      catch (IOException e2)
      {
        System.out.println(e2.getMessage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    try
    {
      if ((width > 0) && (hight > 0) && (width * hight < srcImage.getWidth() * srcImage.getHeight()))
      {
        srcImage = resize(srcImage, width, hight);
        ImageIO.write(srcImage, imgType, saveFile);
      } else if ((width > 0) && (hight > 0) && (width * hight > srcImage.getWidth() * srcImage.getHeight()))
      {
        FileUtil.copyFile(fromFileStr, saveToFileStr);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static BufferedImage resize(BufferedImage source, int targetW, int targetH)
  {
    int type = source.getType();
    BufferedImage target = null;
    double sx = targetW / source.getWidth();
    double sy = targetH / source.getHeight();

    if (sx > sy)
    {
      sx = sy;
      targetW = (int)(sx * source.getWidth());
    } else {
      sy = sx;
      targetH = (int)(sy * source.getHeight());
    }
    if (type == 0) {
      ColorModel cm = source.getColorModel();
      WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
      boolean alphaPremultiplied = cm.isAlphaPremultiplied();
      target = new BufferedImage(cm, raster, alphaPremultiplied, null);
    } else {
      target = new BufferedImage(targetW, targetH, type);
    }Graphics2D g = target.createGraphics();

    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
    g.dispose();
    return target;
  }

  public static String cmyk2rgb(String filename)
    throws IOException
  {
    String format = "jpg";
    String rgbFilename = filename;
    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(filename));
    BufferedImage image = decoder.decodeAsBufferedImage();
    if (image != null)
    {
      int colorSpaceType = image.getColorModel().getColorSpace().getType();
      if (colorSpaceType != 9)
      {
        BufferedImage rgbImage = 
          new BufferedImage(
          image.getWidth(), image.getHeight(), 5);
        ColorConvertOp op = new ColorConvertOp(null);
        op.filter(image, rgbImage);
        ImageIO.write(rgbImage, format, new File(rgbFilename));
        BufferedImage img = ImageIO.read(new File(rgbFilename));
        System.out.println(img.getColorModel().getColorSpace().getType());
        BufferedImage k = new BufferedImage(image.getWidth(), image.getHeight(), 1);
        BufferedImage r = new InvertFilter().filter(image, k);
        ImageIO.write(r, "jpg", new File(rgbFilename));
      }
    }
    return rgbFilename;
  }
}