package com.wondersgroup.healthcloud.entity.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by nick on 2016/11/10.
 */
@Data
@XmlRootElement(name = "request")
public class InterDisPayRequest {

    private String yljgdm;
    private String klx;
    private String kh;
    private String cfhm;
    private String zfptddh;
    private String jylsh;
    private String zffs;
    private String zfje;
    private String zfsj;
    private String jzlsh;
    private String sjbh;
}
