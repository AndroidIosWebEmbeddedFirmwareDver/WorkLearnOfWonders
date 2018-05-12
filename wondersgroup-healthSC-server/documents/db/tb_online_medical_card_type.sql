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

 Date: 14/04/2018 11:54:01
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- Records of tb_online_medical_card_type
-- ----------------------------
BEGIN;
INSERT INTO `tb_online_medical_card_type` VALUES (1, '2018-04-13 16:15:20', '2018-04-13 14:40:39', '00', '无卡', 1);
INSERT INTO `tb_online_medical_card_type` VALUES (2, '2018-04-13 16:15:17', '2018-04-13 14:39:32', '03', '身份证', 1);
INSERT INTO `tb_online_medical_card_type` VALUES (3, '2018-04-13 16:15:10', '2018-04-13 14:39:56', '01', '院内就诊卡', 0);
INSERT INTO `tb_online_medical_card_type` VALUES (4, '2018-04-13 16:15:13', '2018-04-13 14:40:14', '02', '居民健康卡', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
