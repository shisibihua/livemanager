CREATE DATABASE IF NOT EXISTS hht_livemanager DEFAULT  CHARACTER SET utf8 COLLATE utf8_general_ci;
USE hht_livemanager;
SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `live_program` (
  `live_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL COMMENT '直播标题',
  `type` varchar(50) DEFAULT NULL COMMENT '直播类型',
  `viewers_number` int(11) DEFAULT 0 COMMENT '观看人数',
  `begin_time` datetime DEFAULT NULL COMMENT '直播开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '直播结束时间',
  `stream_code` varchar(100) DEFAULT NULL COMMENT '直播码',
  `details` varchar(255) DEFAULT NULL COMMENT '直播详情',
  `cover` varchar(255) DEFAULT NULL COMMENT '直播封面',
  `create_time` datetime DEFAULT NULL COMMENT '直播创建时间',
  `is_del` int(5) DEFAULT 0 COMMENT '删除状态  0:未删除;1:已删除',
  `status` int(5) DEFAULT 2 COMMENT '直播状态 1：正在直播；2：等待直播；3：结束直播；4:禁用',
  `actual_begin_time` datetime DEFAULT NULL COMMENT '直播实际开始时间',
  `actual_end_time` datetime DEFAULT NULL COMMENT '直播实际结束时间',
  `push_client_ip` varchar(50) DEFAULT NULL COMMENT '推流终端ip',
  `video_frame_rate` int(11) DEFAULT 0 COMMENT '推流帧率(腾讯)FPS',
  `audio_frame_rate` int(11) DEFAULT 0 COMMENT '推流帧率KB/S',
  `bit_rate` int(11) DEFAULT 0 COMMENT '推流码率KB/S',
  `bandwidth` float(11,2) DEFAULT 0 COMMENT '带宽Mbps',
  `traffic_value` float(11,2) DEFAULT 0 COMMENT '流量G',
  `license_code` varchar(50) DEFAULT NULL COMMENT '授权码',
  `hitevision_account` varchar(100) DEFAULT NULL COMMENT '鸿合账户',
  `speaker_name` varchar(50) DEFAULT NULL COMMENT '教师名称',
  `pic_count` int(11) DEFAULT 0 COMMENT '截图数量',
  `school_name` varchar(100) DEFAULT NULL COMMENT '学校名称',
  PRIMARY KEY (`live_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播表';

CREATE TABLE IF NOT EXISTS `live_user` (
  `live_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `gender` int(5) DEFAULT NULL COMMENT '用户性别，性别 1-男 2-女 0-未知',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `status` int(5) DEFAULT NULL COMMENT '用户激活状态：0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用',
  `user_type_id` int(5) DEFAULT NULL COMMENT '用户类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   PRIMARY KEY (`live_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
INSERT INTO `live_user` VALUES ('1','admin', '87d8fc01147faa33730eda2963c4b679','管理员',1, null, null,1,1,null) ON DUPLICATE KEY UPDATE live_user_id=1;


CREATE TABLE IF NOT EXISTS `live_user_type` (
  `live_user_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '用户类型',
   PRIMARY KEY (`live_user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户类型表';
insert into live_user_type(live_user_type_id,name) values(1,'鸿合管理员');

CREATE TABLE IF NOT EXISTS `live_operation_log` (
  `live_operation_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `description` varchar(100) DEFAULT NULL COMMENT '日志描述',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户访问ip',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   PRIMARY KEY (`live_operation_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

CREATE TABLE IF NOT EXISTS `live_sys_log` (
  `live_sys_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `level` varchar(5) DEFAULT NULL COMMENT '日志级别,ERROR,INFO,DEBUG',
  `message` varchar(100) DEFAULT NULL COMMENT '日志描述',
  `description` varchar(100) DEFAULT NULL COMMENT '日志描述',
  `source` varchar(50) DEFAULT NULL COMMENT '来源',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   PRIMARY KEY (`live_sys_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

CREATE TABLE IF NOT EXISTS `live_supervise` (
  `live_supervise_id` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(100) DEFAULT NULL COMMENT '图片链接',
  `stream_code` varchar(100) DEFAULT NULL COMMENT '直播码',
  `screenshot_time` datetime DEFAULT NULL COMMENT '截图时间',
  `create_time` datetime DEFAULT NULL COMMENT '请求时间',
   PRIMARY KEY (`live_supervise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监黄记录表';

-- ----------------------------
-- Table structure for live_config
CREATE TABLE IF NOT EXISTS `live_config` (
  `live_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL  COMMENT '名称',
  `app_id` varchar(50) NOT NULL  COMMENT '应用id',
  `bizid` varchar(50) NOT NULL  COMMENT '直播id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `push_secret_key` varchar(50) NOT NULL COMMENT '推流防盗链Key',
  `api_authentication_key` varchar(50) NOT NULL  COMMENT '鉴权防盗链key',
  `secret_id` varchar(50) NULL  COMMENT '监黄防盗链id',
  `secret_key` varchar(50) NULL  COMMENT '监黄防盗链key',
  PRIMARY KEY (`live_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*INSERT INTO `live_config` VALUES ('1', '腾讯云', '1251626075', '2072', '2018-09-19 13:12:44','f8765f0595fd0e38aabc271eddc040c2',
 '5333bd41fef8f6e7bfb0a7581a8e2d04','AKID56wnaILDRUM5f4qVZXK1Xv891icDF7Uk','LafYGpEigWDl9MSN8iBTlKnBPVTI7rQp');*/
 INSERT INTO `live_config` VALUES ('1', '腾讯云', '1251498379', '2152', '2018-09-19 13:12:44','8719d14f15c54ac69a54966267a15e02',
 '3d24958349fc49908244a054dab88b64','AKIDL0VnqLV33vhJguixaylOmunfZGkyM6pI','B7oFRJz1oQQFo8IIK7e5HzVt21kiT53u');

-- ----------------------------
-- Table structure for live_license
-- ----------------------------
CREATE TABLE IF NOT EXISTS `live_license` (
   `live_license_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `contact_number` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT NULL COMMENT '状态 0 禁用，1 启用',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `license_code` varchar(50) DEFAULT NULL COMMENT '授权码',
  `city_id` int(11) NOT NULL COMMENT '市id',
  `province_id` int(11) NOT NULL COMMENT '省id',
  `county_id` int(11) NOT NULL COMMENT '区id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`live_license_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='直播授权';

-- ----------------------------
-- Table structure for live_license_attachment
-- ----------------------------
CREATE TABLE IF NOT EXISTS `live_license_attachment` (
  `live_auth_attachment_id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) NOT NULL  COMMENT '路径',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `live_license_id` int(11) NOT NULL  COMMENT '关联授权id',
  `name` varchar(255) NOT NULL COMMENT '文件名称',
  PRIMARY KEY (`live_auth_attachment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='授权附件';

-- ----------------------------
-- Table structure for live_max_limit
-- ----------------------------
CREATE TABLE IF NOT EXISTS `live_max_limit` (
  `live_max_limit_id` int(11) NOT NULL AUTO_INCREMENT,
  `max_count` int(11) DEFAULT NULL  COMMENT '直播数量限制',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`live_max_limit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `live_max_limit` VALUES ('1', '10', '2018-09-12 10:12:54');

-- ----------------------------
-- Table structure for live_history_d
-- ----------------------------
CREATE TABLE IF NOT EXISTS `live_history_d` (
  `live_history_d_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` date DEFAULT NULL COMMENT '创建日期',
  `time_point` time DEFAULT NULL COMMENT '时间点',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `viewers_number` int(11) DEFAULT NULL COMMENT '观看人数',
  `pic_count` int(11) DEFAULT NULL COMMENT '截图数量',
  `traffic_value` float(11,2) DEFAULT NULL COMMENT '流量G',
  `live_count` int(11) DEFAULT NULL COMMENT '直播数量',
  PRIMARY KEY (`live_history_d_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='直播历史记录（每天）';


-- ----------------------------
-- Table change 2018-12-28
-- ----------------------------
ALTER TABLE `live_program`
ADD COLUMN `stream_code_device`  varchar(10) NULL COMMENT '设备使用直播码' AFTER `school_name`;