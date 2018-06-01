CREATE TABLE `simple_message` (
  `id` varchar(40) NOT NULL COMMENT '主键ID',
  `message` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `sender` varchar(11) DEFAULT NULL COMMENT '发送者id，对应用户表主键id',
  `receiver` varchar(11) DEFAULT NULL COMMENT '接受者id，对应用户表主键id',
  `create_dt` datetime DEFAULT NULL COMMENT '消息发送时间',
  `update_dt` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息内容表';
