/*
 Navicat Premium Data Transfer

 Source Server         : te-10.1.64.80 mysql-m.wdjky.me
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 10.1.64.80:9034
 Source Schema         : healthcloud_sc_server

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 26/04/2018 13:34:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_online_medical_card_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_online_medical_card_type`;
CREATE TABLE `tb_online_medical_card_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '在线挂号卡类型唯一ID',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '在线挂号卡类型更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '在线挂号卡类型创建时间',
  `type_code` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '在线挂号卡类型编码描述',
  `type_name` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '在线挂号卡类型名称描述',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除，0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

SET FOREIGN_KEY_CHECKS = 1;
