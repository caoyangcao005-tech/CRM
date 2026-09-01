CREATE DATABASE IF NOT EXISTS crm
    DEFAULT CHARACTER SET utf8
    COLLATE utf8_general_ci;

USE crm;

CREATE TABLE IF NOT EXISTS sys_user (
    user_id INT NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    user_password VARCHAR(100) NOT NULL DEFAULT '123456',
    user_role_id INT NOT NULL DEFAULT 3,
    user_flag INT NOT NULL DEFAULT 1,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS bas_dict (
    dict_id INT NOT NULL AUTO_INCREMENT,
    dict_type VARCHAR(50) NOT NULL,
    dict_item VARCHAR(50) NOT NULL,
    dict_value VARCHAR(100) NULL,
    dict_is_editable CHAR(1) NOT NULL DEFAULT '1',
    PRIMARY KEY (dict_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cst_customer (
    cust_no VARCHAR(17) NOT NULL,
    cust_name VARCHAR(100) NOT NULL,
    cust_region VARCHAR(50) NULL,
    cust_manager_id INT NULL,
    cust_manager_name VARCHAR(50) NULL,
    cust_level INT NULL,
    cust_satisfy INT NULL,
    cust_credit INT NULL,
    cust_addr VARCHAR(300) NULL,
    cust_zip VARCHAR(10) NULL,
    cust_tel VARCHAR(50) NULL,
    cust_fax VARCHAR(50) NULL,
    cust_website VARCHAR(100) NULL,
    cust_licence_no VARCHAR(50) NULL,
    cust_chieftain VARCHAR(50) NULL,
    cust_bankroll INT NULL,
    cust_turnover INT NULL,
    cust_bank VARCHAR(100) NULL,
    cust_bank_account VARCHAR(50) NULL,
    cust_local_tax_no VARCHAR(50) NULL,
    cust_national_tax_no VARCHAR(50) NULL,
    cust_status VARCHAR(10) NULL DEFAULT '1',
    PRIMARY KEY (cust_no),
    UNIQUE KEY uk_customer_name (cust_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS cst_service (
    svr_id INT NOT NULL AUTO_INCREMENT,
    svr_type VARCHAR(20) NOT NULL,
    svr_title VARCHAR(500) NOT NULL,
    svr_cust_no VARCHAR(17) NULL,
    svr_cust_name VARCHAR(100) NOT NULL,
    svr_status VARCHAR(10) NOT NULL,
    svr_request VARCHAR(3000) NOT NULL,
    svr_create_id INT NOT NULL,
    svr_create_by VARCHAR(50) NOT NULL,
    svr_create_date DATETIME NOT NULL,
    svr_due_id INT NULL,
    svr_due_to VARCHAR(50) NULL,
    svr_due_date DATETIME NULL,
    svr_deal VARCHAR(3000) NULL,
    svr_deal_id INT NULL,
    svr_deal_by VARCHAR(50) NULL,
    svr_deal_date DATETIME NULL,
    svr_result VARCHAR(500) NULL,
    svr_satisfy INT NULL,
    PRIMARY KEY (svr_id),
    KEY idx_service_status (svr_status),
    KEY idx_service_customer (svr_cust_name),
    KEY idx_service_create_date (svr_create_date),
    KEY idx_service_due_user (svr_due_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE INTO sys_user
    (user_id, user_name, user_password, user_role_id, user_flag)
VALUES
    (1, '系统管理员', '123456', 1, 1),
    (2, '销售主管', '123456', 2, 1),
    (3, '客户经理小张', '123456', 3, 1),
    (4, '客户经理小李', '123456', 3, 1),
    (5, '已停用用户', '123456', 3, 2);

INSERT IGNORE INTO bas_dict
    (dict_id, dict_type, dict_item, dict_value, dict_is_editable)
VALUES
    (1, '服务类型', '咨询', '咨询', '1'),
    (2, '服务类型', '投诉', '投诉', '1'),
    (3, '服务类型', '建议', '建议', '1');

INSERT IGNORE INTO cst_customer
    (cust_no, cust_name, cust_region, cust_manager_id, cust_manager_name,
     cust_level, cust_satisfy, cust_credit, cust_addr, cust_tel, cust_status)
VALUES
    ('KH202608310000001', '北京阳光实业有限公司', '华北', 3, '客户经理小张', 3, 4, 4, '北京市海淀区', '010-88886666', '1'),
    ('KH202608310000002', '上海未来科技有限公司', '华东', 4, '客户经理小李', 4, 5, 5, '上海市浦东新区', '021-66668888', '1'),
    ('KH202608310000003', '广州新城商贸有限公司', '华南', 3, '客户经理小张', 2, 3, 3, '广州市天河区', '020-88990000', '1');

INSERT IGNORE INTO cst_service
    (svr_id, svr_type, svr_title, svr_cust_no, svr_cust_name, svr_status,
     svr_request, svr_create_id, svr_create_by, svr_create_date,
     svr_due_id, svr_due_to, svr_due_date, svr_deal, svr_deal_id,
     svr_deal_by, svr_deal_date, svr_result, svr_satisfy)
VALUES
    (1, '咨询', '询问产品升级方案', 'KH202608310000001', '北京阳光实业有限公司', '新创建',
     '客户希望了解下一版本的升级范围和时间。', 3, '客户经理小张', NOW(),
     NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
    (2, '投诉', '售后响应较慢', 'KH202608310000002', '上海未来科技有限公司', '已分配',
     '客户反映售后工单等待时间较长。', 4, '客户经理小李', DATE_SUB(NOW(), INTERVAL 1 DAY),
     3, '客户经理小张', NOW(), NULL, NULL, NULL, NULL, NULL, NULL),
    (3, '建议', '增加批量导出功能', 'KH202608310000003', '广州新城商贸有限公司', '已处理',
     '客户建议增加业务数据批量导出。', 3, '客户经理小张', DATE_SUB(NOW(), INTERVAL 2 DAY),
     4, '客户经理小李', DATE_SUB(NOW(), INTERVAL 1 DAY),
     '已记录需求并提交产品部门评估。', 4, '客户经理小李', NOW(), NULL, NULL),
    (4, '咨询', '合同续签流程咨询', 'KH202608310000001', '北京阳光实业有限公司', '已归档',
     '客户咨询年度合同续签流程。', 3, '客户经理小张', DATE_SUB(NOW(), INTERVAL 5 DAY),
     3, '客户经理小张', DATE_SUB(NOW(), INTERVAL 4 DAY),
     '已电话说明续签资料及审批流程。', 3, '客户经理小张', DATE_SUB(NOW(), INTERVAL 3 DAY),
     '客户已了解续签流程。', 5);
