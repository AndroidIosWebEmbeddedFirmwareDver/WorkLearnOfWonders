package com.wondersgroup.healthcloud.common.http.support.misc;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

/**
 * Created by zhaozhenxing on 2016/11/7.
 */
public class XMLModelAndViewBuilder {
    public static ModelAndView build(Object object) {
        MappingJackson2XmlView view = new MappingJackson2XmlView();
        ModelAndView mav = new ModelAndView(view);
        mav.addObject(object);
        return mav;
    }
}
