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

 Date: 20/03/2018 01:33:47
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
  `cat_id` int(11) NULL DEFAULT NULL,
  `comment` varchar(999) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `solved` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userid`(`user_id`) USING BTREE,
  INDEX `cat_id`(`cat_id`) USING BTREE,
  CONSTRAINT `cat_id` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  CONSTRAINT `user_id_opinion` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opinion
-- ----------------------------
INSERT INTO `opinion` VALUES (3, 1, 'why there is no content for c# ??', 'i am student and i want to know more about s#', 1, 0, 1);
INSERT INTO `opinion` VALUES (4, 1, 'Title234234324', 'Title34wesdfdsfsdf', 1, 1, 1);
INSERT INTO `opinion` VALUES (5, 1, 'werwerweTitle', 'Titlewerwerwerwer', NULL, 0, 1);
INSERT INTO `opinion` VALUES (6, 1, 'sdfsdfsdfsdf', 'dsfsdfsdfsdfsdfsdf', NULL, 0, 1);
INSERT INTO `opinion` VALUES (7, 1, 'sdfsdfsdfsdfsdfsdfhsdhfusdnfjonsdjfnsdnfosdnfsdnfksdnfksndkflnsdfnsdnfsdlnflsdnfklsndlfknsdlkfnsdlfnlskdnflksdnfldsnfnsdf', ' 15 down vote The most elegant and flexible solution I have found so far is here: http://android-er.blogspot.sg/2010/12/custom-arrayadapter-for-spinner-with.html  Basically, follow these steps:  Create custom layout xml file for your dropdown item, lets say I will call it spinner_item.xml Create custom view class, for your dropdown Adapter. In this custom class, you need to overwrite and set your custom dropdown item layout in getView() and getDropdownView() method. My code is as below:  public class CustomArrayAdapter extends ArrayAdapter<String>{  private List<String> objects; private Context context;  public CustomArrayAdapter(Context context, int resourceId,      List<String> objects) {      super(context, resourceId, objects);      this.objects = objects;      this.context = context; }  @Override public View getDropDownView(int position, View convertView,     ViewGroup parent) {     return getCustomView(position, convertView, parent); }  @Override public View getView(int position, View convertView, ViewGroup parent) {   return getCustomView(position, convertView, parent); }  public View getCustomView(int position, View convertView, ViewGroup parent) {  LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE ); View row=inflater.inflate(R.layout.spinner_item, parent, false); TextView label=(TextView)row.findViewById(R.id.spItem);  label.setText(objects.get(position));  if (position == 0) {//Special style for dropdown header       label.setTextColor(context.getResources().getColor(R.color.text_hint_color)); }  return row; }  } In your activity or fragment, make use of the custom adapter for your spinner view. Something like this:  Spinner sp = (Spinner)findViewById(R.id.spMySpinner); ArrayAdapter<String> myAdapter = new CustomArrayAdapter(this, R.layout.spinner_item, options); sp.setAdapter(myAdapter);', NULL, NULL, 1);
INSERT INTO `opinion` VALUES (8, 1, 'sdf', ' 15 down vote The most elegant and flexible solution I have found so far is here: http://android-er.blogspot.sg/2010/12/custom-arrayadapter-for-spinner-with.html  Basically, follow these steps:  Create custom layout xml file for your dropdown item, lets say I will call it spinner_item.xml Create custom view class, for your dropdown Adapter. In this custom class, you need to overwrite and set your custom dropdown item layout in getView() and getDropdownView() method. My code is as below:  public class CustomArrayAdapter extends ArrayAdapter<String>{  private List<String> objects; private Context context;  public CustomArrayAdapter(Context context, int resourceId,      List<String> objects) {      super(context, resourceId, objects);      this.objects = objects;      this.context = context; }  @Override public View getDropDownView(int position, View convertView,     ViewGroup parent) {     return getCustomView(position, convertView, parent); }  @Override public View getView(int position, View convertView, ViewGroup parent', NULL, NULL, 1);
INSERT INTO `opinion` VALUES (9, 1, 'sdfsdf', ' 15 down vote The most elegant a', NULL, NULL, 1);
INSERT INTO `opinion` VALUES (10, 1, 'sdfsdfsdfsdfsdfsdfhsdhfusdnfjonsdjfnsdnfosdnfsdnfksdnfksndkflnsdfnsdnfsdlnflsdnfklsndlfknsdlkfnsdlfnlskdnflksdnfldsnfnsdf', ' 15 down vote The most elegant and flexible solution I have found so far is here: http://android-er.blogspot.sg/2010/12/custom-arrayadapter-for-spinner-with.html  Basically, follow these steps:  Create custom layout xml file for your dropdown item, lets say I will call it spinner_item.xml Create custom view class, for your dropdown Adapter. In this custom class, you need to overwrite and set your custom dropdown item layout in getView() and getDropdownView() method. My code is as below:  public class CustomArrayAdapter extends ArrayAdapter<String>{  private List<String> objects; private Context context;  public CustomArrayAdapter(Context context, int resourceId,      List<String> objects) {      super(context, resourceId, objects);      this.objects = objects;      this.context = context; }  @Override public View getDropDownView(int position, View convertView,     ViewGroup parent) {     return getCustomView(position, convertView, parent); }  @Override public View getView(int position, View convertView, ViewGroup parent) {   return getCustomView(position, convertView, parent); }  public View getCustomView(int position, View convertView, ViewGroup parent) {  LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE ); View row=inflater.inflate(R.layout.spinner_item, parent, false); TextView label=(TextView)row.findViewById(R.id.spItem);  label.setText(objects.get(position));  if (position == 0) {//Special style for dropdown header       label.setTextColor(context.getResources().getColor(R.color.text_hint_color)); }  return row; }  } In your activity or fragment, make use of the custom adapter for your spinner view. Something like this:  Spinner sp = (Spinner)findViewById(R.id.spMySpinner); ArrayAdapter<String> myAdapter = new CustomArrayAdapter(this, R.layout.spinner_item, options); sp.setAdapter(myAdapter);', NULL, 1, 1);

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
  `answer` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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
  `best_answer` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_id`(`comment_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `comment_id` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `id` int(11) NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `cat_id` int(11) NULL DEFAULT NULL,
  `topic_id` int(11) NULL DEFAULT NULL,
  `question_id` int(11) NULL DEFAULT NULL,
  `exam_id` int(11) NULL DEFAULT NULL,
  `person_id` int(11) NULL DEFAULT NULL,
  `comment_id` int(11) NULL DEFAULT NULL,
  `reply_id` int(11) NULL DEFAULT NULL,
  `report_id` int(11) NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  `solved` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `usernum`(`user_id`) USING BTREE,
  INDEX `catnum`(`cat_id`) USING BTREE,
  INDEX `topicnum`(`topic_id`) USING BTREE,
  INDEX `questionnum`(`question_id`) USING BTREE,
  INDEX `exannum`(`exam_id`) USING BTREE,
  INDEX `personnum`(`person_id`) USING BTREE,
  INDEX `commentnum`(`comment_id`) USING BTREE,
  INDEX `replynum`(`reply_id`) USING BTREE,
  INDEX `reportnum`(`report_id`) USING BTREE,
  CONSTRAINT `catnum` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `commentnum` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `exannum` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `personnum` FOREIGN KEY (`person_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `questionnum` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `replynum` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `reportnum` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `topicnum` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `usernum` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'sdfsedf', '243234', NULL, '234234', 'S', 234234);
INSERT INTO `user` VALUES (2, 'khaled', '5151', NULL, 'uub', 'S', 14614);
INSERT INTO `user` VALUES (3, 'khaled', '5151', NULL, 'uub', 'I', 14614);

SET FOREIGN_KEY_CHECKS = 1;
