/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wondersgroup.hs.healthcloud.common.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.wondersgroup.hs.healthcloud.base.R;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.zxing.camera.CameraManager;

import java.util.Collection;
import java.util.HashSet;


/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 */
public final class ViewfinderView extends View {
    private static final String TAG = "log";
    /**
     * 刷新界面的时间
     */
    private static final long ANIMATION_DELAY = 15L;
    private static final int OPAQUE = 0xFF;

    /**
     * 四个绿色边角对应的长度
     */
    private int ScreenRate = UIUtil.dip2px(20);
    /**
     * 四个绿色边角对应的宽度
     */
    private static final int CORNER_WIDTH = 3;//px
    /**
     * 扫描框中的中间线的宽度
     */
    private static final int MIDDLE_LINE_WIDTH = 3;//px

    /**
     * 扫描框中的中间线的与扫描框左右的间隙
     */
    private static final int MIDDLE_LINE_PADDING = 5;//px

    /**
     * 中间那条线每次刷新移动的距离
     */
    private static final int SPEEN_DISTANCE = 5;//px

    /**
     * 手机的屏幕密度
     */
    private static float density;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 14; //sp
    /**
     * 字体距离扫描框下面的距离
     */
    private static final int TEXT_PADDING_TOP = 30;

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 中间滑动线的最顶端位置
     */
    private int slideTop;

    /**
     * 中间滑动线的最底端位置
     */
    private int slideBottom;

    /**
     * 将扫描的二维码拍下来，这里没有这个功能，暂时不考虑
     */
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int cornerColor; //边框拐角颜色
    private final int bottomTextColor;
    private final String bottomTips;

    private final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;

    boolean isFirst;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        density = context.getResources().getDisplayMetrics().density;
//		//将像素转换成dp /////四个角的长度
//		ScreenRate = UIUtil.dip2px(20);
////		ScreenRate = (int)(20 * density);

        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        cornerColor = resources.getColor(R.color.viewfinder_corner);
        bottomTextColor = resources.getColor(R.color.tc2);
        bottomTips = resources.getString(R.string.capture_code_tips);
        resultPointColor = resources.getColor(R.color.possible_result_points);
        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
        Rect frame ;
        try {
             frame = CameraManager.get().getFramingRect();
            if (frame == null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }

        //获取屏幕的宽和高
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        //画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);


        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            //画线四个边
            paint.setColor(Color.WHITE);
            canvas.drawLine(frame.left, frame.top + 1, frame.left + frame.width(), frame.top + 1, paint); //上
            canvas.drawLine(frame.left + 1, frame.top, frame.left + 1, frame.top + frame.height(), paint); //左
            canvas.drawLine(frame.left + 1, frame.top + frame.height() - 1, frame.left + frame.width() - 1, frame.top + frame.height() - 1, paint);//下
            canvas.drawLine(frame.left + frame.width() - 1, frame.top + 1, frame.left + frame.width() - 1, frame.top + frame.height() - 1, paint);//右

            //画扫描框边上的角，总共8个部分
            paint.setColor(cornerColor);
            canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH, frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right, frame.top
                    + ScreenRate, paint);
            canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                    + ScreenRate, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - ScreenRate,
                    frame.left + CORNER_WIDTH, frame.bottom, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.bottom - CORNER_WIDTH,
                    frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom - ScreenRate,
                    frame.right, frame.bottom, paint);


            //绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }
            canvas.drawRect(frame.left + MIDDLE_LINE_PADDING, slideTop - MIDDLE_LINE_WIDTH / 2, frame.right - MIDDLE_LINE_PADDING, slideTop + MIDDLE_LINE_WIDTH / 2, paint);


            //画扫描框下面的字
            paint.setTextSize(UIUtil.sp2px(TEXT_SIZE));
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();

            float bottomY = frame.bottom + (float) TEXT_PADDING_TOP * density; //字体底部y坐标
            float fontHeight = fontMetrics.bottom - fontMetrics.top; //字体高度
            Rect targetRect = new Rect(0, frame.bottom, width, (int) (bottomY) + (int) fontHeight);
            paint.setColor(Color.TRANSPARENT);
            canvas.drawRect(targetRect, paint);
            paint.setColor(bottomTextColor);
            canvas.drawText(bottomTips, targetRect.centerX(), (float) (frame.bottom + (float) TEXT_PADDING_TOP * density), paint);


            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }


            //只刷新扫描框的内容，其他地方不刷新
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     *
     * @param barcode An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}
