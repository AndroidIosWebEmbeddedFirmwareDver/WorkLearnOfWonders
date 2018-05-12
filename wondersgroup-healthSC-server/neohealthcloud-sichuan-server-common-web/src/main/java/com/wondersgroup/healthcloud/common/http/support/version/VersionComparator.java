package com.wondersgroup.healthcloud.common.http.support.version;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 15/11/23.
 */
public final class VersionComparator {
    public static boolean in(Version from, Version to, String version) {
        int compareWithFrom = from.compareTo(version);
        int compareWithTo = to.compareTo(version);
        return compareWithFrom < 1 && compareWithTo > -1;// from<=version<=to
    }

    public static boolean conflict(VersionRange v1, VersionRange v2) {
        Version v1from = new Version(v1.from(), true);
        Version v1to = new Version(v1.to(), false);
        boolean conflictionV1 = in(v1from, v1to, v2.from()) || in(v1from, v1to, v2.to());
        if (conflictionV1) {
            return true;
        }
        Version v2from = new Version(v2.from(), true);
        Version v2to = new Version(v2.to(), false);
        return in(v2from, v2to, v1.from()) || in(v2from, v2to, v1.to());
    }
}
