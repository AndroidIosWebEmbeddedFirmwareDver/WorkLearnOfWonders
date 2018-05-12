package hack.wang.java.image.handle;

import java.awt.*;

/**
 * @Description a
 * @Author
 * @Create 2018-03-23 下午4:07
 **/

public class ColorUtil {


//        public static void main(String[] args) {
//            System.out.println(toHexFromColor(Color.BLUE));
//            System.out.println(toColorFromString(toHexFromColor(Color.BLUE)));
//        }

    /**
     * Color对象转换成字符串
     *
     * @param color Color对象
     * @return 16进制颜色字符串
     */
    private static String toHexFromColor(Color color) {
        String r, g, b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(color.getRed());
        g = Integer.toHexString(color.getGreen());
        b = Integer.toHexString(color.getBlue());
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("0xFF");
        su.append(r);
        su.append(g);
        su.append(b);
        //0xFF0000FF
        return su.toString();
    }

    /**
     * 字符串转换成Color对象
     *
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     */
    public static Color toColorFromString(String colorStr) {
        colorStr = colorStr.substring(4);
        Color color = new Color(Integer.parseInt(colorStr, 16));
        //java.awt.Color[r=0,g=0,b=255]
        return color;
    }
}
