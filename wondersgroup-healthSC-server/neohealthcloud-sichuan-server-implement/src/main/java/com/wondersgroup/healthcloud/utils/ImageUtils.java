package com.wondersgroup.healthcloud.utils;


import com.squareup.okhttp.Request;
import com.wondersgroup.common.http.HttpRequestExecutorManager;
import com.wondersgroup.common.http.builder.RequestBuilder;
import com.wondersgroup.common.http.entity.JsonObjectResponseWrapper;
import com.wondersgroup.common.image.utils.ImagePath;
import com.wondersgroup.common.image.utils.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangzhixiu on 15/5/15.
 */
@Component
public class ImageUtils {

    @Autowired
    private HttpRequestExecutorManager httpRequestExecutorManager;

    public Float getImgRatio(Image img) {
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();
            BigDecimal b = new BigDecimal((float) height / (float) width);
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        return 0.0f;
    }

    public String getBigThumb(Image image, String screenWidth) {
        Assert.notNull(image);
        Assert.notNull(screenWidth);
        int width = Integer.valueOf(screenWidth);
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        BigDecimal b;
        Float imgRatio;
        if (imgWidth >= imgHeight) {
            b = new BigDecimal((float) imgHeight / (float) imgWidth);
            imgRatio = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            imgWidth = width / 3 * 2;
            imgHeight = (int) (imgWidth * imgRatio);
        } else if (imgHeight > imgWidth) {
            b = new BigDecimal((float) imgWidth / (float) imgHeight);
            imgRatio = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            imgHeight = width / 3 * 2;
            imgWidth = (int) (imgHeight * imgRatio);
        }
        return image.getUrl() + ImagePath.thumbnailPostfix(String.valueOf(imgWidth), String.valueOf(imgHeight));
    }

    public String getSquareThumb(Image image, String screenWidth) {
        Assert.notNull(image);
        Assert.notNull(screenWidth);
        int width = Integer.valueOf(screenWidth);
        int length = width / 3;
        return image.getUrl() + ImagePath.cutCenterPostfix(String.valueOf(length), String.valueOf(length));
    }

    public String getSquareThumb(String imageUrl, String screenWidth) {
        Assert.notNull(imageUrl);
        Assert.notNull(screenWidth);
        int width = Integer.valueOf(screenWidth);
        int length = width / 3;
        return imageUrl + ImagePath.cutCenterPostfix(String.valueOf(length), String.valueOf(length));
    }

    public Integer getUsefulImgWidth(Image image, String screenWidth) {
        Assert.notNull(image);
        Assert.notNull(screenWidth);
        int width = Integer.valueOf(screenWidth) / 3 * 2;
        return image.getWidth() > width ? width : image.getWidth();
    }


    public Integer getUsefulImgHeight(Image image, String screenWidth) {
        Assert.notNull(image);
        Assert.notNull(screenWidth);
        int width = Integer.valueOf(screenWidth) / 3 * 2;
        if (image.getWidth() > width) {
            Float imgRatio = getImgRatio(image);
            return (int) (width * imgRatio);
        }
        return image.getHeight();
    }

    public Image getImage(String imgUrl) {
        Image img = null;
        try {
            Request request = new RequestBuilder().get().url(imgUrl + "?imageInfo").build();
            JsonObjectResponseWrapper<Image> response = (JsonObjectResponseWrapper<Image>) httpRequestExecutorManager.newCall(request).run().as(JsonObjectResponseWrapper.class);
            img = response.withObjectType(Image.class).convertBody();
            if (img != null) {
                img.setUrl(imgUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //ignore
        }
        return img;
    }


    public static void main(String[] args) {
        ImageUploader.upload("app", "icon_mytask_1.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\1.png"));
        ImageUploader.upload("app", "icon_mytask_1f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\1f.png"));
        ImageUploader.upload("app", "icon_mytask_4.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\4.png"));
        ImageUploader.upload("app", "icon_mytask_4f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\4f.png"));
        ImageUploader.upload("app", "icon_mytask_5.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\5.png"));
        ImageUploader.upload("app", "icon_mytask_5f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\5f.png"));
        ImageUploader.upload("app", "icon_mytask_8.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\8.png"));
        ImageUploader.upload("app", "icon_mytask_8f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\8f.png"));
        ImageUploader.upload("app", "icon_mytask_9.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\9.png"));
        ImageUploader.upload("app", "icon_mytask_9f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\9f.png"));
        ImageUploader.upload("app", "icon_mytask_11.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\11.png"));
        ImageUploader.upload("app", "icon_mytask_11f.png", new File("C:\\Users\\Yoda\\Desktop\\sss\\11f.png"));

    }

    public static class Image implements Serializable {
        private static final long serialVersionUID = -6576358149375258483L;
        private String url;
        private String format;
        private String colorModel;
        private String orientation;
        private Integer width;
        private Integer height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getColorModel() {
            return colorModel;
        }

        public void setColorModel(String colorModel) {
            this.colorModel = colorModel;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }
    }
}
