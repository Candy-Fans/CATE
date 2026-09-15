/*
 Navicat Premium Data Transfer

 Source Server         : local-demo
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : cate

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 28/05/2024 23:50:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '账号，不可重复',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '密码，必填',
  `phone_num` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE COMMENT '管理的用户名，不可重复',
  UNIQUE INDEX `phone_num`(`phone_num` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'demo-admin', 'iyOuhdlAOcrgZ2002GGE9w==', '13800000000');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `type` tinyint NULL DEFAULT NULL COMMENT '收藏类型（0美食1合集）',
  `food_id` int NULL DEFAULT NULL COMMENT '美食id',
  `food_list_id` int NULL DEFAULT NULL COMMENT '合集id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_collect_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_collect_food`(`food_id` ASC) USING BTREE,
  CONSTRAINT `fk_collect_food` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_collect_user` FOREIGN KEY (`user_id`) REFERENCES `consumer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of collect
-- ----------------------------
-- Intentionally empty: personal collection history was removed before publication.

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '评论类型（0美食1合集2窗口）',
  `food_id` int NULL DEFAULT NULL COMMENT '美食id',
  `food_list_id` int NULL DEFAULT NULL COMMENT '美食合集id',
  `food_window_id` int NULL DEFAULT NULL COMMENT '窗口id',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `up` int NULL DEFAULT 0 COMMENT '评论点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_comment_user`(`user_id` ASC) USING BTREE,
  INDEX `fk_comment_food`(`food_id` ASC) USING BTREE,
  INDEX `fk_comment_foodlist`(`food_list_id` ASC) USING BTREE,
  INDEX `fk_comment_foodwindow`(`food_window_id` ASC) USING BTREE,
  CONSTRAINT `fk_comment_food` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_foodlist` FOREIGN KEY (`food_list_id`) REFERENCES `food_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_foodwindow` FOREIGN KEY (`food_window_id`) REFERENCES `food_window` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comment_user` FOREIGN KEY (`user_id`) REFERENCES `consumer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 138 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
-- Intentionally empty: personal comments were removed before publication.

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别（1男0女）',
  `phone_num` char(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '电话',
  `email` char(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birth` datetime NULL DEFAULT NULL COMMENT '生日',
  `introduction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '签名',
  `location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地区',
  `avator` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE COMMENT '用户名不可重复',
  UNIQUE INDEX `phoneNum`(`phone_num` ASC) USING BTREE COMMENT '手机号码不可重复'
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '前端用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of consumer
-- ----------------------------
-- Intentionally empty: users should register their own local demo accounts.

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `food_window_id` int NULL DEFAULT NULL COMMENT '所属窗口id',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '美食名称',
  `introduction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '美食图片',
  `price` int NULL DEFAULT NULL COMMENT '美食价格',
  `nums` int UNSIGNED NOT NULL COMMENT '美食被点击次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_food_foodwindow`(`food_window_id` ASC) USING BTREE,
  CONSTRAINT `fk_food_foodwindow` FOREIGN KEY (`food_window_id`) REFERENCES `food_window` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 193 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '美食' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of food
-- ----------------------------
INSERT INTO `food` VALUES (33, 2, '西二铁板炒饭炸串-油炸淀粉肠', '绝对卫生，干净', '2018-12-30 10:31:30', '2024-04-24 22:02:08', '/img/foodPic/1713967328791dfc.jpg', 3, 19);
INSERT INTO `food` VALUES (60, 9, '西一手工水饺-猪肉香菇水饺', '猪肉香菇水饺', '2019-06-02 19:35:54', '2024-04-24 18:12:57', '/img/foodPic/1713953577993jiaozi.jpg', 10, 4);
INSERT INTO `food` VALUES (160, 1, '东二一兜鱼-炸鱼丸', '666', '2024-04-24 15:45:20', '2024-04-24 15:45:20', '/img/foodPic/tubiao.jpg', 6, 11);
INSERT INTO `food` VALUES (161, 1, '东二一兜鱼-美味鳕鱼', '测试', '2024-04-24 15:46:42', '2024-04-24 15:46:42', '/img/foodPic/tubiao.jpg', 10, 72);
INSERT INTO `food` VALUES (162, 2, '西二铁板炒饭炸串-铁板烤鱼', '美味铁板烤鱼双人餐', '2024-04-24 17:45:40', '2024-04-24 17:45:40', '/img/foodPic/tubiao.jpg', 30, 1);
INSERT INTO `food` VALUES (163, 3, '西二五谷鱼粉-全家福渔粉', '', '2024-04-24 17:46:06', '2024-04-24 17:46:06', '/img/foodPic/tubiao.jpg', 15, 11);
INSERT INTO `food` VALUES (164, 3, '西二五谷鱼粉-菌菇粉丝烩面', '菌菇粉丝烩面', '2024-04-24 17:46:50', '2024-04-24 17:48:26', '/img/foodPic/1713952105694share_1800fb365a6c35f4c473650a925af825.png', 12, 3);
INSERT INTO `food` VALUES (165, 12, '西三黄焖鸡米饭-黄焖鸡米饭', '黄焖鸡米饭', '2019-06-02 19:55:55', '2024-04-24 18:21:57', '/img/foodPic/1713954117315share_3965e2673b19cbec95599eedeb036d80.png', 14, 1);
INSERT INTO `food` VALUES (166, 12, '西三黄焖鸡米饭-黄焖茄子', '黄焖茄子', '2019-06-02 19:56:38', '2024-04-24 18:22:24', '/img/foodPic/1713954144418share_3965e2673b19cbec95599eedeb036d80.png', 12, 1);
INSERT INTO `food` VALUES (167, 4, '西二木桶饭-麻辣豆角', '美味可口', '2024-04-24 18:04:45', '2024-04-24 18:05:03', '/img/foodPic/1713953103843cdj.jpg', 3, 2);
INSERT INTO `food` VALUES (168, 5, '西二锡纸炒鸡-麻辣口味锡纸炒鸡', '麻辣口味锡纸炒鸡', '2024-04-24 18:05:52', '2024-04-24 18:05:52', '/img/foodPic/tubiao.jpg', 12, 5);
INSERT INTO `food` VALUES (169, 5, '西二锡纸炒鸡-酱香口味锡纸炒鸡', '酱香口味锡纸炒鸡', '2024-04-24 18:06:55', '2024-04-24 18:06:55', '/img/foodPic/tubiao.jpg', 12, 1);
INSERT INTO `food` VALUES (170, 6, '西二小馆辣肉面-招牌辣肉面', '招牌辣肉面', '2024-04-24 18:07:22', '2024-04-24 18:07:22', '/img/foodPic/tubiao.jpg', 10, 1);
INSERT INTO `food` VALUES (171, 8, '东一牛肉卷饼-牛肉卷饼', '牛肉卷饼', '2024-04-24 18:12:25', '2024-04-24 18:12:40', '/img/foodPic/1713953560075qq_pic_merged_1712761499036.jpg', 6, 4);
INSERT INTO `food` VALUES (172, 9, '西一手工水饺-猪肉大葱水饺', '猪肉大葱水饺', '2024-04-24 18:13:19', '2024-04-24 18:13:25', '/img/foodPic/1713953605545jiaozi.jpg', 10, 1);
INSERT INTO `food` VALUES (173, 10, '西三烤鱼饭-麻辣烤鱼饭', '麻辣烤鱼饭', '2024-04-24 18:13:47', '2024-04-24 18:13:47', '/img/foodPic/tubiao.jpg', 12, 1);
INSERT INTO `food` VALUES (174, 10, '西三烤鱼饭-藤椒烤鱼饭', '藤椒烤鱼饭', '2024-04-24 18:13:59', '2024-04-24 18:13:59', '/img/foodPic/tubiao.jpg', 12, 1);
INSERT INTO `food` VALUES (175, 11, '西三臊子面-番茄鸡蛋面', '番茄鸡蛋面量大管饱', '2024-04-24 18:14:29', '2024-04-24 18:21:06', '/img/foodPic/1713954066254jdm.jpg', 8, 1);
INSERT INTO `food` VALUES (176, 13, '东二焖烤鸡腿饼-油炸淀粉肠', '美味', '2024-04-24 18:23:00', '2024-04-24 18:23:18', '/img/foodPic/1713954198103dfc.jpg', 2, 16);
INSERT INTO `food` VALUES (177, 13, '东二焖烤鸡腿饼-烤面筋', '美味', '2024-04-24 18:23:30', '2024-04-24 18:23:34', '/img/foodPic/1713954214723kmj.png', 3, 7);
INSERT INTO `food` VALUES (178, 13, '东二焖烤鸡腿饼-蒙古大串', '美味', '2024-04-24 18:23:43', '2024-04-24 18:23:48', '/img/foodPic/1713954228416mgdc.jpg', 5, 23);
INSERT INTO `food` VALUES (179, 14, '东二东北私家菜-锅包肉', '正宗东北锅包肉', '2024-04-24 18:24:16', '2024-04-24 18:24:30', '/img/foodPic/1713954270748guobaorou.jpg', 5, 1);
INSERT INTO `food` VALUES (180, 15, '东二饸饹面-正宗饸饹面', '正宗郏县饸饹面', '2024-04-24 18:25:05', '2024-04-24 18:25:15', '/img/foodPic/1713954315538hlm.jpg', 10, 1);
INSERT INTO `food` VALUES (181, 16, '东三麻辣香锅-土豆泥鸡蛋生菜沙拉', '减脂必备', '2024-04-24 18:26:07', '2024-04-24 18:26:07', '/img/foodPic/tubiao.jpg', 6, 1);
INSERT INTO `food` VALUES (182, 16, '东三麻辣香锅-麻辣香锅', '23元一斤', '2024-04-24 18:26:40', '2024-04-24 19:15:11', '/img/foodPic/1713957311638R-C.png', 23, 1);
INSERT INTO `food` VALUES (183, 17, '西三里脊肉饼-里脊肉饼', '美味可口', '2024-04-24 18:27:34', '2024-04-24 18:27:57', '/img/foodPic/1713954477521ljrbx3.jpg', 6, 1);
INSERT INTO `food` VALUES (184, 17, '西三里脊肉饼-爆裂肠', '美味至极', '2024-04-24 18:27:51', '2024-04-24 18:28:01', '/img/foodPic/1713954481119si3blc.jpg', 3, 2);
INSERT INTO `food` VALUES (185, 18, '东二布袋馍-油炸淀粉肠', '油炸淀粉肠', '2024-04-24 18:28:19', '2024-04-24 18:28:33', '/img/foodPic/1713954513885dfc.jpg', 2, 1);
INSERT INTO `food` VALUES (186, 18, '东二布袋馍-里脊肉饼', '里脊肉饼', '2024-04-24 18:28:29', '2024-04-24 18:28:39', '/img/foodPic/1713954518996d2ljrb.jpg', 6, 1);
INSERT INTO `food` VALUES (187, 13, '东二焖烤鸡腿饼-焖烤鸡腿饼', '焖烤鸡腿饼', '2024-04-24 18:44:22', '2024-04-24 18:44:38', '/img/foodPic/1713955477918share_32fa1c6187769db9fd5ceaa656f3d08d.png', 11, 12);
INSERT INTO `food` VALUES (188, 47, '西一校堡王-双堡套餐', '鸡腿堡+鸡排堡+可乐+小薯，超值套餐', '2024-04-24 19:08:25', '2024-04-24 19:11:54', '/img/foodPic/1713956916355hb.jpg', 20, 122);
INSERT INTO `food` VALUES (189, 47, '西一校堡王-经典鸡腿堡', '经典鸡腿堡', '2024-04-24 19:08:59', '2024-04-24 19:11:59', '/img/foodPic/1713956949183zjhb.jpg', 10, 4);
INSERT INTO `food` VALUES (190, 1, '东二一兜鱼-鸡腿', '', '2024-04-26 11:46:28', '2024-04-26 11:46:35', '/img/foodPic/1714103195379si3blc.jpg', 10, 1);
INSERT INTO `food` VALUES (192, 52, '东三意面-意大利面', '美味', '2024-05-18 10:55:53', '2024-05-18 10:56:29', '/img/foodPic/1716000989161hj2.jpg', 10, 1);
INSERT INTO `food` VALUES (193, 53, '东一胡辣汤-胡辣汤', '', '2024-05-24 21:19:40', '2024-05-24 21:19:40', '/img/foodPic/tubiao.jpg', 4, 1);

-- ----------------------------
-- Table structure for food_list
-- ----------------------------
DROP TABLE IF EXISTS `food_list`;
CREATE TABLE `food_list`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '合集图片',
  `introduction` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '简介',
  `style` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '美食合集' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of food_list
-- ----------------------------
INSERT INTO `food_list` VALUES (1, '你最爱的小吃都在这儿', '/img/foodListPic/1713884890306hj4.jpg', '鸡排、生煎、串串香......你最爱的都在这儿', '特色小吃');
INSERT INTO `food_list` VALUES (2, '不知道想吃什么？来这里看看', '/img/foodListPic/1713885012749hj6.jpg', '这么多米饭快餐食物，绝对有你喜欢的', '米饭快餐');
INSERT INTO `food_list` VALUES (3, '爱吃饺子馄饨的同学看这里', '/img/foodListPic/1713885070896hj.jpg', '馄饨饺子美食大合集', '饺子馄饨');
INSERT INTO `food_list` VALUES (88, '心情不好？这些暖胃粉丝汤或许适合你', '/img/foodListPic/1713885116891R-C.jpg', '都是精挑细选的粉丝汤', '暖胃粉丝汤');
INSERT INTO `food_list` VALUES (89, '这份合集绝对不踩雷', '/img/foodListPic/1713885135781hj2.jpg', '米饭爱好者狂喜', '米饭快餐');
INSERT INTO `food_list` VALUES (90, '炸鸡汉堡', '/img/foodListPic/1713885145973zjhb.jpg', '航院最好吃的炸鸡汉堡全部盘点出来了', '炸鸡汉堡');
INSERT INTO `food_list` VALUES (91, '飘香四溢航院必尝美食', '/img/foodListPic/1713885358243R-C.png', '麻辣烫、麻辣香锅、各类美食', '麻辣烫冒菜');
INSERT INTO `food_list` VALUES (97, '好吃的', '/img/foodListPic/1716001068360hb.jpg', '', '特色小吃');

-- ----------------------------
-- Table structure for food_window
-- ----------------------------
DROP TABLE IF EXISTS `food_window`;
CREATE TABLE `food_window`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '名称',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '所属餐厅（0东1西）',
  `pic` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `introduction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '窗口' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of food_window
-- ----------------------------
INSERT INTO `food_window` VALUES (1, '东二一兜鱼', 0, '/img/foodWindowPic/1713882425153share_32b3c1e2b9b75d6a4e9603d291ec3414.png', '美味的炸鱼、虾滑各种美味美食');
INSERT INTO `food_window` VALUES (2, '西二铁板炒饭炸串', 1, '/img/foodWindowPic/1713882454680share_d048e34acfb7f04a9a9e6c96c7f5f969.png', '铁板炒饭·炸串，各种美味炒饭、牛羊肉串等你来品尝');
INSERT INTO `food_window` VALUES (3, '西二五谷鱼粉', 1, '/img/foodWindowPic/1713882459028share_05deea77e3d278ed32091c375ee1067b.png', '五谷鱼粉欢迎品尝');
INSERT INTO `food_window` VALUES (4, '西二木桶饭', 1, '/img/foodWindowPic/1713882517581share_4f6903ae859ca3b098218ac6c616fae5.png', '美味的木桶饭欢迎品尝');
INSERT INTO `food_window` VALUES (5, '西二锡纸炒鸡', 1, '/img/foodWindowPic/1713882582422share_6e9a81b9a56fe1b1d720c7bb9660f753.png', '锡纸炒鸡，美味至极');
INSERT INTO `food_window` VALUES (6, '西二小馆辣肉面', 1, '/img/foodWindowPic/1713882672761share_51ce80ff7a09029a0b2d302605bf5949.png', '各种美味辣肉面欢迎品尝');
INSERT INTO `food_window` VALUES (7, '西一旋转小火锅', 1, '/img/foodWindowPic/1713882749368share_0e29c776d20188bd5f8c5a6ee86a8576.png', '旋转小火锅，按签子计价');
INSERT INTO `food_window` VALUES (8, '东一牛肉卷饼', 0, '/img/foodWindowPic/1713882767331wx_camera_1712535488466.jpg', '牛肉卷饼非常好吃');
INSERT INTO `food_window` VALUES (9, '西一手工水饺', 1, '/img/foodWindowPic/1713882786688share_37614ef1049978c9480c26938cb1881b.png', '各种口味的手工水饺任你挑选，美味至极');
INSERT INTO `food_window` VALUES (10, '西三烤鱼饭', 1, '/img/foodWindowPic/1713882816875share_a3f3ba929dc767e35fd2eabfe062c436.png', '美味烤鱼饭');
INSERT INTO `food_window` VALUES (11, '西三臊子面', 1, '/img/foodWindowPic/1713882832526share_f927b261c3030369cb7cf31d97bc2498.png', '美味臊子面，快来品尝');
INSERT INTO `food_window` VALUES (12, '西三黄焖鸡米饭', 1, '/img/foodWindowPic/1713882855475share_f3d53f05cb0b70eb0d8cf0a9de1f614d.png', '黄焖鸡、黄焖土豆');
INSERT INTO `food_window` VALUES (13, '东二焖烤鸡腿饼', 0, '/img/foodWindowPic/1713886457341jtb.jpg', '各种美味烤串，经典鸡腿饼欢迎品尝');
INSERT INTO `food_window` VALUES (14, '东二东北私家菜', 0, '/img/foodWindowPic/1713883033023share_d20954090dd1ffa9fb91ae8e3cc252e0.png', '正宗东北菜，欢迎品尝');
INSERT INTO `food_window` VALUES (15, '东二饸饹面', 0, '/img/foodWindowPic/1713883074944share_4848ae529e8aada0d1df88b127c6e3a3.png', '经典郏县饸饹面');
INSERT INTO `food_window` VALUES (16, '东三麻辣香锅', 0, '/img/foodWindowPic/1713882325880mlxg.png', '麻辣香锅、烤鱼、轻食，接受预定');
INSERT INTO `food_window` VALUES (17, '西三里脊肉饼', 1, '/img/foodWindowPic/1713883126749share_2fac9b982a7a4267717d34e064a1dc32.png', '里脊肉饼、爆裂肠');
INSERT INTO `food_window` VALUES (18, '东二布袋馍', 0, '/img/foodWindowPic/hhh.jpg', '布袋馍炸串');
INSERT INTO `food_window` VALUES (47, '西一校堡王', 1, '/img/foodWindowPic/1713957425272zjhb.jpg', '炸鸡汉堡');
INSERT INTO `food_window` VALUES (48, 'ceshi', 0, '/img/foodWindowPic/hhh.jpg', 's');
INSERT INTO `food_window` VALUES (52, '东三意面', 0, '/img/foodWindowPic/hhh.jpg', '');
INSERT INTO `food_window` VALUES (53, '东一胡辣汤', 0, '/img/foodWindowPic/17165567602224.jpg', '胡辣汤');

-- ----------------------------
-- Table structure for list_food
-- ----------------------------
DROP TABLE IF EXISTS `list_food`;
CREATE TABLE `list_food`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `food_id` int NULL DEFAULT NULL COMMENT '美食id',
  `food_list_id` int NULL DEFAULT NULL COMMENT '美食合集id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_listfood_food`(`food_id` ASC) USING BTREE,
  INDEX `fk_listfood_foodlist`(`food_list_id` ASC) USING BTREE,
  CONSTRAINT `fk_listfood_food` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_listfood_foodlist` FOREIGN KEY (`food_list_id`) REFERENCES `food_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 228 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '合集包含美食列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of list_food
-- ----------------------------
INSERT INTO `list_food` VALUES (211, 176, 1);
INSERT INTO `list_food` VALUES (212, 33, 1);
INSERT INTO `list_food` VALUES (213, 185, 1);
INSERT INTO `list_food` VALUES (214, 178, 1);
INSERT INTO `list_food` VALUES (215, 177, 1);
INSERT INTO `list_food` VALUES (216, 171, 1);
INSERT INTO `list_food` VALUES (217, 184, 1);
INSERT INTO `list_food` VALUES (218, 168, 2);
INSERT INTO `list_food` VALUES (219, 167, 2);
INSERT INTO `list_food` VALUES (220, 172, 3);
INSERT INTO `list_food` VALUES (221, 60, 3);
INSERT INTO `list_food` VALUES (222, 164, 88);
INSERT INTO `list_food` VALUES (225, 189, 90);
INSERT INTO `list_food` VALUES (226, 188, 90);

-- ----------------------------
-- Table structure for mark
-- ----------------------------
DROP TABLE IF EXISTS `mark`;
CREATE TABLE `mark`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `consumer_id` int NOT NULL COMMENT '用户id',
  `type_s` tinyint(1) NULL DEFAULT NULL COMMENT '0美食1合集2窗口',
  `food_id` int NULL DEFAULT NULL COMMENT '美食id',
  `food_list_id` int NULL DEFAULT NULL COMMENT '合集id',
  `food_window_id` int NULL DEFAULT NULL COMMENT '窗口id',
  `score` int NULL DEFAULT NULL COMMENT '评分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `consumer_food`(`consumer_id` ASC, `food_id` ASC) USING BTREE,
  UNIQUE INDEX `consumer_food_list`(`consumer_id` ASC, `food_list_id` ASC) USING BTREE,
  UNIQUE INDEX `consumer_food_window`(`consumer_id` ASC, `food_window_id` ASC) USING BTREE,
  INDEX `fk_mark_food`(`food_id` ASC) USING BTREE,
  INDEX `fk_mark_foodlist`(`food_list_id` ASC) USING BTREE,
  INDEX `fk_mark_foodwindow`(`food_window_id` ASC) USING BTREE,
  CONSTRAINT `fk_mark_food` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mark_foodlist` FOREIGN KEY (`food_list_id`) REFERENCES `food_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mark_foodwindow` FOREIGN KEY (`food_window_id`) REFERENCES `food_window` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mark_user` FOREIGN KEY (`consumer_id`) REFERENCES `consumer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '评价' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mark
-- ----------------------------
-- Intentionally empty: personal ratings were removed before publication.

SET FOREIGN_KEY_CHECKS = 1;
