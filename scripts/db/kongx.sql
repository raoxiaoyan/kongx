/*
Navicat MySQL Data Transfer

Source Server         : 10.36.33.60
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : kongx_for_java

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-09-29 17:58:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for kongx_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `kongx_operation_log`;
CREATE TABLE `kongx_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(64) DEFAULT NULL,
  `profile` varchar(32) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `operation_type` varchar(255) DEFAULT NULL,
  `content` json DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_target_hash` (`target`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kongx_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_server_config
-- ----------------------------
DROP TABLE IF EXISTS `kongx_server_config`;
CREATE TABLE `kongx_server_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) DEFAULT NULL,
  `config_value` text,
  `config_type` varchar(255) DEFAULT NULL COMMENT '参数类型',
  `comment` varchar(2048) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `modifier` varchar(255) DEFAULT NULL,
  `modify_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_config_key` (`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kongx_server_config
-- ----------------------------
INSERT INTO `kongx_server_config` VALUES ('1', 'super_admin', 'admin', 'system', '超级管理员', '2019-12-12 16:34:07', 'admin', null, '2019-12-12 16:33:58.156');
INSERT INTO `kongx_server_config` VALUES ('6', 'envs', '[\n    {\n        \"name\": \"开发环境\",\n        \"code\": \"dev\",\n        \"deployType\": \"beta\",\n        \"profiles\": [\n            {\n                \"profile\":\"f\",\n                \"code\":\"betaf\"\n            },\n            {\n                \"profile\":\"p\",\n                \"code\":\"betap\"\n            }\n        ]\n    },\n    {\n        \"name\": \"测试环境\",\n        \"code\": \"beta\",\n        \"deployType\": \"beta\",\n        \"profiles\": [\n            {\n                \"profile\":\"a\",\n                \"code\":\"betaa\"\n            },\n            {\n                \"profile\":\"b\",\n                \"code\":\"betab\"\n            },\n            {\n                \"profile\":\"c\",\n                \"code\":\"betac\"\n            },\n            {\n                \"profile\":\"d\",\n                \"code\":\"betad\"\n            }]\n    }]\n', 'system', '环境列表，默认支持', '2019-12-16 17:06:27', null, null, '2019-12-16 16:37:53.122');
INSERT INTO `kongx_server_config` VALUES ('7', 'envs_extension', '[{\n	\"name\": \"其它环境\",\n        \"code\":\"dev\",\n        \"deployType\":\"infr\",\n	\"profiles\": [ {\n                \"profile\":\"s\",\n                \"code\":\"infrs\"\n            }]\n} ]', 'extension', '环境列表的扩展配置', '2019-12-16 16:09:21', null, null, '2019-12-16 16:08:59.814');
INSERT INTO `kongx_server_config` VALUES ('12', 'auth_server_code', 'auth_server', null, '用户中心的访问地址代码', '2020-01-14 10:53:09', null, null, null);
INSERT INTO `kongx_server_config` VALUES ('13', 'default_domains', '[\"example.com\",\"example1.com\"]', null, '默认的网关域名列表', '2020-01-19 11:43:34', null, null, '2020-01-19 11:43:45.75');
INSERT INTO `kongx_server_config` VALUES ('20', 'config_type', '[{\"label\":\"内置参数\",\"value\":\"system\"},{\"label\":\"扩展参数\",\"value\":\"extension\"}]', 'system', '系统内置参数', '2020-04-09 16:36:52', null, null, '2020-04-09 19:19:56.202');
INSERT INTO `kongx_server_config` VALUES ('21', 'config_type_extension', '[{\"label\":\"其它参数\",\"value\":\"other\"}]', 'extension', '扩展参数类型', '2020-04-09 19:31:05', null, null, '2020-04-09 19:31:05.433');

-- ----------------------------
-- Table structure for kongx_sync_config
-- ----------------------------
DROP TABLE IF EXISTS `kongx_sync_config`;
CREATE TABLE `kongx_sync_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sync_no` varchar(255) DEFAULT NULL,
  `services` json DEFAULT NULL,
  `dest_clients` json DEFAULT NULL,
  `comment` varchar(4000) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `data_type` varchar(255) DEFAULT NULL,
  `policy` varchar(255) DEFAULT NULL,
  `src_client` json DEFAULT NULL,
  `log_type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kongx_sync_config
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `kongx_sync_log`;
CREATE TABLE `kongx_sync_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sync_no` varchar(255) DEFAULT NULL,
  `service` varchar(255) DEFAULT NULL,
  `src_client` varchar(255) DEFAULT NULL,
  `dest_client` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `content` json DEFAULT NULL,
  `comment` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kongx_sync_log
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_system_function
-- ----------------------------
DROP TABLE IF EXISTS `kongx_system_function`;
CREATE TABLE `kongx_system_function` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
  `code` varchar(64) DEFAULT NULL COMMENT '编码',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_icon` varchar(512) DEFAULT NULL COMMENT '图标icon',
  `visit_view` varchar(2048) DEFAULT NULL COMMENT '访问视图',
  `visit_path` varchar(1024) DEFAULT NULL COMMENT '访问路径',
  `use_yn` varchar(6) NOT NULL DEFAULT 'y' COMMENT '是否可用n-不可用，y-可用',
  `menu_type` varchar(20) NOT NULL DEFAULT 'menu' COMMENT '菜单类型(menu=菜单,point=功能点)',
  `application_code` varchar(64) NOT NULL DEFAULT 'kongx' COMMENT '所属系统code',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序，小的在前面',
  PRIMARY KEY (`id`),
  KEY `nh_parentid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- Records of kongx_system_function
-- ----------------------------
INSERT INTO `kongx_system_function` VALUES ('1', '-1', '', 'Dashboard', 'icon-console', '', '/console', 'y', 'menu', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('2', '1', '', '首页', 'icon-dashboard', 'page/wel', 'application', 'y', 'menu', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('4', '5', '', 'Kong Shell', 'icon-shell', 'views/gateway/shell/index', 'shell', 'y', 'menu', 'kongx', '7');
INSERT INTO `kongx_system_function` VALUES ('5', '-1', null, 'Gateway', 'icon-gateway', '', '/gateway', 'y', 'menu', 'kongx', '4');
INSERT INTO `kongx_system_function` VALUES ('6', '5', '', 'Upstreams', 'icon-gateway', 'views/gateway/upstream/index', 'upstream', 'y', 'menu', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('7', '5', null, 'Services', 'icon-services', 'views/gateway/service/index', 'service', 'y', 'menu', 'kongx', '3');
INSERT INTO `kongx_system_function` VALUES ('8', '5', '', 'Routes', 'icon-route', 'views/gateway/routing/index', 'routing', 'y', 'menu', 'kongx', '4');
INSERT INTO `kongx_system_function` VALUES ('9', '5', '', 'Plugins', 'icon-plugin', 'views/gateway/plugin/index', 'plugin', 'y', 'menu', 'kongx', '5');
INSERT INTO `kongx_system_function` VALUES ('10', '-1', '', '系统管理', 'icon-system', '', '/system', 'y', 'menu', 'kongx', '6');
INSERT INTO `kongx_system_function` VALUES ('11', '10', null, '用户管理', 'icon-yonghuguanli', 'views/admin/user/index', 'user', 'y', 'menu', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('12', '10', null, '角色管理', 'icon-quanxianguanli', 'views/admin/role/index', 'role', 'y', 'menu', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('13', '10', null, '菜单管理', 'icon-caidanguanli', 'views/admin/menu/index', 'menu', 'y', 'menu', 'kongx', '4');
INSERT INTO `kongx_system_function` VALUES ('14', '-1', null, '参数管理', 'icon-canshuguanli', '', '/operating', 'y', 'menu', 'kongx', '7');
INSERT INTO `kongx_system_function` VALUES ('15', '14', null, '环境管理', 'icon-huanjingguanli', 'views/operating/env/index', 'envs', 'y', 'menu', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('16', '14', null, '系统参数', 'icon-xitongcanshu', 'views/operating/developers/index', 'params', 'y', 'menu', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('17', '-1', null, '日志管理', 'icon-rizhiguanli1', '', '/audit', 'y', 'menu', 'kongx', '8');
INSERT INTO `kongx_system_function` VALUES ('18', '17', '', '同步日志', 'icon-sync', 'views/operating/synclog/index', 'synclog', 'y', 'menu', 'kongx', '3');
INSERT INTO `kongx_system_function` VALUES ('19', '17', null, '操作日志', 'icon-log', 'views/operating/log/index', 'operationlog', 'y', 'menu', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('20', '6', 'upstream_add', '新增上游代理', '', '', '', 'y', 'point', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('21', '6', 'upstream_update', '修改上游代理', '', '', '', 'y', 'point', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('22', '6', 'upstream_delete', '删除上游代理', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('23', '7', 'service_add', '新增服务', '', '', '', 'y', 'point', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('24', '7', 'service_update', '修改服务', '', '', '', 'y', 'point', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('25', '7', 'service_delete', '删除服务', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('26', '7', 'service_sync', '同步服务', '', '', '', 'y', 'point', 'kongx', '4');
INSERT INTO `kongx_system_function` VALUES ('27', '8', 'route_update', '更新路由', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('28', '8', 'route_delete', '删除路由', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('29', '9', 'plugin_update', '更新插件', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('30', '9', 'plugin_delete', '删除插件', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('31', '9', 'plugin_add', '新增插件', '', '', '', 'y', 'point', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('32', '11', 'user_authority', '用户授权', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('33', '13', 'menu_add', '新建菜单', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('34', '13', 'menu_delete', '删除菜单', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('35', '13', 'menu_update', '修改菜单', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('36', '12', 'role_add', '新建角色', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('37', '12', 'role_update', '修改角色', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('38', '12', 'role_config', '配置权限', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('39', '7', 'service_view', '查看服务', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('40', '6', 'upstream_view', '查看上游代理', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('41', '8', 'route_view', '查看路由', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('42', '9', 'plugin_view', '查看插件', '', '', '', 'y', 'point', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('43', '11', 'user_view', '查看用户列表', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('44', '13', 'menu_view', '查看菜单', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('45', '12', 'role_view', '查看角色', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('46', '16', 'params_view', '查看系统参数', '', '', '', 'y', 'point', 'kongx', '999');
INSERT INTO `kongx_system_function` VALUES ('47', '10', null, '用户组管理', 'icon-yonghuzuguanli', 'views/admin/usergroup/index', 'group', 'y', 'menu', 'kongx', '3');
INSERT INTO `kongx_system_function` VALUES ('48', '47', 'usergroup_add', '新增用户组', '', '', '', 'y', 'point', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('49', '47', 'usergroup_update', '修改用户组', '', '', '', 'y', 'point', 'kongx', '2');
INSERT INTO `kongx_system_function` VALUES ('50', '47', 'usergroup_delete', '删除用户组', '', '', '', 'y', 'point', 'kongx', '3');
INSERT INTO `kongx_system_function` VALUES ('51', '47', 'usergroup_view', '查看用户组', '', '', '', 'y', 'point', 'kongx', '1');
INSERT INTO `kongx_system_function` VALUES ('52', '47', 'usergroup_config', '用户组配置', '', '', '', 'y', 'point', 'kongx', '4');
INSERT INTO `kongx_system_function` VALUES ('53', '5', '', 'Consumers', 'icon-consumer', 'views/gateway/consumer/index', '', 'y', 'menu', 'kongx', '6');
INSERT INTO `kongx_system_function` VALUES ('54', '53', 'consumer_view', 'view consumers', '', '', '', 'y', 'point', 'kongx', '999');

-- ----------------------------
-- Table structure for kongx_system_profile
-- ----------------------------
DROP TABLE IF EXISTS `kongx_system_profile`;
CREATE TABLE `kongx_system_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profile_code` varchar(255) DEFAULT NULL COMMENT '环境编码',
  `name` varchar(255) DEFAULT NULL,
  `env` varchar(255) DEFAULT NULL COMMENT '所属环境',
  `deploy_type` varchar(255) DEFAULT NULL COMMENT '部署类型',
  `ab` varchar(255) DEFAULT NULL COMMENT '简称',
  `url` varchar(255) DEFAULT NULL,
  `consul_url` varchar(255) DEFAULT NULL,
  `config_url` varchar(255) DEFAULT NULL,
  `extensions` varchar(4000) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uh_profile_code` (`profile_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kongx_system_profile
-- ----------------------------
INSERT INTO `kongx_system_profile` VALUES ('28', 'betap', 'betap', 'dev', 'beta', 'p', 'http://p.kong.qa.daling.com:8001/', 'http://p.consul.qa.daling.com:8500', '', '[]', '2020-06-18 16:27:01', 'betap', null, null);
INSERT INTO `kongx_system_profile` VALUES ('30', 'sdeva', 'sdeva', null, null, null, 'http://l-infra1.dev.bj6.daling.com:8001/', 'http://l-infra1.dev.bj6.daling.com:8500', '', '[]', '2020-06-18 16:27:01', 'sdeva', null, null);
INSERT INTO `kongx_system_profile` VALUES ('31', 'betaf', 'betaf', 'dev', 'beta', 'f', 'http://l-infra1.dev.bj6.daling.com:8001/', null, null, '[]', '2020-09-11 17:26:32', 'betaf', null, null);

-- ----------------------------
-- Table structure for kongx_system_role
-- ----------------------------
DROP TABLE IF EXISTS `kongx_system_role`;
CREATE TABLE `kongx_system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_type` varchar(32) DEFAULT NULL COMMENT '角色类型: roleType 菜单角色，数据权限角色',
  `sort_order` bigint(20) DEFAULT NULL COMMENT '排序',
  `use_yn` varchar(6) DEFAULT NULL COMMENT '是否可用n-不可用，y-可用',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_at` timestamp NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `modifier` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `modify_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- Records of kongx_system_role
-- ----------------------------
INSERT INTO `kongx_system_role` VALUES ('1', 'super_admin', '超级管理员', 'menu', null, 'y', null, '2020-05-11 16:00:00', 'admin', 'admin', '2020-09-28 15:05:49');
INSERT INTO `kongx_system_role` VALUES ('2', 'domestic_consumer', '普通用户', 'menu', null, 'y', null, '2020-05-09 16:42:53', 'admin', 'admin', '2020-09-28 15:05:51');

-- ----------------------------
-- Table structure for kongx_system_role_function
-- ----------------------------
DROP TABLE IF EXISTS `kongx_system_role_function`;
CREATE TABLE `kongx_system_role_function` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色Id',
  `half_checked` varchar(11) DEFAULT 'y' COMMENT '是否半选中',
  `function_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色与菜单关系表';

-- ----------------------------
-- Records of kongx_system_role_function
-- ----------------------------
INSERT INTO `kongx_system_role_function` VALUES ('108', '1', 'n', '-1');
INSERT INTO `kongx_system_role_function` VALUES ('109', '1', 'n', '1');
INSERT INTO `kongx_system_role_function` VALUES ('110', '1', 'n', '2');
INSERT INTO `kongx_system_role_function` VALUES ('111', '1', 'n', '4');
INSERT INTO `kongx_system_role_function` VALUES ('112', '1', 'n', '3');
INSERT INTO `kongx_system_role_function` VALUES ('113', '1', 'n', '5');
INSERT INTO `kongx_system_role_function` VALUES ('114', '1', 'n', '6');
INSERT INTO `kongx_system_role_function` VALUES ('115', '1', 'n', '20');
INSERT INTO `kongx_system_role_function` VALUES ('116', '1', 'n', '21');
INSERT INTO `kongx_system_role_function` VALUES ('117', '1', 'n', '22');
INSERT INTO `kongx_system_role_function` VALUES ('118', '1', 'n', '40');
INSERT INTO `kongx_system_role_function` VALUES ('119', '1', 'n', '7');
INSERT INTO `kongx_system_role_function` VALUES ('120', '1', 'n', '23');
INSERT INTO `kongx_system_role_function` VALUES ('121', '1', 'n', '24');
INSERT INTO `kongx_system_role_function` VALUES ('122', '1', 'n', '26');
INSERT INTO `kongx_system_role_function` VALUES ('123', '1', 'n', '25');
INSERT INTO `kongx_system_role_function` VALUES ('124', '1', 'n', '39');
INSERT INTO `kongx_system_role_function` VALUES ('125', '1', 'n', '8');
INSERT INTO `kongx_system_role_function` VALUES ('126', '1', 'n', '27');
INSERT INTO `kongx_system_role_function` VALUES ('127', '1', 'n', '28');
INSERT INTO `kongx_system_role_function` VALUES ('128', '1', 'n', '41');
INSERT INTO `kongx_system_role_function` VALUES ('129', '1', 'n', '9');
INSERT INTO `kongx_system_role_function` VALUES ('130', '1', 'n', '31');
INSERT INTO `kongx_system_role_function` VALUES ('131', '1', 'n', '42');
INSERT INTO `kongx_system_role_function` VALUES ('132', '1', 'n', '29');
INSERT INTO `kongx_system_role_function` VALUES ('133', '1', 'n', '30');
INSERT INTO `kongx_system_role_function` VALUES ('134', '1', 'n', '53');
INSERT INTO `kongx_system_role_function` VALUES ('135', '1', 'n', '54');
INSERT INTO `kongx_system_role_function` VALUES ('136', '1', 'n', '55');
INSERT INTO `kongx_system_role_function` VALUES ('137', '1', 'n', '10');
INSERT INTO `kongx_system_role_function` VALUES ('138', '1', 'n', '11');
INSERT INTO `kongx_system_role_function` VALUES ('139', '1', 'n', '32');
INSERT INTO `kongx_system_role_function` VALUES ('140', '1', 'n', '43');
INSERT INTO `kongx_system_role_function` VALUES ('141', '1', 'n', '12');
INSERT INTO `kongx_system_role_function` VALUES ('142', '1', 'n', '36');
INSERT INTO `kongx_system_role_function` VALUES ('143', '1', 'n', '37');
INSERT INTO `kongx_system_role_function` VALUES ('144', '1', 'n', '38');
INSERT INTO `kongx_system_role_function` VALUES ('145', '1', 'n', '45');
INSERT INTO `kongx_system_role_function` VALUES ('146', '1', 'n', '47');
INSERT INTO `kongx_system_role_function` VALUES ('147', '1', 'n', '51');
INSERT INTO `kongx_system_role_function` VALUES ('148', '1', 'n', '48');
INSERT INTO `kongx_system_role_function` VALUES ('149', '1', 'n', '49');
INSERT INTO `kongx_system_role_function` VALUES ('150', '1', 'n', '50');
INSERT INTO `kongx_system_role_function` VALUES ('151', '1', 'n', '52');
INSERT INTO `kongx_system_role_function` VALUES ('152', '1', 'n', '13');
INSERT INTO `kongx_system_role_function` VALUES ('153', '1', 'n', '33');
INSERT INTO `kongx_system_role_function` VALUES ('154', '1', 'n', '34');
INSERT INTO `kongx_system_role_function` VALUES ('155', '1', 'n', '35');
INSERT INTO `kongx_system_role_function` VALUES ('156', '1', 'n', '44');
INSERT INTO `kongx_system_role_function` VALUES ('157', '1', 'n', '14');
INSERT INTO `kongx_system_role_function` VALUES ('158', '1', 'n', '15');
INSERT INTO `kongx_system_role_function` VALUES ('159', '1', 'n', '16');
INSERT INTO `kongx_system_role_function` VALUES ('160', '1', 'n', '46');
INSERT INTO `kongx_system_role_function` VALUES ('161', '1', 'n', '17');
INSERT INTO `kongx_system_role_function` VALUES ('162', '1', 'n', '19');
INSERT INTO `kongx_system_role_function` VALUES ('163', '1', 'n', '18');

-- ----------------------------
-- Table structure for kongx_system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `kongx_system_user_role`;
CREATE TABLE `kongx_system_user_role` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户Id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色关系表';

-- ----------------------------
-- Records of kongx_system_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_user_group
-- ----------------------------
DROP TABLE IF EXISTS `kongx_user_group`;
CREATE TABLE `kongx_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1024) DEFAULT NULL COMMENT '用户组名称',
  `use_yn` varchar(255) DEFAULT NULL COMMENT '是否可用',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `modifier` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  `modify_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户组';

-- ----------------------------
-- Records of kongx_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_user_group_role
-- ----------------------------
DROP TABLE IF EXISTS `kongx_user_group_role`;
CREATE TABLE `kongx_user_group_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '用户组ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `profile` varchar(255) NOT NULL COMMENT '所属环境',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组与角色关系表';

-- ----------------------------
-- Records of kongx_user_group_role
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_user_group_user
-- ----------------------------
DROP TABLE IF EXISTS `kongx_user_group_user`;
CREATE TABLE `kongx_user_group_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL COMMENT '用户组ID',
  `user_id` varchar(255) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组与用户的关系表';

-- ----------------------------
-- Records of kongx_user_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for kongx_user_info
-- ----------------------------
DROP TABLE IF EXISTS `kongx_user_info`;
CREATE TABLE `kongx_user_info` (
  `user_id` varchar(50) NOT NULL COMMENT '用户Id',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) NOT NULL,
  `status` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '状态',
  `creator` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of kongx_user_info
-- ----------------------------
INSERT INTO `kongx_user_info` VALUES ('admin', 'admin', '', '', '60029c3f5fdb5f1291362f52f7251702507ebc5b', 'activate', 'admin', '2020-09-10 18:21:38');
