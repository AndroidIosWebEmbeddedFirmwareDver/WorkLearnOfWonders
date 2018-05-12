package com.wondersgroup.healthSC.common.json;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhixiu on 6/26/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonListResponseEntity<T> {

    private int code;
    private String msg;
    private ListData<T> data;

    public JsonListResponseEntity() {
        this.code = 0;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class ListData<T> {
        public Boolean more;
        @JsonProperty("more_params")
        public MoreParams moreParams;
        public Map<String, Object> extras;
        public List<T> content;

        public ListData() {
            this.more = false;
            this.content = Lists.newArrayList();
        }

        public ListData(List<T> content) {
            this.more = false;
            this.content = content;
        }

        public ListData(List<T> content, Boolean more, String order, String flag) {
            this.more = more;
            if (order != null || flag != null) {
                this.moreParams = new MoreParams(order, flag);
            }
            this.content = content;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class MoreParams {
        public String order;
        public String flag;

        public MoreParams(String order, String flag) {
            this.order = order;
            this.flag = flag;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ListData<T> getData() {
        return data;
    }

    public void setData(ListData<T> data) {
        this.data = data;
    }

    @JsonIgnore
    public void setContent(List<T> content) {
        this.data = new ListData<>(content);
    }

    @JsonIgnore
    public void setContent(List<T> data, Boolean more, String order, String flag) {
        this.data = new ListData<>(data, more, order, flag);
    }

    @JsonIgnore
    public void addExtra(String key, String value) {
        if (this.data.extras == null) {
            this.data.extras = Maps.newHashMap();
        }
        this.data.extras.put(key, value);
    }

    @JsonIgnore
    public void setExtras(Map<String, Object> extras) {
        if (data == null) {
            this.data = new ListData<>();
        }
        this.data.extras = extras;
    }
}
