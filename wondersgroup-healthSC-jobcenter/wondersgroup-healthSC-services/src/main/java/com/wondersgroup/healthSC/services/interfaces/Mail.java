package com.wondersgroup.healthSC.services.interfaces;

import javax.mail.MessagingException;
import java.io.File;

/**
 * Created by ys on 16/2/24.
 *
 */
public interface Mail {

    /**
     * 发送简单邮件
     * @param sendTo 收件人地址
     * @param title  邮件标题
     * @param content 邮件内容
     */
    void sendSimpleMail(String[] sendTo, String title, String content);

   /**
    *  发送带附件的邮件
    * @param sendTo 收件人地址
    * @param title  邮件标题
    * @param content 邮件内容
    * @param attachment 附件
    */
    void sendAttachmentsMail(String[] sendTo, String title, String content, File attachment) throws MessagingException ;

}
