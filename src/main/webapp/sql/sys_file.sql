CREATE TABLE `sys_file` (
  `fileid` int(4) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `filename` varchar(50) DEFAULT NULL COMMENT '文件名',
  `createdate` datetime DEFAULT NULL COMMENT '上传时间',
  `filetype` varchar(30) DEFAULT NULL COMMENT '文件类型',
  `filepath` varchar(100) DEFAULT NULL COMMENT '文件存放地址',
  PRIMARY KEY (`fileid`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8 COMMENT='文件上传表';