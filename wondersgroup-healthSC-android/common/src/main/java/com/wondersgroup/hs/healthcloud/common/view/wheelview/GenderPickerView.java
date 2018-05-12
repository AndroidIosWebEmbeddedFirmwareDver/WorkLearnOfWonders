package com.wondersgroup.hs.healthcloud.common.view.wheelview;

import android.app.Activity;

import java.util.ArrayList;

/**
 * 类描述：
 * 创建人：Bob
 * 创建时间：2016/8/11 20:48
 */
public class GenderPickerView extends OptionsPickerView<String> {
    ArrayList<String> datas = new ArrayList<>();

    public GenderPickerView(Activity context) {
        super(context);
        datas.add("男");
        datas.add("女");
        setPicker(datas);
        setCyclic(false);
    }
}
