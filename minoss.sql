/*
 Navicat Premium Data Transfer

 Source Server         : Centos虚拟机数据库
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 10.211.55.6:3306
 Source Schema         : minoss

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 27/01/2020 18:33:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for minoss_bucket
-- ----------------------------
DROP TABLE IF EXISTS `minoss_bucket`;
CREATE TABLE `minoss_bucket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bucket_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'bucket 名称',
  `bucket_right` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'bucket权限 1私有  2公共读 ',
  `bucket_store_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'bucket 存储到本地的路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='bucket 存储空间';

-- ----------------------------
-- Records of minoss_bucket
-- ----------------------------
BEGIN;
INSERT INTO `minoss_bucket` VALUES (9, 1221732542747549697, 0, 1, '2020-01-27 09:51:51', '2020-01-27 10:06:21', '这是一个测试bocket-1', 1, 'store/bucket');
COMMIT;

-- ----------------------------
-- Table structure for minoss_bucket_collect
-- ----------------------------
DROP TABLE IF EXISTS `minoss_bucket_collect`;
CREATE TABLE `minoss_bucket_collect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bucket_mid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'bucket的业务ID',
  `store_used_size` bigint(20) NOT NULL DEFAULT '0' COMMENT '存储空间已使用大小',
  `store_file_size` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件存储数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  KEY `bucket_mid` (`bucket_mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Bucket 汇总表 （各数据的统计）';

-- ----------------------------
-- Records of minoss_bucket_collect
-- ----------------------------
BEGIN;
INSERT INTO `minoss_bucket_collect` VALUES (4, 1221732543045345282, 0, 0, '2020-01-27 09:51:51', '2020-01-27 09:51:51', 1221732542747549697, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for minoss_user
-- ----------------------------
DROP TABLE IF EXISTS `minoss_user`;
CREATE TABLE `minoss_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除  0未删除  1已删除',
  `version` int(10) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `login_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录名',
  `login_password` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录密码',
  `nike_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `jwt_token` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'token 令牌',
  `jwt_salt` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'token令牌哈希盐',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  UNIQUE KEY `loginname` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- ----------------------------
-- Records of minoss_user
-- ----------------------------
BEGIN;
INSERT INTO `minoss_user` VALUES (1, 670241412494790656, 0, 48, '2020-01-24 04:20:05', '2020-01-25 15:39:05', 'pencilso', '$2a$10$7ph6bWxDxp/XhvXG9YwKkONQW6Q9yCpD4JLQLQr.BEdgYwImUtfKa', '', 'eyJhbGciOiJIUzUxMiJ9.eyJ1X2lkIjo2NzAyNDE0MTI0OTQ3OTA2NTYsInVfc2FsdCI6Ijk1ZWU3YzFhYzA2MTQ5OTI5YWQ1MTMzYjNhMDQyZDc5IiwibF90aW1lIjoxNTc5OTY2NzQ1NjY1fQ.2AOH5w4hEJbzK6iVToEeN7SkUOI3pehDdbA3XIacUTNwUI9pAcz1iMW_65qfXubSf3al4NzayaGMygOYAHiI1w', '95ee7c1ac06149929ad5133b3a042d79');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
