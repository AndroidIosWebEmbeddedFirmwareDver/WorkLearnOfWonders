
#2018年04月22日

#APP用户表中增加注册来源、平台用户ID两个字段
ALTER TABLE `healthcloud_sc_server`.`tb_user_account` ADD COLUMN `origin` CHAR(1) NULL  COMMENT '来源(9:平台注册、其他:微健康注册)';
ALTER TABLE `healthcloud_sc_server`.`tb_user_account` ADD COLUMN `platform_user_id` VARCHAR(32) NULL  COMMENT '平台注册id';

#科室表中增加科室地址字段
ALTER TABLE `healthcloud_sc_server`.`tb_department_info` ADD COLUMN `dept_addr` VARCHAR(512) NULL  COMMENT '科室地址' AFTER `dept_desc`;

#2018年04月26日

#订单表添加字段 tb_order_info
ALTER TABLE `healthcloud_sc_server`.`tb_order_info ` ADD COLUMN `medi_card_type_name` VARCHAR(32) NULL  COMMENT '诊疗卡类型名称' AFTER `source`;
ALTER TABLE `healthcloud_sc_server`.`tb_order_info ` ADD COLUMN `medi_card_type_code` VARCHAR(32) NULL  COMMENT '诊疗卡类型编码' AFTER `medi_card_type_name `;

#新增表 见附件
#1.用户在线就医卡类型表
tb_online_medical_card_type.sql
#2.用户绑定在线就医卡表tb_user_online_medical_cards.sql

