/*
Navicat MySQL Data Transfer

Source Server         : 192.168.31.169
Source Server Version : 50735
Source Host           : 192.168.31.169:3306
Source Database       : test1

Target Server Type    : MYSQL
Target Server Version : 50735
File Encoding         : 65001

Date: 2021-08-14 18:10:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `ACCOUNT`;
CREATE TABLE `ACCOUNT` (
  `ACC_ID` int(11) DEFAULT NULL,
  `ACC_FIRST_NAME` varchar(255) DEFAULT NULL,
  `ACC_LAST_NAME` varchar(255) DEFAULT NULL,
  `ACC_EMAIL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
