package hack.wang.java.image.handle.demian;

import java.awt.*;

/**
 * @Description a
 * @Author
 * @Create 2018-03-23 下午2:10
 **/

public class WaterMarkEntity {

    public String pressText;
    public String fontName;
    public int fontStyle;
    public Color color;
    public int fontSize;
    public int x;
    public int y;
    public float alpha;


    public WaterMarkEntity initDefault() {
        this.pressText = "水印";
        this.fontName = "宋体";
        this.fontStyle = Font.PLAIN;
        this.color = Color.red;
        this.fontSize = 24;
        this.x = 0;
        this.y = 0;
        this.alpha = 0.5f;

        return this;
    }

    public WaterMarkEntity init(
            String pressText,
            String fontName,
            int fontStyle,
            Color color,
            int fontSize,
            int x,
            int y,
            float alpha
    ) {
        this.pressText = pressText;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.color = color;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        this.alpha = alpha;

        return this;
    }

}
