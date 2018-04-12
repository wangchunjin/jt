package com.epmis.sys.util.ZXingCode;

import java.awt.Color;

public class LogoConfig
{
  public static final Color DEFAULT_BORDERCOLOR = Color.blue;
  public static final int DEFAULT_BORDER = 1;
  public static final int DEFAULT_LOGOPART = 5;
  private final int border = 1;
  private final Color borderColor;
  private final int logoPart;

  public LogoConfig()
  {
    this(DEFAULT_BORDERCOLOR, 5);
  }

  public LogoConfig(Color borderColor, int logoPart)
  {
    this.borderColor = borderColor;
    this.logoPart = logoPart;
  }

  public Color getBorderColor()
  {
    return this.borderColor;
  }

  public int getBorder()
  {
    return 1;
  }

  public int getLogoPart()
  {
    return this.logoPart;
  }
}