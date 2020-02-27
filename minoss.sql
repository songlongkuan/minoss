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

 Date: 27/02/2020 17:49:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for minoss_access
-- ----------------------------
DROP TABLE IF EXISTS `minoss_access`;
CREATE TABLE `minoss_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `access_key` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '授权key',
  `access_secret` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '授权secret',
  `access_remarks` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `if_bucket_put` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可以上传 0：不可以  1：可以',
  `if_bucket_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可以删除  0：不可以  1：可以',
  `if_bucket_get` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可以获取 0：不可以  1：可以',
  `if_bucket_edit` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可以编辑  0：不可以 1：可以',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  UNIQUE KEY `access_key` (`access_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='bucket 授权';

-- ----------------------------
-- Records of minoss_access
-- ----------------------------
BEGIN;
INSERT INTO `minoss_access` VALUES (1, 1225412652902195202, 1, 0, '2020-02-06 13:35:18', '2020-02-07 09:27:48', 'fa5b40b6b6f32387a49a05dcb3e34a6f', '$2a$10$dMlm4CqCnqXtzqRQyZfbJODbiwNpiTBBJQpfFZmZTV4E00JSu5Wm2', 'access-1', 1, 1, 1, 1);
INSERT INTO `minoss_access` VALUES (2, 1225703879358935041, 1, 0, '2020-02-07 08:52:31', '2020-02-07 09:27:56', 'a9ad9ce9775283498c6331c78d475b60', '$2a$10$ZktqTetT7Oyhb.OGDtQjXuiFHVHPT62XYxK5lfh0YGTeYakDOk85G', 'access-2', 1, 1, 1, 1);
INSERT INTO `minoss_access` VALUES (3, 1225703968785690626, 1, 0, '2020-02-07 08:52:53', '2020-02-07 09:28:01', '425766ed127e873026e251d8a9371a5a', '$2a$10$0t5JE6MdoPUiIML0pt8bA.laRY3o02ev3v27OuSdZiICo/epvKx2.', 'access-3', 0, 0, 0, 0);
INSERT INTO `minoss_access` VALUES (4, 1225710871863373826, 4, 0, '2020-02-07 09:20:19', '2020-02-07 09:28:06', '3d1133bb95ed515396c0d59faaf17105', '$2a$10$QPmBBDwMHolOHOcmugHnIefulI4mUY6ldAvBy0R5eF3dIgQmkCfe6', 'access-4', 1, 1, 1, 1);
INSERT INTO `minoss_access` VALUES (5, 1225711290580742145, 1, 1, '2020-02-07 09:21:58', '2020-02-08 15:27:43', '3ef2dce29d97ebf2c5dce5f50bb74ece', '$2a$10$nIoJs9iqIWKIbbrkUcZXW.UQP40XZkvg.VwGOBzxChhTLEMjA4d8i', 'access-5', 0, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for minoss_access_bucket
-- ----------------------------
DROP TABLE IF EXISTS `minoss_access_bucket`;
CREATE TABLE `minoss_access_bucket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `bucket_mid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'bucket 业务唯一ID',
  `access_mid` bigint(20) NOT NULL DEFAULT '0' COMMENT 'access 业务唯一ID',
  `order_sort` int(5) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  KEY `bucket_mid` (`bucket_mid`) USING BTREE,
  KEY `access_mid` (`access_mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of minoss_access_bucket
-- ----------------------------
BEGIN;
INSERT INTO `minoss_access_bucket` VALUES (1, 1226329558567907330, 0, 1, '2020-02-09 02:18:45', '2020-02-09 02:19:38', 1221732542747549697, 1225412652902195202, 1);
INSERT INTO `minoss_access_bucket` VALUES (2, 1226329558567907331, 0, 1, '2020-02-09 02:18:45', '2020-02-09 02:19:38', 1223589111068123138, 1225412652902195202, 2);
INSERT INTO `minoss_access_bucket` VALUES (3, 1226329780350197761, 0, 1, '2020-02-09 02:19:38', '2020-02-09 12:42:30', 1221732542747549697, 1225412652902195202, 0);
INSERT INTO `minoss_access_bucket` VALUES (4, 1226329780350197762, 0, 1, '2020-02-09 02:19:38', '2020-02-09 12:42:30', 1223589111068123138, 1225412652902195202, 1);
INSERT INTO `minoss_access_bucket` VALUES (5, 1226486529577238530, 0, 1, '2020-02-09 12:42:30', '2020-02-09 12:42:40', 1221732542747549697, 1225412652902195202, 0);
INSERT INTO `minoss_access_bucket` VALUES (6, 1226486529577238531, 0, 1, '2020-02-09 12:42:30', '2020-02-09 12:42:40', 1223589111068123138, 1225412652902195202, 1);
INSERT INTO `minoss_access_bucket` VALUES (7, 1226486529577238532, 0, 1, '2020-02-09 12:42:30', '2020-02-09 12:42:40', 1225710741223387137, 1225412652902195202, 2);
INSERT INTO `minoss_access_bucket` VALUES (8, 1226486572480774145, 0, 0, '2020-02-09 12:42:40', '2020-02-09 12:42:40', 1223589111068123138, 1225412652902195202, 0);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='bucket 存储空间';

-- ----------------------------
-- Records of minoss_bucket
-- ----------------------------
BEGIN;
INSERT INTO `minoss_bucket` VALUES (9, 1221732542747549697, 0, 8, '2020-01-27 09:51:51', '2020-02-27 11:18:32', '测试bucket-1', 1, '/users/pencilso/resource/bucket/bucket-1/');
INSERT INTO `minoss_bucket` VALUES (10, 1223589111068123138, 0, 4, '2020-02-01 12:49:12', '2020-02-27 11:12:48', '测试bucket 2', 2, '/users/pencilso/resource/bucket/bucket-2/');
INSERT INTO `minoss_bucket` VALUES (11, 1223589138071052289, 0, 4, '2020-02-01 12:49:18', '2020-02-27 11:12:52', '测试bucket 3', 2, '/users/pencilso/resource/bucket/bucket-3/');
INSERT INTO `minoss_bucket` VALUES (12, 1225710741223387137, 0, 3, '2020-02-07 09:19:47', '2020-02-27 11:12:57', '测试bucket-4', 1, '/users/pencilso/resource/bucket/bucket-4/');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='Bucket 汇总表 （各数据的统计）';

-- ----------------------------
-- Records of minoss_bucket_collect
-- ----------------------------
BEGIN;
INSERT INTO `minoss_bucket_collect` VALUES (4, 1221732543045345282, 0, 0, '2020-01-27 09:51:51', '2020-01-27 09:51:51', 1221732542747549697, 0, 0);
INSERT INTO `minoss_bucket_collect` VALUES (5, 1223589111382695937, 0, 0, '2020-02-01 12:49:12', '2020-02-01 12:49:12', 1223589111068123138, 0, 0);
INSERT INTO `minoss_bucket_collect` VALUES (6, 1223589138100412417, 0, 0, '2020-02-01 12:49:18', '2020-02-01 12:49:18', 1223589138071052289, 0, 0);
INSERT INTO `minoss_bucket_collect` VALUES (7, 1225710741252747266, 0, 0, '2020-02-07 09:19:47', '2020-02-07 09:19:47', 1225710741223387137, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for minoss_file
-- ----------------------------
DROP TABLE IF EXISTS `minoss_file`;
CREATE TABLE `minoss_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '原始文件名',
  `file_name_store` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件名 - 存储到服务器上的本地文件名',
  `file_ext` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件后缀',
  `file_md5` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件md5',
  `file_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件访问路径',
  `file_path_md5` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件访问路径的md5',
  `folder_mid` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件夹id',
  `access_mid` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE,
  KEY `if_del,file_path_md5` (`if_del`,`file_path_md5`) USING BTREE,
  KEY `folder_mid` (`folder_mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for minoss_folder
-- ----------------------------
DROP TABLE IF EXISTS `minoss_folder`;
CREATE TABLE `minoss_folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mid` bigint(20) NOT NULL DEFAULT '0',
  `version` int(10) NOT NULL DEFAULT '0',
  `if_del` tinyint(1) NOT NULL DEFAULT '0',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件夹名称',
  `parent_mid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级文件夹ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mid` (`mid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文件夹模型';

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
INSERT INTO `minoss_user` VALUES (1, 670241412494790656, 0, 141, '2020-01-24 04:20:05', '2020-02-27 10:42:39', 'pencilso', '$2a$10$7ph6bWxDxp/XhvXG9YwKkONQW6Q9yCpD4JLQLQr.BEdgYwImUtfKa', '', 'eyJhbGciOiJIUzUxMiJ9.eyJzYWx0IjoiZjUzMWIwMDNkMzY1MjcwNGIyN2I3Mzk5ODk0NjY1ZDQiLCJpZCI6NjcwMjQxNDEyNDk0NzkwNjU2LCJ0aW1lIjoxNTgyNzcxMzU5ODA4fQ.BuUa15ZwPCWXiY5HsFzTG3F6LiE99t7e1epoFoIj9OQcBApqPGQHW8I0_5WvbjcrFb_z6rGCxSHKeogAvWEQqA', 'f531b003d3652704b27b7399894665d4');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
