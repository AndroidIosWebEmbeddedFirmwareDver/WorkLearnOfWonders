/*
 Navicat Premium Data Transfer

 Source Server         : L-te-192.168.2.103
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 192.168.2.103:3306
 Source Schema         : healthcloud_sc_server

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 14/04/2018 11:56:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user_online_medical_cards
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_online_medical_cards`;
CREATE TABLE `tb_user_online_medical_cards` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '在线挂号卡类型唯一ID',
  `uid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户唯一编码ID',
  `card_type_code` int(11) DEFAULT NULL COMMENT '在线挂号卡类型编码描述',
  `card_type_name` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '在线挂号卡类型名称描述',
  `hospital_code` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '医院代码；如果卡类型是就诊卡则不为空',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '在线挂号卡类型更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '在线挂号卡类型创建时间',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除，0-未删除，1-已删除',
  `mediacl_card_no` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '在线挂号卡卡号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


SET FOREIGN_KEY_CHECKS = 1;
