package com.wondersgroup.healthSC.services.impl;

import com.wondersgroup.healthSC.common.util.DateFormatter;
import com.wondersgroup.healthSC.services.impl.dto.PayFlowDTO;
import com.wondersgroup.healthSC.services.utils.ExcelParse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ys on 2016/11/08.
 *
 */
@Component
public class CustomEmailsSender {

    private static final Logger logger = LoggerFactory.getLogger(CustomEmailsSender.class);

    @Value("${spring.mail.sendFrom}")
    private String sendFrom;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PayFlowService payFlowService;

    /**
     * 默认发送昨天的数据
     */
    public void sendOrderReportingDataToHospital() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date lastDay = calendar.getTime();
        this.sendOrderReportingDataToHospital(lastDay, lastDay);
    }

    /**
     * 可指定发送的日期范围
     * @param startDate
     * @param endDate
     */
    public void sendOrderReportingDataToHospital(Date startDate, Date endDate){
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdfDay.format(new Date());
        String sendTimeRangeShow = DateFormatter.format(startDate, "yyyy年MM月dd日 00:00:00")
                + " - " + DateFormatter.format(startDate, "yyyy年MM月dd日 23:59:59");

        String sql_openEmails = "select id, hospital_code,hospital_name,custom_emails from tb_hospital_info hos " +
                " where hos.is_open_emails=1 and del_flag='0'";
        List<Map<String, Object>> hospitals = jdbcTemplate.queryForList(sql_openEmails);
        if (null == hospitals || hospitals.isEmpty()){
            logger.info("get empty hospital for send order pay reporting!");
            return;
        }
        String[] adminEmails = getDebugAdminEmails();
        for (Map<String, Object> hospital : hospitals){
            String custom_emails = hospital.get("custom_emails").toString();
            if (StringUtils.isEmpty(custom_emails)){
                continue;
            }
            String[] sendToEmails = null != adminEmails && adminEmails.length > 0 ? adminEmails : StringUtils.split(custom_emails, ",");
            String hospitalCode = hospital.get("hospital_code").toString();
            String hospitalName = hospital.get("hospital_name").toString();
            List<PayFlowDTO> payFlowDTOs = payFlowService.queryPayFlowByHosCode(hospitalCode, startDate, endDate);
            if (null == payFlowDTOs || payFlowDTOs.isEmpty()){
                continue;
            }
            String[] titles = new String[]{"交易时间", "订单号", "支付金额", "柜台", "客户名称", "收款商户", "订单状态"};
            List<ArrayList<Object>> dataRows = new ArrayList<>();
            for (PayFlowDTO payFlowDTO : payFlowDTOs){
                ArrayList<Object> dataRow = new ArrayList<>();
                dataRow.add(payFlowDTO.getUpdate_time());
                dataRow.add(payFlowDTO.getShow_order_id());
                dataRow.add(payFlowDTO.getCost());
                dataRow.add(payFlowDTO.getPos_id());
                dataRow.add(payFlowDTO.getHos_name());
                dataRow.add(payFlowDTO.getMerchant_id());
                dataRow.add(payFlowDTO.getStatus());
                dataRows.add(dataRow);
            }
            File writeFile;
            String fileName = today + "_" + hospitalCode;
            try {
                writeFile = ExcelParse.writerAsxlsxExcel(fileName, dataRows, titles);
            } catch (Exception e) {
                logger.info("error : "+e.getMessage());
                continue;
            }
            logger.info("create data file ok : [" + fileName + "]");

            String title = sendTimeRangeShow + " 健康双流APP建行支付交易流水";
            String content = sendTimeRangeShow + " 健康双流APP建行支付交易流水，见附件。";
            content = getEmailContext(hospitalName, content);
            try {
                sendAttachmentsMail(sendToEmails, title, content, writeFile);
            } catch (MessagingException e) {
                e.printStackTrace();
            }finally {
                if (writeFile != null && writeFile.exists()){
                    writeFile.delete();
                }
            }
            logger.info("send email ok! [ "+sendTimeRangeShow+" ]");
            logger.info(String.format("send email to %s, emails:%s", hospitalName, sendToEmails.toString()));
        }
    }

    private String getEmailContext(String hospitalName, String context){
        String newContxt = "<html>\n" +
                "  <head>\n" +
                "    <meta http-equiv='content-type' content='text/html; charset=utf-8'>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <br>\n" +
                "    %s:\n" +
                "    <br>\n" +
                "    <div style='text-indent:2em'>\n" +
                "      <p>%s</p>\n" +
                "    </div>\n" +
                "    谢谢！\n" +
                "    <br><br><br><br>\n" +
                "    <span style='font-family: Arial;color:RGB(128,128,128)'>\n" +
                "      ----------------------------------------------------------------------------------------------------<br>\n" +
                "      指导单位：四川省成都市双流县卫生和计划生育委员会  <br>\n" +
                "      运营服务：万达信息股份有限公司\t四川万达健康数据有限公司<br>\n" +
                "      咨询电话：400-900-9957<br>\n" +
                "      ----------------------------------------------------------------------------------------------------<br><br>\n" +
                "      保密通知：本电子邮件仅为指定收件人使用并可能载有保密内容，非指定收件人请勿使用、打开、转发、" +
                "复印本电子邮件或依赖本邮件的内容而采取任何行动。若您误收到本电子邮件，请立即通知发件人并返回该邮件，以及立即删除该电子邮件及其附件。谢谢。\n" +
                "    </span>\n" +
                "    <br><br><br>\n" +
                "  </body>\n" +
                "</html>\n";
        return String.format(newContxt, hospitalName, context);
    }

    /**
     * 发送邮箱
     * @param sendTo
     * @param title
     * @param content
     * @param attachment
     * @throws MessagingException
     */
    private void sendAttachmentsMail(String[] sendTo, String title, String content, File attachment) throws MessagingException{
        MimeMessage mimeMessage =  mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        //基本设置.
        helper.setFrom(sendFrom);//发送者.
        helper.setTo(sendTo);//接收者.
        helper.setSubject(title);//邮件主题.
        helper.setText(content, true);//邮件内容.
        FileSystemResource file = new FileSystemResource(attachment);
        String fileName = attachment.getName();
        try {
            fileName = MimeUtility.encodeWord(fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        helper.addAttachment(fileName, file);
        mailSender.send(mimeMessage);
    }

    /**
     * 如果管理员设置了debug的邮箱地址，则统一发送到这个邮箱里面，不在给医院邮箱发送
     */
    private String[] getDebugAdminEmails(){
        String sql = "select * from tb_app_config where key_word='app_pay_report_debug_email' limit 1";
        List<Map<String, Object>> infos = jdbcTemplate.queryForList(sql);
        if (null == infos || infos.isEmpty()){
            return null;
        }
        Map<String, Object> info = infos.get(0);
        return StringUtils.split(info.get("data").toString(), ",");
    }

}
