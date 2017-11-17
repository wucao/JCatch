CREATE TABLE `t_app` (
  `id` VARCHAR(255) NOT NULL COMMENT 'ID',
  `name` VARCHAR(255) NOT NULL COMMENT '应用名称',
  `description` VARCHAR(1000) NOT NULL COMMENT '应用介绍',
  `secret_key` VARCHAR(255) NOT NULL COMMENT '密钥',
  `subscriber` VARCHAR(1000) DEFAULT NULL COMMENT '订阅者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_exception` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` varchar(255) NOT NULL COMMENT '应用ID',
  `remote_addr` varchar(255) NOT NULL COMMENT '远程IP',
  `exception_name` varchar(255) DEFAULT NULL COMMENT '异常类型',
  `message` text COMMENT '异常message',
  `stack_trace` longtext COMMENT '异常StackTrace',
  `class_name` varchar(1000) DEFAULT NULL COMMENT '类名',
  `method_name` varchar(1000) DEFAULT NULL COMMENT '方法名',
  `file_name` varchar(1000) DEFAULT NULL COMMENT '文件名',
  `line_number` int(11) DEFAULT NULL COMMENT '行号',
  `occurrence_number` int(11) DEFAULT 1 COMMENT '异常次数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_submit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;