package com.wondersgroup.healthcloud.push.util;

import java.util.HashMap;

/**
 * Map链式调用，map.putObj(k,v).putObj(k,v).putObj(...)
 *
 * @author jialing.yao  2016年5月13日
 */
public class MapHelper<K, V> extends HashMap<K, V> {
    /**
     * @fields serialVersionUID
     */
    private static final long serialVersionUID = -281484194745034898L;

    //构造实例
    public static MapHelper<String, Object> builder() {
        return new MapHelper<String, Object>();
    }

    public MapHelper<K, V> putObj(K key, V value) {
        super.put(key, value);
        return this;
    }
}
