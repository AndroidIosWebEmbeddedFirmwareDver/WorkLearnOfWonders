package hack.wang.java.image.handle;

import hack.wang.java.image.handle.demian.WaterMarkEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Description A Gener Of Block chain copyright registration certificate
 * @Author
 * @Create 2018-03-23 下午4:21
 **/

public class BlockChainCertificateGeneror {

    static final int TempleImageWidth = 595;//px
    static final int TempleImageHeight = 842;//px

    static String TEMPLE_IMAGE_FILE_PATH = "/Users/wangxiaolong/Desktop/chain/tupian.png";
    static String CENTER_IMAGE_FILE_PATH = "/Users/wangxiaolong/Desktop/chain/center_image.png";
    static int CENTER_IMAGE_FILE_WIDTH = 387;
    static int CENTER_IMAGE_FILE_HEIGHT = 258;
    static  String OUTPUT_IMAGE_FILE_PATH = TEMPLE_IMAGE_FILE_PATH.substring(0, TEMPLE_IMAGE_FILE_PATH.lastIndexOf('.') + 1) + "_block_chain_cretificate.png";


    static WaterMarkEntity waterMarkEntity_1;//头部 -> 区块链版权登记证书
    static WaterMarkEntity waterMarkEntity_2;//头部 -> Blockchain copyright registration certificate
    static WaterMarkEntity waterMarkEntity_3;//中部 -> 封面图片
    static WaterMarkEntity waterMarkEntity_4;//中部 -> 作品名称
    static WaterMarkEntity waterMarkEntity_5;//中部 -> 区块链DNA
    static WaterMarkEntity waterMarkEntity_6;//中部 -> [1231726392478123671394]
    static WaterMarkEntity waterMarkEntity_7;//中部 -> 作品区块Hash
    static WaterMarkEntity waterMarkEntity_8;//中部 -> b7ba04e1309075b8ae4f11b07f7b41a5e6132727f1791c366349ee3
    static WaterMarkEntity waterMarkEntity_9;//中部 -> 3b6e7fe8
    static WaterMarkEntity waterMarkEntity_10;//中部 -> 登记时间：2018年1月1日 上午12:22:13
    static WaterMarkEntity waterMarkEntity_11;//中部 -> 作 者：张天爱/李一亮
    static WaterMarkEntity waterMarkEntity_12;//中部 -> 著作权人：张天爱/李一亮
    static WaterMarkEntity waterMarkEntity_13;//底部 -> ©淘一设计®


    public static void initData() {
        waterMarkEntity_1 = new WaterMarkEntity().init(
                "区块链版权登记证书",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                18,
                209,
                122,
                1.0f
        );
        waterMarkEntity_2 = new WaterMarkEntity().init(
                "Blockchain copyright registration certificate",
                "LucidaCalligraphy-Italic",
                Font.PLAIN,
                ColorUtil.toColorFromString("0xFF4A5677"),
                14,
                164,
                148,
                1.0f
        );
        waterMarkEntity_3 = new WaterMarkEntity().init(
                "",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                12,
                105,
                201 - 20,
                1.0f
        );
        waterMarkEntity_4 = new WaterMarkEntity().init(
                "作品名称",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                14,
                270,
                471,
                1.0f
        );
        waterMarkEntity_5 = new WaterMarkEntity().init(
                "区块链DNA",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                18,
                252,
                510,
                1.0f
        );
        waterMarkEntity_6 = new WaterMarkEntity().init(
                "[1231726392478123671394]",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                18,
                188,
                543,
                1.0f
        );
        waterMarkEntity_7 = new WaterMarkEntity().init(
                "作品区块Hash",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                16,
                247,
                570,
                1.0f
        );
        waterMarkEntity_8 = new WaterMarkEntity().init(
                "b7ba04e1309075b8ae4f11b07f7b41a5e6132727f1791c366349ee3",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                14,
                67,
                593,
                1.0f
        );
        waterMarkEntity_9 = new WaterMarkEntity().init(
                "3b6e7fe8",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                14,
                265,
                615,
                1.0f
        );
        waterMarkEntity_10 = new WaterMarkEntity().init(
                "登记时间：2018年1月1日 上午12:22:13",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                13,
                208,
                661,
                1.0f
        );
        waterMarkEntity_11 = new WaterMarkEntity().init(
                "作 者：张天爱/李一亮",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                13,
                231,
                685,
                1.0f
        );
        waterMarkEntity_12 = new WaterMarkEntity().init(
                "著作权人：张天爱/李一亮",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                13,
                208,
                711,
                1.0f
        );
        waterMarkEntity_13 = new WaterMarkEntity().init(
                "©淘一设计®",
                "PingFangSC-Regular",
                Font.BOLD,
                ColorUtil.toColorFromString("0xFF4A5677"),
                22,
                253 - 22,
                773 + 22,
                1.0f
        );


    }

