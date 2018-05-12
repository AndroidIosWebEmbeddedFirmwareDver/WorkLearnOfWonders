package com.wondersgroup.hs.healthcloud.common.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.wondersgroup.hs.healthcloud.common.util.BaseConstant;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/8/5 14:13
 */
public class BaseListResponse<T> {

    public boolean more;
    public HashMap<String, String> more_params;
    private List<T> content;
    public JSONObject extras;

    public void setContent(JSONArray content) {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Class<T> type = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
            this.content = JSON.parseArray(content.toString(), type);
        }
    }

    public <T> T getExtraData(Class<T> clazz) {
        if (extras == null) {
            return null;
        }
        return JSON.toJavaObject(extras, clazz);
    }

    @JSONField(serialize = false, deserialize = false)
    public List<T> getList() {
        return content == null ? new ArrayList<T>() : content;
    }

    public boolean isListEmpty() {
        return getList() == null || getList().isEmpty();
    }

    public int refresh(int type, BaseListResponse<T> t) {
        if (t == null) {
            if (type != BaseConstant.TYPE_NEXT) {
                more = false;
                more_params = null;
                content = null;
                extras = null;
            }
            return 0;
        }
        more = t.more;
        more_params = t.more_params;
        extras = t.extras;
        if (type == BaseConstant.TYPE_NEXT) {
            getList().addAll(t.getList());
            return t.getList().size();
        } else {
            content = t.getList();
            return 0;
        }
    }
}
