package com.wondersgroup.healthcloud.common.http.support.version;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import org.apache.commons.lang3.StringUtils;

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
public final class Version implements Comparable<Version> {
    int major;
    int minor;

    public Version(String version, boolean isFrom) {
        if (StringUtils.isBlank(version)) {
            throw new VersionMissingException();
        }
        if (StringUtils.equals("0", version)) {
            this.major = 0;
            this.minor = 0;
        } else if (StringUtils.equals("999", version)) {
            this.major = 999;
            this.minor = 999;
        } else {
            String[] versionArray = StringUtils.split(version, "\\.");
            if (versionArray.length < 2) {
                throw new RuntimeException("wrong version syntax, version must like x.y");
            }
            major = Integer.valueOf(versionArray[0]);
            minor = Integer.valueOf(versionArray[1]);
        }
    }

    @Override
    public int compareTo(Version that) {
        Preconditions.checkNotNull(that);
        return ComparisonChain.start()
                .compare(this.major, that.major)
                .compare(this.minor, that.minor)
                .result();
    }

    public int compareTo(String that) {
        return this.compareTo(new Version(that, true));
    }
}