    /**
     * 画文字
     *
     * @param g
     * @param waterMarkEntity
     */
    private static void doPainText(Graphics2D g, WaterMarkEntity waterMarkEntity) {
        System.out.println("\n" + " x：" + waterMarkEntity.x + " y：" + waterMarkEntity.y);
        g.setColor(waterMarkEntity.color);
        g.setFont(new Font(waterMarkEntity.fontName, waterMarkEntity.fontStyle, waterMarkEntity.fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                waterMarkEntity.alpha));
        // 在指定坐标绘制水印文字
        g.drawString(waterMarkEntity.pressText, waterMarkEntity.x, waterMarkEntity.y);
    }

    private static void pressImage(Graphics2D g, WaterMarkEntity waterMarkEntity, String pressImg, int width, int height, int wideth_biao, int height_biao) {
        System.out.println("\n" + " x：" + waterMarkEntity.x + " y：" + waterMarkEntity.y);
        // 水印文件
        Image src_biao = null;
        try {
            src_biao = ImageIO.read(new File(pressImg));
//            int wideth_biao = src_biao.getWidth(null);
//            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    waterMarkEntity.alpha));

            g.drawImage(src_biao, waterMarkEntity.x,
                    waterMarkEntity.y, wideth_biao, height_biao, null);
//            g.drawImage(src_biao, (width - wideth_biao) / 2,
//                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getLength(String text) {
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

    public static void doCreateChainCretificate() {
        try {
            initData();
            File img = new File(TEMPLE_IMAGE_FILE_PATH);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            System.out.println("Width:" + width + " Height:" + height);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);

            waterMarkEntity_1.x = (width - (getLength(waterMarkEntity_1.pressText) * waterMarkEntity_1.fontSize)) / 2;
            waterMarkEntity_2.x = (width - (getLength(waterMarkEntity_2.pressText) * waterMarkEntity_2.fontSize)) / 2;
            waterMarkEntity_4.x = (width - (getLength(waterMarkEntity_4.pressText) * waterMarkEntity_4.fontSize)) / 2;
            waterMarkEntity_5.x = (width - (getLength(waterMarkEntity_5.pressText) * waterMarkEntity_5.fontSize)) / 2;
            waterMarkEntity_6.x = (width - (getLength(waterMarkEntity_6.pressText) * waterMarkEntity_6.fontSize)) / 2;
            waterMarkEntity_7.x = (width - (getLength(waterMarkEntity_7.pressText) * waterMarkEntity_7.fontSize)) / 2;
            waterMarkEntity_8.x = (width - (getLength(waterMarkEntity_8.pressText) * waterMarkEntity_8.fontSize)) / 2;
            waterMarkEntity_9.x = (width - (getLength(waterMarkEntity_9.pressText) * waterMarkEntity_9.fontSize)) / 2;
            waterMarkEntity_10.x = (width - (getLength(waterMarkEntity_10.pressText) * waterMarkEntity_10.fontSize)) / 2;
            waterMarkEntity_11.x = (width - (getLength(waterMarkEntity_11.pressText) * waterMarkEntity_11.fontSize)) / 2;
            waterMarkEntity_12.x = (width - (getLength(waterMarkEntity_12.pressText) * waterMarkEntity_12.fontSize)) / 2;
            waterMarkEntity_13.x = (width - (getLength(waterMarkEntity_13.pressText) * waterMarkEntity_13.fontSize)) / 2;

            doPainText(g, waterMarkEntity_1);
            doPainText(g, waterMarkEntity_2);
            pressImage(g, waterMarkEntity_3, CENTER_IMAGE_FILE_PATH, width, height, CENTER_IMAGE_FILE_WIDTH, CENTER_IMAGE_FILE_HEIGHT);
            doPainText(g, waterMarkEntity_4);
            doPainText(g, waterMarkEntity_5);
            doPainText(g, waterMarkEntity_6);
            doPainText(g, waterMarkEntity_7);
            doPainText(g, waterMarkEntity_8);
            doPainText(g, waterMarkEntity_9);
            doPainText(g, waterMarkEntity_10);
            doPainText(g, waterMarkEntity_11);
            doPainText(g, waterMarkEntity_12);
            doPainText(g, waterMarkEntity_13);

            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(OUTPUT_IMAGE_FILE_PATH));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doScaleParams() {
        int scaleLevel = 10;
        //母版放大10倍
        ImageHandleUtils.scale(TEMPLE_IMAGE_FILE_PATH, TEMPLE_IMAGE_FILE_PATH + "_scale_big@10x.png", scaleLevel, true);//测试OK
        TEMPLE_IMAGE_FILE_PATH = TEMPLE_IMAGE_FILE_PATH + "_scale_big@10x.png";
        OUTPUT_IMAGE_FILE_PATH = TEMPLE_IMAGE_FILE_PATH.substring(0, TEMPLE_IMAGE_FILE_PATH.lastIndexOf('.')) + "_block_chain_cretificate.png";
        CENTER_IMAGE_FILE_WIDTH *= 10;
        CENTER_IMAGE_FILE_HEIGHT *= 10;

        //放大参数
        waterMarkEntity_1.x *= scaleLevel;
        waterMarkEntity_1.y *= scaleLevel;
        waterMarkEntity_1.fontSize *= scaleLevel;

        waterMarkEntity_2.x *= scaleLevel;
        waterMarkEntity_2.y *= scaleLevel;
        waterMarkEntity_2.fontSize *= scaleLevel;

        waterMarkEntity_3.x *= scaleLevel;
        waterMarkEntity_3.y *= scaleLevel;
        waterMarkEntity_3.fontSize *= scaleLevel;

        waterMarkEntity_4.x *= scaleLevel;
        waterMarkEntity_4.y *= scaleLevel;
        waterMarkEntity_4.fontSize *= scaleLevel;

        waterMarkEntity_5.x *= scaleLevel;
        waterMarkEntity_5.y *= scaleLevel;
        waterMarkEntity_5.fontSize *= scaleLevel;

        waterMarkEntity_6.x *= scaleLevel;
        waterMarkEntity_6.y *= scaleLevel;
        waterMarkEntity_6.fontSize *= scaleLevel;

        waterMarkEntity_7.x *= scaleLevel;
        waterMarkEntity_7.y *= scaleLevel;
        waterMarkEntity_7.fontSize *= scaleLevel;

        waterMarkEntity_8.x *= scaleLevel;
        waterMarkEntity_8.y *= scaleLevel;
        waterMarkEntity_8.fontSize *= scaleLevel;

        waterMarkEntity_9.x *= scaleLevel;
        waterMarkEntity_9.y *= scaleLevel;
        waterMarkEntity_9.fontSize *= scaleLevel;

        waterMarkEntity_10.x *= scaleLevel;
        waterMarkEntity_10.y *= scaleLevel;
        waterMarkEntity_10.fontSize *= scaleLevel;

        waterMarkEntity_11.x *= scaleLevel;
        waterMarkEntity_11.y *= scaleLevel;
        waterMarkEntity_11.fontSize *= scaleLevel;

        waterMarkEntity_12.x *= scaleLevel;
        waterMarkEntity_12.y *= scaleLevel;
        waterMarkEntity_12.fontSize *= scaleLevel;

        waterMarkEntity_13.x *= scaleLevel;
        waterMarkEntity_13.y *= scaleLevel;
        waterMarkEntity_13.fontSize *= scaleLevel;

    }

    public static void doCreateChainCretificateFor10() {
        try {
            initData();
            doScaleParams();
            File img = new File(TEMPLE_IMAGE_FILE_PATH);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            System.out.println("Width:" + width + " Height:" + height);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);

            waterMarkEntity_1.x = (width - (getLength(waterMarkEntity_1.pressText) * waterMarkEntity_1.fontSize)) / 2;
            waterMarkEntity_2.x = (width - (getLength(waterMarkEntity_2.pressText) * waterMarkEntity_2.fontSize)) / 2;
            waterMarkEntity_4.x = (width - (getLength(waterMarkEntity_4.pressText) * waterMarkEntity_4.fontSize)) / 2;
            waterMarkEntity_5.x = (width - (getLength(waterMarkEntity_5.pressText) * waterMarkEntity_5.fontSize)) / 2;
            waterMarkEntity_6.x = (width - (getLength(waterMarkEntity_6.pressText) * waterMarkEntity_6.fontSize)) / 2;
            waterMarkEntity_7.x = (width - (getLength(waterMarkEntity_7.pressText) * waterMarkEntity_7.fontSize)) / 2;
            waterMarkEntity_8.x = (width - (getLength(waterMarkEntity_8.pressText) * waterMarkEntity_8.fontSize)) / 2;
            waterMarkEntity_9.x = (width - (getLength(waterMarkEntity_9.pressText) * waterMarkEntity_9.fontSize)) / 2;
            waterMarkEntity_10.x = (width - (getLength(waterMarkEntity_10.pressText) * waterMarkEntity_10.fontSize)) / 2;
            waterMarkEntity_11.x = (width - (getLength(waterMarkEntity_11.pressText) * waterMarkEntity_11.fontSize)) / 2;
            waterMarkEntity_12.x = (width - (getLength(waterMarkEntity_12.pressText) * waterMarkEntity_12.fontSize)) / 2;
            waterMarkEntity_13.x = (width - (getLength(waterMarkEntity_13.pressText) * waterMarkEntity_13.fontSize)) / 2;

            doPainText(g, waterMarkEntity_1);
            doPainText(g, waterMarkEntity_2);
            pressImage(g, waterMarkEntity_3, CENTER_IMAGE_FILE_PATH, width, height, CENTER_IMAGE_FILE_WIDTH, CENTER_IMAGE_FILE_HEIGHT);
            doPainText(g, waterMarkEntity_4);
            doPainText(g, waterMarkEntity_5);
            doPainText(g, waterMarkEntity_6);
            doPainText(g, waterMarkEntity_7);
            doPainText(g, waterMarkEntity_8);
            doPainText(g, waterMarkEntity_9);
            doPainText(g, waterMarkEntity_10);
            doPainText(g, waterMarkEntity_11);
            doPainText(g, waterMarkEntity_12);
            doPainText(g, waterMarkEntity_13);

            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(OUTPUT_IMAGE_FILE_PATH));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
