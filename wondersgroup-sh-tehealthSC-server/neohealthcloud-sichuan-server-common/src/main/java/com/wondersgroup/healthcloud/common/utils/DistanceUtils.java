package com.wondersgroup.healthcloud.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by zhangzhixiu on 15/8/19.
 */
public class DistanceUtils {
    private static DecimalFormat d = new DecimalFormat("##");

    static {
        d.setRoundingMode(RoundingMode.UP);
    }

    /**
     * @param lat1 纬度
     * @param lng1 经度
     * @param lat2
     * @param lng2
     * @return
     */
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (earthRadius * c);
    }

    public static String getDistance(Double x1, Double y1, Double x2, Double y2) {
        Double distance = DistanceUtils.distFrom(x1, y1, x2, y2);
        if (distance >= 1000d) {
            return d.format(distance / 1000d) + "公里";
        } else {
            return d.format(distance) + "米";
        }
    }

}
