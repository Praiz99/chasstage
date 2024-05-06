package com.wckj.chasstage.common.util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import java.awt.*;

public class ITextFontUtil
{

    public ITextFontUtil()
    {
    }

    public static BaseFont getBaseFont(String font, String encode)
    {
        BaseFont bFont = null;
        try
        {
            bFont = BaseFont.createFont(font, encode, false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return bFont;
    }

    public static Font getFont(BaseFont bFont, float fontSize, int fontStyle, Color color)
    {
        if(color != null)
        {
            BaseColor bColor = new BaseColor(color.getRGB());
            return new Font(bFont, fontSize, fontStyle, bColor);
        } else
        {
            return new Font(bFont, fontSize, fontStyle);
        }
    }

    public static Font getFont(BaseFont bFont, float fontSize, int fontStyle)
    {
        return getFont(bFont, fontSize, fontStyle, null);
    }

    public static BaseFont getCnSongHBaseFont()
    {
        return getBaseFont(CHINESE_FONT_SONGTI, ENCODE_GB_H);
    }

    public static BaseFont getCnSongVBaseFont()
    {
        return getBaseFont(CHINESE_FONT_SONGTI, ENCODE_GB_V);
    }

    public static Font getCnSongHFont(float fontSize, int fontStyle, Color color)
    {
        return getFont(getCnSongHBaseFont(), fontSize, fontStyle, color);
    }

    public static Font getCnSongHFont(float fontSize, int fontStyle)
    {
        return getFont(getCnSongHBaseFont(), fontSize, fontStyle);
    }

    public static Font getCnSongVFont(float fontSize, int fontStyle, Color color)
    {
        return getFont(getCnSongVBaseFont(), fontSize, fontStyle, color);
    }

    public static Font getCnSongVFont(float fontSize, int fontStyle)
    {
        return getFont(getCnSongVBaseFont(), fontSize, fontStyle);
    }

    private static String CHINESE_FONT_SONGTI = "STSongStd-Light";
    private static String ENCODE_GB_H = "UniGB-UCS2-H";
    private static String ENCODE_GB_V = "UniGB-UCS2-V";

}

