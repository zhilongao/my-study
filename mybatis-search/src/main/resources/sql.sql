-- 创建用户数据表
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;


-- 清空用户表数据
TRUNCATE TABLE user;

-- 向用户表插入测试数据
INSERT INTO `user` VALUES (1, 'Jone', 18, 'Jone@126.com');
INSERT INTO `user` VALUES (2, 'Jack', 20, 'Jack@126.com');
INSERT INTO `user` VALUES (3, 'Tom', 28, 'Tom@126.com');
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'Sandy@126.com');
INSERT INTO `user` VALUES (5, 'Billie', 24, 'Billie@126.com');