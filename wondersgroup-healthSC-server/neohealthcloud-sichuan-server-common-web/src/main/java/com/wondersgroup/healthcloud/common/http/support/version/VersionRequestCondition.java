package com.wondersgroup.healthcloud.common.http.support.version;

import com.google.common.collect.ImmutableSet;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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
 * Created by zhangzhixiu on 15/11/22.
 */
public final class VersionRequestCondition implements RequestCondition<VersionRequestCondition> {

    private final Version from;
    private final Version to;
    private final Set<String> exclude;

    public VersionRequestCondition(String from, String to, String[] exclude) {
        this.from = new Version(from, true);
        this.to = new Version(to, false);
        this.exclude = ImmutableSet.copyOf(exclude);
    }

    @Override
    public VersionRequestCondition combine(VersionRequestCondition other) {
        return other;
    }

    @Override
    public VersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        String versionHeader = request.getHeader("version");
        if (VersionComparator.in(from, to, versionHeader)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(VersionRequestCondition other, HttpServletRequest request) {
        return other.from.compareTo(request.getHeader("version"));
    }
}
