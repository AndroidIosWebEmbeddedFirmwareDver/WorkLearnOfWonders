package com.wonders.health.venus.open.user.entity;

/**
 * 类描述：
 * 创建人：hhw
 * 创建时间：2016/8/11 17:31
 */
public class SearchConfig {
    public static final int TYPE_HOME_SEARCH = 1;
    public static final int TYPE_FOOD_SEARCH = 3;
    public static final int TYPE_DEPARTMENT_SEARCH = 2;

    public int search_type = TYPE_HOME_SEARCH;
    public String history_pref_name;
    public String hint_text;
}
