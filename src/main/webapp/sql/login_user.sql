CREATE TABLE `login_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户登录表ID',
  `USERNAME` varchar(30) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(50) NOT NULL COMMENT '登录密码',
  `LAST_DATE` date DEFAULT NULL COMMENT '上次登录时间',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建时间',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '更新时间',
  `LOGIN_STATUS` int(4) DEFAULT '0' COMMENT '登录状态, 0.未登录, 1.已登录',
  `USER_ID` int(4) DEFAULT NULL COMMENT '对应数据信息表id',
  `HEAD_FILE_ID` int(11) DEFAULT NULL COMMENT '头像文件id,对应sysfile表id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户登录表';