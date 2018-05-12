package com.wondersgroup.hs.healthcloud.common.tutorial;

import com.wondersgroup.hs.healthcloud.base.R;

/**
 * Created by angelo on 2015/6/17.
 */
public class DataConfig {
    private final int pageNum;
    private int[] backgroundColors;
    private int[] imageResources;
    private int[] backgroundResources;
    private String[] titles; // 标题
    private String[] contents; // 副标题
    private int[] headTextColors; // 标题字体颜色
    private int[] contentTextColors; // 副标题字体颜色
    private int[] headTextSizes; // 标题字体大小
    private int[] contentTextSizes; // 副标题字体大小
    private int IndicatorColor = R.color.tc3;
    private int IndicatorSelectColor =R.color.tutorial_indicator_selected;

    public int getIndicatorColor() {
        return IndicatorColor;
    }

    public void setIndicatorColor(int indicatorColor) {
        IndicatorColor = indicatorColor;
    }

    public int getIndicatorSelectColor() {
        return IndicatorSelectColor;
    }

    public void setIndicatorSelectColor(int indicatorSelectColor) {
        IndicatorSelectColor = indicatorSelectColor;
    }

    //test
    public DataConfig(int pageNum) {
        this.pageNum = pageNum;
//        backgroundResources = new int[pageNum];
//        backgroundColors = new int[pageNum];
//        imageResources = new int[pageNum];
//        titles = new String[pageNum];
//        contents = new String[pageNum];
    }

    public int getPageNum() {
        return pageNum;
    }

    public int[] getBackgroundColors() {
        return backgroundColors;
    }

    public void setBackgroundColors(int[] backgroundColors) {
        if (backgroundColors.length < pageNum) {
            System.arraycopy(backgroundColors, 0, this.backgroundColors, 0, backgroundColors.length);
            return;
        }
        this.backgroundColors = backgroundColors;
    }

    public int[] getImageResources() {
        return imageResources;
    }

    public void setImageResources(int[] imageResources) {
        if (imageResources.length < pageNum) {
            System.arraycopy(imageResources, 0, this.imageResources, 0, imageResources.length);
            return;
        }
        this.imageResources = imageResources;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        if (titles.length < pageNum) {
            System.arraycopy(titles, 0, this.titles, 0, titles.length);
            return;
        }
        this.titles = titles;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        if (contents.length < pageNum) {
            System.arraycopy(contents, 0, this.contents, 0, contents.length);
            return;
        }
        this.contents = contents;
    }

    public int[] getBackgroundResources() {
        return backgroundResources;
    }

    public void setBackgroundResources(int[] backgroundResources) {
        if (backgroundResources.length < pageNum) {
            System.arraycopy(backgroundResources, 0, this.backgroundResources, 0, backgroundResources.length);
            return;
        }
        this.backgroundResources = backgroundResources;
    }

    public int[] getHeadTextColors() {
        return headTextColors;
    }

    public void setHeadTextColors(int[] headTextColors) {
        if (headTextColors.length < pageNum) {
            System.arraycopy(headTextColors, 0, this.headTextColors, 0, headTextColors.length);
            return;
        }
        this.headTextColors = headTextColors;
    }

    public int[] getContentTextColors() {
        return contentTextColors;
    }

    public void setContentTextColors(int[] contentTextColors) {
        if (contentTextColors.length < pageNum) {
            System.arraycopy(contentTextColors, 0, this.contentTextColors, 0, contentTextColors.length);
            return;
        }
        this.contentTextColors = contentTextColors;
    }

    public int[] getHeadTextSizes() {
        return headTextSizes;
    }

    public void setHeadTextSizes(int[] headTextSizes) {
        if (headTextSizes.length < pageNum) {
            System.arraycopy(headTextSizes, 0, this.headTextSizes, 0, headTextSizes.length);
            return;
        }
        this.headTextSizes = headTextSizes;
    }

    public int[] getContentTextSizes() {
        return contentTextSizes;
    }

    public void setContentTextSizes(int[] contentTextSizes) {
        if (contentTextSizes.length < pageNum) {
            System.arraycopy(contentTextSizes, 0, this.contentTextSizes, 0, contentTextSizes.length);
            return;
        }
        this.contentTextSizes = contentTextSizes;
    }
}
