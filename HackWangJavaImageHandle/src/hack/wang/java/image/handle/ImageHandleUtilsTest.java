package hack.wang.java.image.handle;

import hack.wang.java.image.handle.demian.WaterMarkEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Description a
 * @Author
 * @Create 2018-03-23 下午1:32
 **/

public class ImageHandleUtilsTest {

    public static final String HANDLED_IMAGE_URL = "/Users/wangxiaolong/Desktop/tupian.png";
    public static final int IMAGE_MARGIN_WIDTH = 0;
    public static final int IMAGE_MARGIN_HEIGHT = 60*12;
    public static final int BOTTOM_MARGIN_HEIGHT = 412;
    public static final int MAGIN_OF_TEXT = 12*5;
    public static  final boolean flag=true;

    public static void test() {
        String outPutResultImage = HANDLED_IMAGE_URL.substring(0, HANDLED_IMAGE_URL.lastIndexOf('.'));
//        // 1-缩放图像：
//        // 方法一：按比例缩放
//        //        放大2倍
//        ImageHandleUtils.scale(HANDLED_IMAGE_URL, outPutResultImage+"_scale_big@12x.png", 12, true);//测试OK
//        //        缩小2倍
//        ImageHandleUtils.scale(HANDLED_IMAGE_URL, outPutResultImage+"_scale_small@12x.png", 12, false);//测试OK

//        // 2-切割图像：
//        // 方法一：按指定起点坐标和宽高切割
//        ImageHandleUtils.cut(HANDLED_IMAGE_URL, outPutResultImage+"_cut1.jpg", 0, 0, 400, 400 );//测试OK
//        // 方法二：指定切片的行数和列数
//        ImageHandleUtils.cut2(HANDLED_IMAGE_URL, outPutResultImage+"_cut2.jpg", 2, 2 );//测试OK
//        // 方法三：指定切片的宽度和高度
//        ImageHandleUtils.cut3(HANDLED_IMAGE_URL, outPutResultImage+"_cut3.jpg", 300, 300 );//测试OK

//        // 3-图像类型转换：
//        ImageHandleUtils.convert(HANDLED_IMAGE_URL, "GIF", outPutResultImage+"abc_convert.gif");//测试OK

        // 4-彩色转黑白：
//        ImageHandleUtils.gray(HANDLED_IMAGE_URL, outPutResultImage+"_gray.png");//测试OK

        // 5-给图片添加文字水印：
//        int width = ImageHandleUtils.getImage(HANDLED_IMAGE_URL).getWidth(null);
//        int height = ImageHandleUtils.getImage(HANDLED_IMAGE_URL).getHeight(null);
//        System.out.println("Width:" + width + " Height:" + height);
//
//
//        WaterMarkEntity waterMarkEntity = new WaterMarkEntity().initDefault();
        // 方法一：
//        ImageHandleUtils.pressText(waterMarkEntity.pressText, HANDLED_IMAGE_URL, outPutResultImage + "_abc_pressText.jpg",
//                waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.color, waterMarkEntity.fontSize, waterMarkEntity.x, waterMarkEntity.y, waterMarkEntity.alpha);//测试OK
//
//        //最右下角
//        waterMarkEntity.x=width-IMAGE_MARGIN_WIDTH-waterMarkEntity.fontSize*waterMarkEntity.pressText.length();
//        waterMarkEntity.y=height-IMAGE_MARGIN_HEIGHT-waterMarkEntity.fontSize;
//
//        System.out.println("X:"+waterMarkEntity.x+" Y:"+waterMarkEntity.y);
//        ImageHandleUtils.pressText(waterMarkEntity.pressText, HANDLED_IMAGE_URL, outPutResultImage + "_abc_pressText1.jpg",
//                        waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.color, waterMarkEntity.fontSize, waterMarkEntity.x, waterMarkEntity.y, waterMarkEntity.alpha);//测试OK
//
//
//        //最左下角
//        waterMarkEntity.x=0+IMAGE_MARGIN_WIDTH;
//        waterMarkEntity.y=height-IMAGE_MARGIN_HEIGHT-waterMarkEntity.fontSize;
//
//        System.out.println("X:"+waterMarkEntity.x+" Y:"+waterMarkEntity.y);
//        ImageHandleUtils.pressText(waterMarkEntity.pressText, HANDLED_IMAGE_URL, outPutResultImage + "_abc_pressText2.jpg",
//                waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.color, waterMarkEntity.fontSize, waterMarkEntity.x, waterMarkEntity.y, waterMarkEntity.alpha);//测试OK
//
//
//        //最右上角
//        waterMarkEntity.x=width-IMAGE_MARGIN_WIDTH-waterMarkEntity.fontSize*waterMarkEntity.pressText.length();
//        waterMarkEntity.y=0+IMAGE_MARGIN_HEIGHT+waterMarkEntity.fontSize;
//
//        System.out.println("X:"+waterMarkEntity.x+" Y:"+waterMarkEntity.y);
//        ImageHandleUtils.pressText(waterMarkEntity.pressText, HANDLED_IMAGE_URL, outPutResultImage + "_abc_pressText3.jpg",
//                waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.color, waterMarkEntity.fontSize, waterMarkEntity.x, waterMarkEntity.y, waterMarkEntity.alpha);//测试OK
//
//        //最左上角
//        waterMarkEntity.x=0+IMAGE_MARGIN_WIDTH;
//        waterMarkEntity.y=0+IMAGE_MARGIN_HEIGHT+waterMarkEntity.fontSize;
//
//        System.out.println("X:"+waterMarkEntity.x+" Y:"+waterMarkEntity.y);
//        ImageHandleUtils.pressText(waterMarkEntity.pressText, HANDLED_IMAGE_URL, outPutResultImage + "_abc_pressText4.jpg",
//                waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.color, waterMarkEntity.fontSize, waterMarkEntity.x, waterMarkEntity.y, waterMarkEntity.alpha);//测试OK
//
//


//        // 6-给图片添加图片水印：
//        ImageHandleUtils.pressImage("/Users/wangxiaolong/Desktop/csdn_cs_qr.png",HANDLED_IMAGE_URL ,outPutResultImage+"_abc_pressImage.jpg", 0, 0, 1.0f);//测试OK

        if(flag) {

            String header_title1 = "";
            String header_title2 = "Block chain copyright registration certificate";

            String srcImageFile = HANDLED_IMAGE_URL;
            String destImageFile = outPutResultImage + "_output.png";

            try {
                File img = new File(srcImageFile);
                Image src = ImageIO.read(img);
                int width = src.getWidth(null);
                int height = src.getHeight(null);
                System.out.println("Width:" + width + " Height:" + height);

                //1.header_text1
                WaterMarkEntity headerTitle1Wenty = new WaterMarkEntity().init(
                        "区块链版权登记证书",
                        "PingFangSC-Regular",
                        Font.BOLD,
                        ColorUtil.toColorFromString("0xFF4A5677"),
                        20*10,
                        0,
                        0,
                        0.8f
                );
//                (width - (getLength(pressText) * fontSize)) / 2 + x

                headerTitle1Wenty.x = (width - (getLength(headerTitle1Wenty.pressText) * headerTitle1Wenty.fontSize)) / 2;
                headerTitle1Wenty.y = IMAGE_MARGIN_HEIGHT * 2 + headerTitle1Wenty.fontSize;

                //2.header_text1
                WaterMarkEntity headerTitle2Wenty = new WaterMarkEntity().init(
                        "Block chain copyright registration certificate",
                        "LucidaCalligraphy-Italic",
                        Font.ITALIC,
                        ColorUtil.toColorFromString("0xFF4A5677"),
                        20*10,
                        0,
                        0,
                        0.8f
                );
                headerTitle2Wenty.x =(width - (getLength(headerTitle2Wenty.pressText) * headerTitle2Wenty.fontSize)) / 2;
                headerTitle2Wenty.y = headerTitle1Wenty.y + MAGIN_OF_TEXT + headerTitle2Wenty.fontSize;






                //n.bottom_text1
                WaterMarkEntity bottomTitle1Wenty = new WaterMarkEntity().init(
                        "©淘一设计®",
                        "宋体",
                        Font.BOLD,
                        ColorUtil.toColorFromString("0xFF4A5677"),
                        20*10,
                        0,
                        0,
                        0.8f
                );
                bottomTitle1Wenty.x = (width - (getLength(bottomTitle1Wenty.pressText) * bottomTitle1Wenty.fontSize)) / 2;
                bottomTitle1Wenty.y = height-BOTTOM_MARGIN_HEIGHT-bottomTitle1Wenty.fontSize;


                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);

                Graphics2D g = image.createGraphics();
                g.drawImage(src, 0, 0, width, height, null);

                doPainText(g, headerTitle1Wenty);
                doPainText(g, headerTitle2Wenty);
                doPainText(g, bottomTitle1Wenty);

                g.dispose();
                ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void doPainText(Graphics2D g,WaterMarkEntity waterMarkEntity){
        System.out.println("\n"+" x："+waterMarkEntity.x+" y："+waterMarkEntity.y);
        g.setColor(waterMarkEntity.color);
        g.setFont(new Font(waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                waterMarkEntity.alpha));
        // 在指定坐标绘制水印文字
        g.drawString(waterMarkEntity.pressText, waterMarkEntity.x, waterMarkEntity.y);
    }

    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }



}
