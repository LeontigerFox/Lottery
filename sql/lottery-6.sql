/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50639
 Source Host           : localhost:3306
 Source Schema         : lottery_210828_xfg_subtractionStock

 Target Server Type    : MySQL
 Target Server Version : 50639
 File Encoding         : 65001

 Date: 05/12/2021 17:53:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                            `activityId` bigint(20) DEFAULT NULL COMMENT '活动ID',
                            `activityName` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '活动名称',
                            `activityDesc` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '活动描述',
                            `beginDateTime` datetime NOT NULL COMMENT '开始时间',
                            `endDateTime` datetime NOT NULL COMMENT '结束时间',
                            `stockCount` int(11) NOT NULL COMMENT '库存',
                            `takeCount` int(11) DEFAULT NULL COMMENT '每人可参与次数',
                            `state` int(11) DEFAULT NULL COMMENT '活动状态：编辑、提审、撤审、通过、运行、拒绝、关闭、开启',
                            `creator` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
                            `createTime` datetime NOT NULL COMMENT '创建时间',
                            `updateTime` datetime NOT NULL COMMENT '修改时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `activity_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='活动配置';

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` VALUES (1, 100001, '活动名', '测试活动', '2021-10-01 00:00:00', '2021-12-30 23:59:59', 100, 10, 5, 'xiaofuge', '2021-08-08 20:14:50', '2021-08-08 20:14:50');
INSERT INTO `activity` VALUES (2, 100002, '活动名02', '测试活动', '2021-10-01 00:00:00', '2021-12-30 23:59:59', 100, 10, 5, 'xiaofuge', '2021-10-05 15:49:21', '2021-10-05 15:49:21');
COMMIT;

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award` (
                         `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                         `awardId` bigint(20) DEFAULT NULL COMMENT '奖品ID',
                         `awardType` int(4) DEFAULT NULL COMMENT '奖品类型（文字描述、兑换码、优惠券、实物奖品暂无）',
                         `awardCount` int(11) DEFAULT NULL COMMENT '奖品数量',
                         `awardName` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品名称',
                         `awardContent` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
                         `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updateTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'updateTime',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='奖品配置';

-- ----------------------------
-- Records of award
-- ----------------------------
BEGIN;
INSERT INTO `award` VALUES (1, 1, 1, 100, 'IMac', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (2, 2, 1, 100, 'iphone', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (3, 3, 1, 100, 'ipad', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (4, 4, 1, 100, 'AirPods', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `award` VALUES (5, 5, 1, 100, 'Book', 'Code', '2021-08-15 15:38:05', '2021-08-15 15:38:05');
COMMIT;

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy` (
                            `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                            `strategyId` bigint(11) NOT NULL COMMENT '策略ID',
                            `strategyDesc` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '策略描述',
                            `strategyMode` int(4) DEFAULT NULL COMMENT '策略方式「1:单项概率、2:总体概率」',
                            `grantType` int(4) DEFAULT NULL COMMENT '发放奖品方式「1:即时、2:定时[含活动结束]、3:人工」',
                            `grantDate` datetime DEFAULT NULL COMMENT '发放奖品时间',
                            `extInfo` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '扩展信息',
                            `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                            `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `strategy_strategyId_uindex` (`strategyId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略配置';

-- ----------------------------
-- Records of strategy
-- ----------------------------
BEGIN;
INSERT INTO `strategy` VALUES (1, 10001, 'test', 2, 1, NULL, '', '2021-09-25 08:15:52', '2021-09-25 08:15:52');
COMMIT;

-- ----------------------------
-- Table structure for strategy_detail
-- ----------------------------
DROP TABLE IF EXISTS `strategy_detail`;
CREATE TABLE `strategy_detail` (
                                   `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
                                   `strategyId` bigint(11) NOT NULL COMMENT '策略ID',
                                   `awardId` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品ID',
                                   `awardDesc` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品描述',
                                   `awardCount` int(11) DEFAULT NULL COMMENT '奖品库存',
                                   `awardSurplusCount` int(11) DEFAULT '0' COMMENT '奖品剩余库存',
                                   `awardRate` decimal(5,2) DEFAULT NULL COMMENT '中奖概率',
                                   `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                                   `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略明细';

-- ----------------------------
-- Records of strategy_detail
-- ----------------------------
BEGIN;
INSERT INTO `strategy_detail` VALUES (1, 10001, '1', NULL, 10, 0, 0.05, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (2, 10001, '2', NULL, 20, 19, 0.15, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (3, 10001, '3', NULL, 50, 43, 0.20, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (4, 10001, '4', NULL, 100, 70, 0.25, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
INSERT INTO `strategy_detail` VALUES (5, 10001, '5', NULL, 500, 388, 0.35, '2021-08-15 15:38:05', '2021-08-15 15:38:05');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
