
#2018��04��22��

#APP�û���������ע����Դ��ƽ̨�û�ID�����ֶ�
ALTER TABLE `healthcloud_sc_server`.`tb_user_account` ADD COLUMN `origin` CHAR(1) NULL  COMMENT '��Դ(9:ƽ̨ע�ᡢ����:΢����ע��)';
ALTER TABLE `healthcloud_sc_server`.`tb_user_account` ADD COLUMN `platform_user_id` VARCHAR(32) NULL  COMMENT 'ƽ̨ע��id';

#���ұ������ӿ��ҵ�ַ�ֶ�
ALTER TABLE `healthcloud_sc_server`.`tb_department_info` ADD COLUMN `dept_addr` VARCHAR(512) NULL  COMMENT '���ҵ�ַ' AFTER `dept_desc`;

#2018��04��26��

#����������ֶ� tb_order_info
ALTER TABLE `healthcloud_sc_server`.`tb_order_info ` ADD COLUMN `medi_card_type_name` VARCHAR(32) NULL  COMMENT '���ƿ���������' AFTER `source`;
ALTER TABLE `healthcloud_sc_server`.`tb_order_info ` ADD COLUMN `medi_card_type_code` VARCHAR(32) NULL  COMMENT '���ƿ����ͱ���' AFTER `medi_card_type_name `;

#������ ������
#1.�û����߾�ҽ�����ͱ�
tb_online_medical_card_type.sql
#2.�û������߾�ҽ����tb_user_online_medical_cards.sql

