package com.epmis.sys.util.ZXingCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ZXingCode
{
  public static void main(String[] args)
    throws WriterException
  {
  }

  public static void CreateCodePic(String content, String logoPath, String outPath, int width, int height)
  {
    try
    {
      File file = new File(outPath);

      ZXingCode zp = new ZXingCode();

      BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, width, height, zp.getDecodeHintType());

      ImageIO.write(bim, "jpg", file);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void addLogo_QRCode(File qrPic, File logoPic, LogoConfig logoConfig, String CodePath)
  {
    try
    {
      System.out.println("加logo开始！");
      if ((!qrPic.isFile()) || (!logoPic.isFile()))
      {
        System.out.print("file not find !");
        System.exit(0);
      }
      System.out.println(qrPic.getPath());

      BufferedImage image = null;
      try {
        System.out.println("11");
        image = ImageIO.read(qrPic);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
      System.out.println("12！");
      Graphics2D g = image.createGraphics();
      System.out.println("2");

      BufferedImage logo = ImageIO.read(logoPic);

      int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? image.getWidth() * 2 / 10 : logo.getWidth(null);
      int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? image.getHeight() * 2 / 10 : logo.getWidth(null);

      System.out.println("3！");
      int x = (image.getWidth() - widthLogo) / 2;
      int y = (image.getHeight() - heightLogo) / 2;

      g.drawImage(logo, x, y, widthLogo, heightLogo, null);
      g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
      System.out.println("4！");
      g.setStroke(new BasicStroke(logoConfig.getBorder()));
      System.out.println("5！");
      g.setColor(logoConfig.getBorderColor());
      g.drawRect(x, y, widthLogo, heightLogo);
      System.out.println("6！");
      g.dispose();
      logo.flush();
      image.flush();
      System.out.println("7！");
      ImageIO.write(image, "jpg", new File(CodePath));
    }
    catch (Exception e)
    {
      System.out.println("报异常！");
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }

  public void parseQR_CODEImage(File file)
  {
    try
    {
      MultiFormatReader formatReader = new MultiFormatReader();

      if (!file.exists())
      {
        return;
      }

      BufferedImage image = ImageIO.read(file);

      LuminanceSource source = new BufferedImageLuminanceSource(image);
      Binarizer binarizer = new HybridBinarizer(source);
      BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

      Map hints = new HashMap();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

      Result result = formatReader.decode(binaryBitmap, hints);

      System.out.println("result = " + result.toString());
      System.out.println("resultFormat = " + result.getBarcodeFormat());
      System.out.println("resultText = " + result.getText());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat, File file)
  {
    try
    {
      if ((file == null) || (file.getName().trim().isEmpty()))
      {
        throw new IllegalArgumentException("文件异常，或扩展名有问题！");
      }

      BufferedImage bi = fileToBufferedImage(bm);
      ImageIO.write(bi, "apng", file);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public BufferedImage fileToBufferedImage(BitMatrix bm)
  {
    BufferedImage image = null;
    try
    {
      int w = bm.getWidth(); int h = bm.getHeight();
      image = new BufferedImage(w, h, 1);

      for (int x = 0; x < w; x++)
      {
        for (int y = 0; y < h; y++)
        {
          image.setRGB(x, y, bm.get(x, y) ? -16777216 : -1);
        }
      }

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return image;
  }

  public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
  {
    MultiFormatWriter multiFormatWriter = null;
    BitMatrix bm = null;
    BufferedImage image = null;
    try
    {
      multiFormatWriter = new MultiFormatWriter();

      bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);

      int w = bm.getWidth();
      int h = bm.getHeight();
      image = new BufferedImage(w, h, 1);

      for (int x = 0; x < w; x++)
      {
        for (int y = 0; y < h; y++)
        {
          image.setRGB(x, y, bm.get(x, y) ? -16777216 : -1);
        }
      }
    }
    catch (WriterException e)
    {
      e.printStackTrace();
    }
    return image;
  }

  public Map<EncodeHintType, Object> getDecodeHintType()
  {
    Map hints = new HashMap();

    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
    hints.put(EncodeHintType.MAX_SIZE, Integer.valueOf(350));
    hints.put(EncodeHintType.MIN_SIZE, Integer.valueOf(50));

    return hints;
  }
}