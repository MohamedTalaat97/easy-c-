/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : c++

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 10/05/2018 00:06:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_id` int(10) NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` float(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `level`(`level_id`) USING BTREE,
  CONSTRAINT `level` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, NULL, 'dsfsdf', NULL);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `solved` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`user_id`) USING BTREE,
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 17, 'dv', 'asdsdasdasdsad', NULL, 0);
INSERT INTO `comment` VALUES (2, 17, 'dv', 'asdsdasdasdsad', NULL, 0);
INSERT INTO `comment` VALUES (4, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (5, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (6, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (7, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (8, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (9, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (10, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (11, 17, 'adasd', 'asdasdasd', '2008-12-25 00:00:00', 0);
INSERT INTO `comment` VALUES (12, 17, 'dsfdsfdsf', 'sdfdsfsdfsdfsdf', '2018-03-31 00:00:00', 0);
INSERT INTO `comment` VALUES (13, 17, 'sfsdfsdfdsfds', 'sdfdfsdf', '2018-03-31 00:00:00', 0);
INSERT INTO `comment` VALUES (14, 17, 'ds', 'sdfdsfdsf', '2018-03-31 00:00:00', 0);
INSERT INTO `comment` VALUES (15, 17, 'how to change the world ?', 'sadfdsfsdf', '2018-03-31 00:00:00', 0);
INSERT INTO `comment` VALUES (16, 17, 'what the hell is that ?', 'sdfdsfdf', '2018-03-31 00:00:00', 0);
INSERT INTO `comment` VALUES (17, 17, 'wrfs  dfsdf  sf ?', 'werw  e fdsfsdf', '2018-03-31 08:43:53', 0);
INSERT INTO `comment` VALUES (18, 17, 'ersgfdg', 'hjjfgjfgj', '2018-03-31 08:45:54', 0);
INSERT INTO `comment` VALUES (19, 17, 'dfgfgfdgf', 'cvbvcbvbcvbvc', '2018-03-31 08:46:08', 0);
INSERT INTO `comment` VALUES (20, 17, 'what is your ID ?', 'sdfsdfsdfsdfsdf', '2018-04-01 10:21:18', 0);
INSERT INTO `comment` VALUES (21, 20, 'what is database ?', 'I dont know how to :-\nmake a database', '2018-05-03 10:27:20', 0);

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_id` int(11) NOT NULL,
  `cat_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `grade` int(11) NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `levelnumber`(`level_id`) USING BTREE,
  INDEX `categorynumber`(`cat_id`) USING BTREE,
  INDEX `usernumber`(`user_id`) USING BTREE,
  CONSTRAINT `categorynumber` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `levelnumber` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `usernumber` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for level
-- ----------------------------
DROP TABLE IF EXISTS `level`;
CREATE TABLE `level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_number` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `level_number`(`level_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for opinion
-- ----------------------------
DROP TABLE IF EXISTS `opinion`;
CREATE TABLE `opinion`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(9999) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `readed` tinyint(1) NULL DEFAULT NULL,
  `favourite` tinyint(1) NULL DEFAULT NULL,
  `seen` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_opinion`(`user_id`) USING BTREE,
  CONSTRAINT `user_id_opinion` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opinion
-- ----------------------------
INSERT INTO `opinion` VALUES (1, 17, 'dfdsfsdfTitle', 'Titledsfsdfsdfsdfsdnfoasdnoiasnmvlksnvlksnlkvmsdkmvsdkmvkasdmv;sdml;vasmdv;lmasd;lvma;dslmv;lsamv;samv;lsdmofsdofoasjffl;asdmf;kmscv;mcvzxcv', 1, 1, 1);
INSERT INTO `opinion` VALUES (2, 20, 'i have an opinion', 'my opinion is to change the app', 1, 0, 1);
INSERT INTO `opinion` VALUES (3, 26, 'what hapebd', 'sfnsfnsffnsfhsfhsfhsfhsfhsfjsfjdfjdfjdfjdfj', 0, 1, 1);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_id` int(11) NULL DEFAULT NULL,
  `cat_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `question` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `answer` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category`(`cat_id`) USING BTREE,
  INDEX `user`(`user_id`) USING BTREE,
  INDEX `levelid`(`level_id`) USING BTREE,
  CONSTRAINT `category` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `levelid` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `best_answer` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_id`(`comment_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (1, 20, 20, 'hello', '2018-04-17 10:31:05', 0);
INSERT INTO `reply` VALUES (2, 20, 20, 'welcome', '2018-04-17 10:34:58', 0);
INSERT INTO `reply` VALUES (4, 20, 21, 'this is me', '2018-04-17 10:36:45', 1);
INSERT INTO `reply` VALUES (5, 20, 17, 'hi from the other side', '2018-04-17 10:38:45', 0);
INSERT INTO `reply` VALUES (6, 20, 17, 'sdfdsfdsf', '2018-04-17 11:02:11', 0);
INSERT INTO `reply` VALUES (7, 20, 17, 'hellp', '2018-04-17 11:03:13', 0);
INSERT INTO `reply` VALUES (8, 20, 17, 'hellpk', '2018-04-17 11:03:19', 0);
INSERT INTO `reply` VALUES (9, 20, 17, 'hello I dont think this is good idea to become that person', '2018-04-17 11:05:23', 0);
INSERT INTO `reply` VALUES (10, 20, 17, 'hello I dont think this is good idea to become that person', '2018-04-17 11:05:35', 0);
INSERT INTO `reply` VALUES (11, 20, 17, 'hello ia am me', '2018-04-17 11:20:33', 0);
INSERT INTO `reply` VALUES (12, 20, 21, 'hello its me', '2018-04-17 11:43:49', 0);
INSERT INTO `reply` VALUES (13, 16, 17, 'where are you ??', '2018-04-17 12:26:28', 0);
INSERT INTO `reply` VALUES (14, 20, 17, 'vg', '2018-04-18 09:24:36', 0);
INSERT INTO `reply` VALUES (15, 20, 17, 'hellp\nsdfsdf', '2018-04-18 09:25:37', 0);
INSERT INTO `reply` VALUES (16, 20, 17, 'I hope this article gave you very good knowledge on basics of RecyclerView. The next step is to follow the below articles that explains advanced RecyclerView that combines CardView and RecyclerView in a grid fashion, adding Swipe to Delete and Undo and adding Search Filter.', '2018-04-18 09:26:05', 0);
INSERT INTO `reply` VALUES (17, 21, 20, 'any reply', '2018-05-03 10:28:15', 0);

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `comment_id` int(11) NULL DEFAULT NULL,
  `reply_id` int(11) NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `solved` tinyint(1) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `commentids`(`comment_id`) USING BTREE,
  INDEX `replyidss`(`reply_id`) USING BTREE,
  INDEX `userids`(`user_id`) USING BTREE,
  CONSTRAINT `commentids` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `replyidss` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userids` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES (1, 20, 21, 17, 'this is not answer', '2018-05-03 10:28:49', 0, 'Wrong Answer');

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `output` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position` float(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `catid`(`cat_id`) USING BTREE,
  CONSTRAINT `catid` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `suspended` tinyint(1) NULL DEFAULT 0,
  `level` int(255) NULL DEFAULT 0,
  `request` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (17, 'gyvy', '1111', 'khaledsab1997@gmail.com', 'I', 12, 0, 0, NULL);
INSERT INTO `user` VALUES (19, 'kkk', '111219', 'khaledsab1997+2@gmail.com', 'S', 20, 0, 0, '');
INSERT INTO `user` VALUES (20, 'a', '1111', 'ykyktitkti@', 'S', 20, 0, 0, '');
INSERT INTO `user` VALUES (21, 'kk', '1111', 'khaledsab_1997@yahoo.com', 'S', 12, 0, 0, '');
INSERT INTO `user` VALUES (22, 'admin', '1111', 'sdf@sd', 'A', NULL, 0, 0, NULL);
INSERT INTO `user` VALUES (23, 'khal', '1111', 'khaledsab1997+6@gmail.com', 'S', 22, 0, 0, '');
INSERT INTO `user` VALUES (24, 'medo', '1234', 'gggdg@', 'I', 30, 0, 0, NULL);
INSERT INTO `user` VALUES (25, '7mada', '1234', '44@', 'I', 40, 1, 0, NULL);
INSERT INTO `user` VALUES (26, 'ahmed', '22222', 'khaledsab1997+56@gmail.com', 'S', 22, 0, 0, '');
INSERT INTO `user` VALUES (27, 'omar', '71361', 'khaledsab1997@', 'I', 22, 0, 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
