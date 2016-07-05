/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50132
Source Host           : localhost:3306
Source Database       : invoicing_mgmt

Target Server Type    : MYSQL
Target Server Version : 50132
File Encoding         : 65001

Date: 2016-07-02 19:18:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for im_category
-- ----------------------------
DROP TABLE IF EXISTS `im_category`;
CREATE TABLE `im_category` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_category
-- ----------------------------
INSERT INTO `im_category` VALUES ('93d68995-ca47-429d-a740-32cb6959f178', '酒', '2016-06-14 16:22:04');
INSERT INTO `im_category` VALUES ('a62a079a-c8d0-4136-b60c-7c692c8c7aeb', '米', '2016-05-23 09:35:06');

-- ----------------------------
-- Table structure for im_company
-- ----------------------------
DROP TABLE IF EXISTS `im_company`;
CREATE TABLE `im_company` (
  `company` varchar(255) DEFAULT NULL,
  `id` varchar(36) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_company
-- ----------------------------
INSERT INTO `im_company` VALUES ('王尼玛有限公司', '6b96cf4e-7c99-44ed-87a8-3a4f6e51678b', '2016-06-26 20:12:22');
INSERT INTO `im_company` VALUES ('黄海狗', 'ad32bb15-a376-41bd-9237-381fd346a624', '2016-07-02 14:38:13');

-- ----------------------------
-- Table structure for im_employees
-- ----------------------------
DROP TABLE IF EXISTS `im_employees`;
CREATE TABLE `im_employees` (
  `name` varchar(255) DEFAULT NULL,
  `id` varchar(36) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `company_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_employees
-- ----------------------------
INSERT INTO `im_employees` VALUES ('121212', '8a190c24-f985-42b6-94aa-ec2f4017d09a', '2016-07-02 14:20:12', '6b96cf4e-7c99-44ed-87a8-3a4f6e51678b');
INSERT INTO `im_employees` VALUES ('nm ', '958ef1ce-dd42-46c6-992a-645da3b76c84', '2016-07-02 16:02:50', 'ad32bb15-a376-41bd-9237-381fd346a624');
INSERT INTO `im_employees` VALUES ('1221121212', 'b359a3a1-deef-4c72-a947-63eb57001c13', '2016-07-02 14:20:06', '6b96cf4e-7c99-44ed-87a8-3a4f6e51678b');

-- ----------------------------
-- Table structure for im_employee_sale
-- ----------------------------
DROP TABLE IF EXISTS `im_employee_sale`;
CREATE TABLE `im_employee_sale` (
  `id` varchar(36) NOT NULL,
  `specifications` varchar(255) DEFAULT NULL,
  `im_warehouse_id` varchar(36) DEFAULT NULL,
  `im_species_id` varchar(36) DEFAULT NULL,
  `sale_price` double(10,2) DEFAULT NULL COMMENT '单价',
  `num` bigint(20) DEFAULT NULL,
  `im_employee_id` varchar(36) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `pro_no` bigint(20) DEFAULT NULL COMMENT '提货单号',
  `billing_by` varchar(255) NOT NULL COMMENT '开单人',
  `count_price` double(10,2) DEFAULT NULL COMMENT '总额',
  `is_price` tinyint(1) DEFAULT '0' COMMENT '是否付款 0:已付款 1：未付款',
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_employee_sale
-- ----------------------------
INSERT INTO `im_employee_sale` VALUES ('1d2c6a93-b922-42b8-92d1-70f3a3373289', '12ml', null, null, '12.23', '12', null, null, '12', '12', '146.76', '1', '12');
INSERT INTO `im_employee_sale` VALUES ('6ba1ef4b-6ac6-48e7-9ad2-806d566c8df0', '12ml', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '84cd2b16-0cad-4cc7-8340-4006f711c86a', '12.23', '12', '8a190c24-f985-42b6-94aa-ec2f4017d09a', null, '12', '12', '146.76', '1', '12');
INSERT INTO `im_employee_sale` VALUES ('73715eee-4076-488e-86fa-9405e7c39bde', '12ml', null, '84cd2b16-0cad-4cc7-8340-4006f711c86a', '12.23', '12', '8a190c24-f985-42b6-94aa-ec2f4017d09a', null, '12', '12', '146.76', '1', '12');
INSERT INTO `im_employee_sale` VALUES ('8e633b6f-8d2d-4e25-ab04-4d822df060b1', '12ml', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '84cd2b16-0cad-4cc7-8340-4006f711c86a', '12.23', '12', '8a190c24-f985-42b6-94aa-ec2f4017d09a', '2016-07-02 18:23:22', '12', '12', '146.76', '1', '12');
INSERT INTO `im_employee_sale` VALUES ('be3cb97d-34be-45bd-bb64-1cce8db0e09d', '12ml', null, null, '12.23', '12', '8a190c24-f985-42b6-94aa-ec2f4017d09a', null, '12', '12', '146.76', '1', '12');
INSERT INTO `im_employee_sale` VALUES ('f6ddbed0-8f15-42ee-955f-3045a6e7aea0', '12ml', null, null, '12.23', '1', '8a190c24-f985-42b6-94aa-ec2f4017d09a', null, '12', '12', '12.23', '1', '12');

-- ----------------------------
-- Table structure for im_species
-- ----------------------------
DROP TABLE IF EXISTS `im_species`;
CREATE TABLE `im_species` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_species
-- ----------------------------
INSERT INTO `im_species` VALUES ('255de564-6bd2-4bf3-b144-95ab98a102f6', '人头', '2016-05-23 09:35:36', 'a62a079a-c8d0-4136-b60c-7c692c8c7aeb');
INSERT INTO `im_species` VALUES ('3681907d-1db1-44f2-8320-0606da8eede0', 'fwefew', '2016-06-26 18:00:38', 'a62a079a-c8d0-4136-b60c-7c692c8c7aeb');
INSERT INTO `im_species` VALUES ('4f70e6d8-bd1e-4234-a299-2bfbccb5cc43', '121', '2016-05-30 15:36:10', '93d68995-ca47-429d-a740-32cb6959f178');
INSERT INTO `im_species` VALUES ('84cd2b16-0cad-4cc7-8340-4006f711c86a', '雪花', '2016-05-23 09:35:21', '93d68995-ca47-429d-a740-32cb6959f178');
INSERT INTO `im_species` VALUES ('a51ac03e-9302-4b90-ae33-75abf533e06c', '飞', '2016-06-26 17:59:29', '93d68995-ca47-429d-a740-32cb6959f178');

-- ----------------------------
-- Table structure for im_stock
-- ----------------------------
DROP TABLE IF EXISTS `im_stock`;
CREATE TABLE `im_stock` (
  `id` varchar(36) NOT NULL,
  `inventory` bigint(255) DEFAULT NULL COMMENT '库存',
  `specifications` varchar(255) DEFAULT NULL COMMENT '规格',
  `inside_price` double(10,2) DEFAULT NULL COMMENT '外部价',
  `outside_price` double(10,2) DEFAULT NULL COMMENT '内部价',
  `purchase_price` double(10,2) DEFAULT NULL COMMENT '进货价',
  `update_time` datetime DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `im_warehouse_id` varchar(36) DEFAULT NULL COMMENT '仓库id',
  `im_species_id` varchar(36) DEFAULT NULL COMMENT '品种id',
  `promotion_endtime` datetime DEFAULT NULL COMMENT '促销截止时间',
  `promotion_price` double(10,2) DEFAULT NULL COMMENT '促销价',
  `is_promotion` tinyint(1) DEFAULT '0' COMMENT '是否促销 0：否 1：是',
  `im_lock` tinyint(1) DEFAULT '0' COMMENT '锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_stock
-- ----------------------------
INSERT INTO `im_stock` VALUES ('113930da-6a37-4f54-85e7-fdcf32bdab00', '4', '13ml', '10.00', '10.00', '10.00', '2016-06-02 17:35:02', '2016-06-02 17:35:02', '22fdf0e3-3720-4971-9a46-b702aaa52817', '3681907d-1db1-44f2-8320-0606da8eede0', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('1212', '13', '30ml', '100.00', '120.00', '80.00', '2016-05-16 16:19:41', '2016-05-01 16:19:49', 'e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '255de564-6bd2-4bf3-b144-95ab98a102f6', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('319f94da-7d0e-4409-a61a-ba91904f53c0', '12', '12ml', '12.00', '12.00', '12.00', '2016-07-02 15:33:53', '2016-07-02 15:33:53', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '84cd2b16-0cad-4cc7-8340-4006f711c86a', '2016-07-01 00:00:00', '12.23', '1', '0');
INSERT INTO `im_stock` VALUES ('358ad31b-c51a-4500-bb48-14385a329d35', '10', '30ml', '1.00', '1.00', '1.00', '2016-05-31 11:08:13', '2016-05-31 11:08:14', '22fdf0e3-3720-4971-9a46-b702aaa52817', '255de564-6bd2-4bf3-b144-95ab98a102f6', null, null, '0', '1');
INSERT INTO `im_stock` VALUES ('49d35d65-a7b9-4b38-bd71-d7bf12d45f1a', '1100', '12ml', '12.00', '12.00', '12.00', '2016-06-02 17:21:05', '2016-06-02 17:21:05', 'e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '3681907d-1db1-44f2-8320-0606da8eede0', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('5c640a88-3389-4023-85b0-57f864295eb6', '992', '13ml', '10.00', '10.00', '10.00', '2016-06-14 08:43:47', '2016-06-14 08:43:47', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '3681907d-1db1-44f2-8320-0606da8eede0', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('5e4cc401-80ba-499f-9dd8-146dde4cac6b', '100', '100', '90.00', '110.00', '80.00', '2016-06-26 16:13:12', '2016-06-26 16:12:13', '22fdf0e3-3720-4971-9a46-b702aaa52817', 'a51ac03e-9302-4b90-ae33-75abf533e06c', '2016-06-15 00:00:00', '555.00', '1', '0');
INSERT INTO `im_stock` VALUES ('62f6e22e-a153-4dd8-8c0b-00e65e70a03e', '4', '13ml', '10.00', '10.00', '10.00', '2016-06-03 17:14:56', '2016-06-03 17:14:56', 'e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '3681907d-1db1-44f2-8320-0606da8eede0', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('9b94e5de-54fa-4815-944a-f03ed67cb0da', '2', '30', '12.00', '12.00', '12.00', '2016-05-31 11:08:01', '2016-05-31 11:08:01', 'e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '84cd2b16-0cad-4cc7-8340-4006f711c86a', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('d6f1c63c-f933-44df-9191-1bb7c4f5b172', '12', 'gg', '12.00', '12.00', '12.00', '2016-06-26 18:59:31', '2016-06-26 18:59:31', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '4f70e6d8-bd1e-4234-a299-2bfbccb5cc43', '2016-06-22 00:00:00', '121.00', '0', '0');
INSERT INTO `im_stock` VALUES ('e0170874-0d5c-4446-8057-1a3e1d2f7fd3', '1', '12', '1.00', '1.00', '1.00', '2016-06-14 16:03:53', '2016-06-14 16:03:53', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '4f70e6d8-bd1e-4234-a299-2bfbccb5cc43', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('e0af7188-aefb-4b90-972a-a4f656b785f4', '11958', '12ml', '12.00', '12.00', '12.00', '2016-05-30 15:31:53', '2016-05-30 15:31:53', '22fdf0e3-3720-4971-9a46-b702aaa52817', '3681907d-1db1-44f2-8320-0606da8eede0', null, null, '0', '0');
INSERT INTO `im_stock` VALUES ('fd48cd29-6cb9-4cbb-9e1b-e78d1a0f688d', '900', '100', '90.00', '110.00', '80.00', '2016-06-26 16:12:13', '2016-06-26 16:12:13', '49ff7b96-79a5-4d9a-b00e-75ff100973b2', 'a51ac03e-9302-4b90-ae33-75abf533e06c', null, null, '0', '0');

-- ----------------------------
-- Table structure for im_stock_log
-- ----------------------------
DROP TABLE IF EXISTS `im_stock_log`;
CREATE TABLE `im_stock_log` (
  `id` varchar(36) NOT NULL,
  `im_warehouse_id` varchar(36) DEFAULT NULL COMMENT '仓库id',
  `im_species_id` varchar(36) DEFAULT NULL COMMENT '品种id',
  `specifications` varchar(255) DEFAULT NULL COMMENT '规格',
  `operate_num` bigint(20) DEFAULT NULL COMMENT '操作数量',
  `operate_action` int(255) DEFAULT NULL COMMENT '操作状态 0：出库 1：入库',
  `update_time` datetime DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operate_by` varchar(255) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_stock_log
-- ----------------------------
INSERT INTO `im_stock_log` VALUES ('12b122fd-d382-4994-a06d-c5ecbc420122', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '2', '0', null, '2016-06-14 10:33:54', 'admin');
INSERT INTO `im_stock_log` VALUES ('49e654e8-8038-40bb-a6f6-d29faec24c8a', '00b51b49-d7ad-48c2-8b52-a63fc6905fef', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '1', '0', null, '2016-06-14 10:05:15', 'admin');
INSERT INTO `im_stock_log` VALUES ('cf28d232-a10d-45f1-8fe0-ffe277ea6e6f', 'e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '2', '1', null, '2016-06-14 10:34:15', 'admin');
INSERT INTO `im_stock_log` VALUES ('d0cc6d1b-4142-49ab-8cf8-ae25f1db0f1f', '22fdf0e3-3720-4971-9a46-b702aaa52817', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '2', '0', null, '2016-06-14 10:34:15', 'admin');
INSERT INTO `im_stock_log` VALUES ('d13b22f7-70b3-48ad-80a7-3567d8488d39', '49ff7b96-79a5-4d9a-b00e-75ff100973b2', 'a51ac03e-9302-4b90-ae33-75abf533e06c', '100', '100', '0', null, '2016-06-26 16:13:12', 'admin');
INSERT INTO `im_stock_log` VALUES ('ef53e973-bd00-41ae-b84e-a7205e52e134', '22fdf0e3-3720-4971-9a46-b702aaa52817', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '1', '1', null, '2016-06-14 10:05:15', 'admin');
INSERT INTO `im_stock_log` VALUES ('f2edf58d-4714-4d30-ab98-1b770de9f52d', '22fdf0e3-3720-4971-9a46-b702aaa52817', '3681907d-1db1-44f2-8320-0606da8eede0', '13ml', '2', '1', null, '2016-06-14 10:33:54', 'admin');
INSERT INTO `im_stock_log` VALUES ('f320abc5-ec7a-450b-b2dd-2468e5d11057', '22fdf0e3-3720-4971-9a46-b702aaa52817', 'a51ac03e-9302-4b90-ae33-75abf533e06c', '100', '100', '1', null, '2016-06-26 16:13:12', 'admin');

-- ----------------------------
-- Table structure for im_sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `im_sys_permissions`;
CREATE TABLE `im_sys_permissions` (
  `id` varchar(36) NOT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_sys_permissions
-- ----------------------------
INSERT INTO `im_sys_permissions` VALUES ('051eca11-fc63-4980-9b4e-ba0e41abe3dc', 'systemManager:userManager:list', '用户管理', '1', '2015-12-04 16:35:11');
INSERT INTO `im_sys_permissions` VALUES ('0cb770de-924d-4ba5-92e6-f6ecc129475e', 'systemManager:sysuserManager:edit', '系统用户更新', '1', '2015-12-07 16:10:26');
INSERT INTO `im_sys_permissions` VALUES ('0cf77e72-02e9-4950-b8ae-53723caa11df', 'dataManager:categoryManager:*', '数据管理：类别管理：所有', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('14406002-2d36-4337-822c-bc4f5d60a605', 'systemManager:*:*', '系统管理：菜单管理：所有', '1', '2015-12-04 14:29:35');
INSERT INTO `im_sys_permissions` VALUES ('28d2d205-e13c-42a3-9055-6624600751f9', 'productManager:shipmentManager:*', '商品管理：出货管理：所有', '1', '2016-07-02 15:11:10');
INSERT INTO `im_sys_permissions` VALUES ('30822f01-245a-496c-96c4-a157dbca0b00', 'productManager:imStockLogManager:*', '商品管理：出货/进货日志：所有', '1', '2016-07-02 15:12:23');
INSERT INTO `im_sys_permissions` VALUES ('38faf257-b0a1-4e3a-948f-a879e0d1859e', 'productManager:imStockManager:*', '商品管理：进货管理：所有', '1', '2016-07-02 15:11:10');
INSERT INTO `im_sys_permissions` VALUES ('3b792f2b-9029-4f55-bf7e-9ab956d0ab70', 'systemManager:imStockManager:list', '进货管理列表', '1', '2016-05-30 17:20:45');
INSERT INTO `im_sys_permissions` VALUES ('490a12c6-66b8-4288-bb74-c86e6696228f', 'productManager:imStockManager:list', '商品管理：进货管理：列表', '1', '2016-07-02 15:11:10');
INSERT INTO `im_sys_permissions` VALUES ('5a39bfad-1a8e-43c0-b8d6-d3e3bfe47b41', 'systemManager:sysuserManager:delete', '系统用户删除', '1', '2015-12-07 16:11:28');
INSERT INTO `im_sys_permissions` VALUES ('5f89739f-ecfd-4607-946f-d32942552f92', 'saleManager:imEmployeeSaleManager:list', '销售管理：销售列表：列表', '1', '2016-07-02 19:02:42');
INSERT INTO `im_sys_permissions` VALUES ('6e7ee822-0e74-4b57-a720-1f8c08b7113c', 'dataManager:imWarehouseManager:*', '数据管理：仓库管理：所有', '1', '2016-07-02 15:01:51');
INSERT INTO `im_sys_permissions` VALUES ('71c31258-58fc-4c4e-80e9-b04f513b604f', 'systemManager:sysuserManager:*', '系统用户所有', '1', '2015-12-07 16:12:19');
INSERT INTO `im_sys_permissions` VALUES ('90c04fd5-c3b6-4753-886a-770d89b6ed0e', 'systemManager:sysuserManager:list', '系统用户列表', '1', '2015-12-04 16:32:03');
INSERT INTO `im_sys_permissions` VALUES ('981c8cd8-9f2d-4f6b-8595-b4ffd6227891', 'dataManager:speciesManager:list', '数据管理：品种管理：列表', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('a07cc3e3-195d-4f19-b2cf-8e17173064cf', 'systemManager:sysuserManager:add', '系统用户添加', '1', '2015-12-07 14:31:28');
INSERT INTO `im_sys_permissions` VALUES ('a8164ff6-d0a5-4947-b97c-bbff8cf170d3', 'productManager:shipmentManager:list', '商品管理：出货管理：列表', '1', '2016-07-02 15:11:10');
INSERT INTO `im_sys_permissions` VALUES ('a8a28fe4-e6f1-4d85-ac9a-b59343cd3028', 'saleManager:imEmployeeSaleManager:*', '销售管理：销售列表：所有', '1', '2016-07-02 19:02:42');
INSERT INTO `im_sys_permissions` VALUES ('c0cbc05f-467b-4f72-80d8-74a76e7b9989', 'dataManager:companyManager:list', '数据管理：公司管理：列表', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('d25f78ec-cc01-4186-b1f3-2782634bd8c4', 'dataManager:companyManager:*', '数据管理：公司管理：所有', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('d6428328-f859-4b6c-b5d0-390b98cbe433', 'dataManager:speciesManager:*', '数据管理：品种管理：所有', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('d7d389fc-caa6-4b56-a3c3-b02178dc96bc', 'dataManager:employeeManager:*', '数据管理：员工管理：所有', '1', '2016-07-02 15:06:41');
INSERT INTO `im_sys_permissions` VALUES ('ddc97b6e-656e-4333-9da1-c308add63c9c', 'dataManager:employeeManager:list', '数据管理：员工管理：列表', '1', '2016-07-02 15:06:41');
INSERT INTO `im_sys_permissions` VALUES ('e8c65fc4-e6aa-40d3-bc93-7a84007d68bf', 'dataManager:imWarehouseManager:list', '数据管理：仓库管理：列表', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('ec28db8b-d62d-4a27-9389-6fa23633fcd2', 'dataManager:categoryManager:list', '数据管理：类别管理：列表', '1', '2016-07-02 15:01:54');
INSERT INTO `im_sys_permissions` VALUES ('f04b39da-f903-4e69-88d7-1f18bc62a845', 'productManager:imStockLogManager:list', '商品管理：出货/进货日志：列表', '1', '2016-07-02 15:12:23');

-- ----------------------------
-- Table structure for im_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `im_sys_role`;
CREATE TABLE `im_sys_role` (
  `id` varchar(36) NOT NULL,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_sys_role
-- ----------------------------
INSERT INTO `im_sys_role` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'admin', '管理员', '1', '2015-12-04 14:25:12');
INSERT INTO `im_sys_role` VALUES ('1d88622e-da25-4990-9ca4-9a689f92c5ec', 'lishun', 'lishun', '1', '2016-05-30 17:21:27');
INSERT INTO `im_sys_role` VALUES ('d7c0b365-a41d-45f0-bcc3-b6ba207d7c00', 'system', '系统用户管理', '1', '2015-12-04 16:32:31');

-- ----------------------------
-- Table structure for im_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `im_sys_role_permission`;
CREATE TABLE `im_sys_role_permission` (
  `role_id` varchar(36) NOT NULL,
  `permission_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_sys_role_permission
-- ----------------------------
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '051eca11-fc63-4980-9b4e-ba0e41abe3dc');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '0cb770de-924d-4ba5-92e6-f6ecc129475e');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '0cf77e72-02e9-4950-b8ae-53723caa11df');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '14406002-2d36-4337-822c-bc4f5d60a605');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '28d2d205-e13c-42a3-9055-6624600751f9');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '30822f01-245a-496c-96c4-a157dbca0b00');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '38faf257-b0a1-4e3a-948f-a879e0d1859e');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '3b792f2b-9029-4f55-bf7e-9ab956d0ab70');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '490a12c6-66b8-4288-bb74-c86e6696228f');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '5a39bfad-1a8e-43c0-b8d6-d3e3bfe47b41');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '5f89739f-ecfd-4607-946f-d32942552f92');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '6e7ee822-0e74-4b57-a720-1f8c08b7113c');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '71c31258-58fc-4c4e-80e9-b04f513b604f');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '90c04fd5-c3b6-4753-886a-770d89b6ed0e');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', '981c8cd8-9f2d-4f6b-8595-b4ffd6227891');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'a07cc3e3-195d-4f19-b2cf-8e17173064cf');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'a8164ff6-d0a5-4947-b97c-bbff8cf170d3');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'a8a28fe4-e6f1-4d85-ac9a-b59343cd3028');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'c0cbc05f-467b-4f72-80d8-74a76e7b9989');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'd25f78ec-cc01-4186-b1f3-2782634bd8c4');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'd6428328-f859-4b6c-b5d0-390b98cbe433');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'd7d389fc-caa6-4b56-a3c3-b02178dc96bc');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'ddc97b6e-656e-4333-9da1-c308add63c9c');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'e8c65fc4-e6aa-40d3-bc93-7a84007d68bf');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'ec28db8b-d62d-4a27-9389-6fa23633fcd2');
INSERT INTO `im_sys_role_permission` VALUES ('08fd39c4-bdd9-4d2a-b23e-924d0288a71c', 'f04b39da-f903-4e69-88d7-1f18bc62a845');
INSERT INTO `im_sys_role_permission` VALUES ('1d88622e-da25-4990-9ca4-9a689f92c5ec', '3b792f2b-9029-4f55-bf7e-9ab956d0ab70');
INSERT INTO `im_sys_role_permission` VALUES ('d7c0b365-a41d-45f0-bcc3-b6ba207d7c00', '90c04fd5-c3b6-4753-886a-770d89b6ed0e');

-- ----------------------------
-- Table structure for im_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `im_sys_user`;
CREATE TABLE `im_sys_user` (
  `id` varchar(36) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `realname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_sys_user
-- ----------------------------
INSERT INTO `im_sys_user` VALUES ('253442ac-2b9b-4b3a-92a9-a7cc0e3601dd', 'h', '2ea4874b546089ad71f1ccbf1d9579e8', '', '5401684ee7d611e8bea113c9dd25e6e3', '1', '2016-06-03 17:18:50', 'wqwrqwrqw');
INSERT INTO `im_sys_user` VALUES ('9b38c1f4-0ffb-4dd5-8270-315fcfb60c60', 'a', 'dd31eaeb66b68d1709e21bbc3a5a987e', 'lishun1005@sohu.com', 'f0af4afc22fc44a57372739f4a9a7b23', '0', '2016-05-20 16:50:19', null);
INSERT INTO `im_sys_user` VALUES ('b475b8ed-4a98-4125-abe4-181030c43fbc', 'admin', 'ec4341432831c56747bdece41e4ddd0e', 'www.wm@1234.com', '220e9469ed84428f114dd3fbbeba7830', '0', '2015-12-07 09:50:57', '黄海狗3');
INSERT INTO `im_sys_user` VALUES ('dd3d02c2-d9b6-476a-8db6-d0b5ecb924d9', 'lishun', '46229bbf221bea8579810edc347236ec', 'lishun1005@sohu.com', 'b869d9e86f06068ffde12fe5727533b0', '0', '2016-05-30 17:21:43', null);

-- ----------------------------
-- Table structure for im_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `im_sys_user_role`;
CREATE TABLE `im_sys_user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_sys_user_role
-- ----------------------------
INSERT INTO `im_sys_user_role` VALUES ('43d1d00b-407c-4134-b069-d33be6a00167', 'd7c0b365-a41d-45f0-bcc3-b6ba207d7c00');
INSERT INTO `im_sys_user_role` VALUES ('634cb080-1490-4669-aa99-21057de6ba27', '08fd39c4-bdd9-4d2a-b23e-924d0288a71c');
INSERT INTO `im_sys_user_role` VALUES ('634cb080-1490-4669-aa99-21057de6ba27', 'd7c0b365-a41d-45f0-bcc3-b6ba207d7c00');
INSERT INTO `im_sys_user_role` VALUES ('b475b8ed-4a98-4125-abe4-181030c43fbc', '08fd39c4-bdd9-4d2a-b23e-924d0288a71c');
INSERT INTO `im_sys_user_role` VALUES ('b475b8ed-4a98-4125-abe4-181030c43fbc', 'd7c0b365-a41d-45f0-bcc3-b6ba207d7c00');
INSERT INTO `im_sys_user_role` VALUES ('dd3d02c2-d9b6-476a-8db6-d0b5ecb924d9', '1d88622e-da25-4990-9ca4-9a689f92c5ec');

-- ----------------------------
-- Table structure for im_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `im_warehouse`;
CREATE TABLE `im_warehouse` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '仓库名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_warehouse
-- ----------------------------
INSERT INTO `im_warehouse` VALUES ('00b51b49-d7ad-48c2-8b52-a63fc6905fef', '仓库3', '2016-06-14 08:42:52');
INSERT INTO `im_warehouse` VALUES ('22fdf0e3-3720-4971-9a46-b702aaa52817', '仓库1', '2016-05-26 10:42:59');
INSERT INTO `im_warehouse` VALUES ('49ff7b96-79a5-4d9a-b00e-75ff100973b2', 'erta', '2016-06-26 16:09:08');
INSERT INTO `im_warehouse` VALUES ('e60db6b7-8237-4d9f-9e66-5bbaec555b0f', '仓库2', '2016-05-26 10:44:53');
