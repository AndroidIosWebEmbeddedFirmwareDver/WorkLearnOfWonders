package com.wonders.health.venus.open.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/1/5 11:07
 */
public class ArticleTab implements Serializable {
    public boolean more;
    public HashMap<String, String> more_params;
    public String cat_id;
    public List<ArticleItem> list=new ArrayList<>();
    public String cat_name;
}
