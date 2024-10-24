DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`               bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `type`             int                                                                    DEFAULT '1' COMMENT '用户类型：1-普通用户',
    `username`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '用户名',
    `landline`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '座机',
    `mobile`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '手机号',
    `post_code`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '邮编',
    `base_address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '基本地址',
    `detail_address`   varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '详细地址',
    `password`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `email`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '邮箱',
    `real_name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '真实姓名',
    `nick_name`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '昵称',
    `home_page`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '主页',
    `id_card`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '身份证号',
    `logins`           int                                                                    DEFAULT '0' COMMENT '登录数',
    `last_visit_time`  datetime                                                               DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录일期',
    `privilege_ranks`  int                                                                    DEFAULT '0' COMMENT '权限级别',
    `receive_msg`      tinyint                                                                DEFAULT '1' COMMENT '是否接收短信',
    `member_apply`     tinyint                                                                DEFAULT '0' COMMENT '会员申请存在',
    `status`           tinyint                                                       NOT NULL DEFAULT '0' COMMENT '状态：0，禁用；1，启用；',
    `praise_grant`     varchar(10) COMMENT '赞助金',
    `last_update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `created`          datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `status` (`status`) USING BTREE,
    KEY `idx_addtime` (`created`) USING BTREE,
    KEY `username` (`username`(191)) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1024859055654637571
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

ALTER TABLE `sys_user`
    ADD COLUMN `receive_email` tinyint DEFAULT '1' COMMENT '是否接收邮件';
ALTER TABLE `sys_user`
    ADD COLUMN `stock_fund` varchar(10) DEFAULT '' COMMENT '股金';


DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `menu_id`          bigint                                                        DEFAULT NULL COMMENT '所属菜单Id',
    `name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '功能点名称',
    `description`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '功能描述',
    `url`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `method`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `create_by`        bigint                                                        DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint                                                        DEFAULT NULL COMMENT '修改人',
    `created`          datetime NOT NULL                                             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL                                             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_system`        tinyint                                                       DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `unq_name` (`name`(191)) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1010101010101010198
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='权限配置';
INSERT INTO `sys_privilege`(`id`, `menu_id`, `name`, `description`, `url`, `method`, `create_by`, `modify_by`,
                            `created`, `last_update_time`, `is_system`)
VALUES (1010101010101010198, 0, 'article_upload', '文章上传权限', NULL, NULL, NULL, NULL, '2021-07-02 19:53:35',
        '2021-07-02 19:55:45', NULL);

DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `role_id`      bigint NOT NULL,
    `privilege_id` bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1021574920613801987
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色权限配置';

DROP TABLE IF EXISTS `sys_role_privilege_user`;
CREATE TABLE `sys_role_privilege_user`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `role_id`      bigint NOT NULL COMMENT '角色Id',
    `user_id`      bigint NOT NULL COMMENT '用户Id',
    `privilege_id` bigint NOT NULL COMMENT '权限Id',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `pk_role_id_user_id_privilege_id` (`role_id`, `user_id`, `privilege_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1010101010101010228
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户权限配置';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`               bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `code`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代码',
    `ranks`            int(2) COMMENT '权限级别 0-10 权限等级依次递减',
    `description`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '描述',
    `create_by`        bigint                                                                DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint                                                                DEFAULT NULL COMMENT '修改人',
    `status`           tinyint                                                      NOT NULL DEFAULT '1' COMMENT '状态0:禁用 1:启用',
    `created`          datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1017767747970568195
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色';


DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT,
    `role_id`          bigint            DEFAULT NULL,
    `menu_id`          bigint            DEFAULT NULL,
    `create_by`        bigint            DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint            DEFAULT NULL COMMENT '修改人',
    `created`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1021574920731242499
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`               bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`        bigint                                                                DEFAULT NULL COMMENT '上级菜单ID',
    `parent_key`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '上级菜单唯一KEY值',
    `type`             tinyint                                                      NOT NULL DEFAULT '2' COMMENT '类型 1-分类 2-节点',
    `name`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `code`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一标识',
    `desc`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '描述',
    `device`           int(1)                                                                DEFAULT NULL COMMENT '连接设备 0 PC 和 移动 1 PC 2 移动',
    `target_url`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '目标地址',
    `sort`             int                                                                   DEFAULT NULL COMMENT '排序索引',
    `status`           tinyint                                                      NOT NULL DEFAULT '1' COMMENT '状态 0-无效； 1-有效；',
    `create_by`        bigint                                                                DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint                                                                DEFAULT NULL COMMENT '修改人',
    `created`          datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 50
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统菜单';

CREATE
    UNIQUE INDEX `uq_name` ON `sys_menu` (name);

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT,
    `role_id`          bigint            DEFAULT NULL,
    `menu_id`          bigint            DEFAULT NULL,
    `create_by`        bigint            DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint            DEFAULT NULL COMMENT '修改人',
    `created`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1021574920731242499
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单';


DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`               bigint                                                         NOT NULL AUTO_INCREMENT,
    `title`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '标题',
    `description`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '简介',
    `author`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT NULL COMMENT '作者',
    `type`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT NULL COMMENT '类型',
    `status`           int                                                                     DEFAULT NULL COMMENT '文章状态',
    `notice`           tinyint                                                                 DEFAULT NULL COMMENT '通知',
    `secret`           tinyint                                                                 DEFAULT NULL COMMENT '私密',
    `sort`             int                                                            NOT NULL COMMENT '文章排序，越大越靠前',
    `count`            int                                                            NOT NULL DEFAULT 0 COMMENT '文章阅读次数',
    `content`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
    `imgUrl`           varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '封面图片url',
    `url`              varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '点击跳转url',
    `last_update_time` datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    `created`          datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建일期',
    `is_new`           tinyint                                                                 DEFAULT 1 COMMENT '是否为新添加',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1021989613635514371
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='文章类信息';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`          bigint            DEFAULT NULL COMMENT '角色ID',
    `user_id`          bigint            DEFAULT NULL COMMENT '用户ID',
    `create_by`        bigint            DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint            DEFAULT NULL COMMENT '修改人',
    `created`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色';


DROP TABLE IF EXISTS `sys_pop`;
CREATE TABLE `sys_pop`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `refresh_time`     int(2)                DEFAULT NULL COMMENT '记录保存时间',
    `start_time`       datetime     NOT NULL COMMENT '弹窗开始时间',
    `end_time`         datetime     NOT NULL COMMENT '弹窗结束时间',
    `status`           tinyint      NOT NULL DEFAULT 1 COMMENT '是否展示',
    `left`             int          NOT NULL COMMENT '弹窗左侧位置',
    `top`              int          NOT NULL COMMENT '弹窗顶部位置',
    `width`            int          NOT NULL COMMENT '弹窗宽度',
    `height`           int          NOT NULL COMMENT '弹窗高度',
    `title`            varchar(200) NOT NULL COMMENT '弹窗标题',
    `content`          text         NOT NULL COMMENT '弹窗内容',
    `create_by`        bigint                DEFAULT NULL COMMENT '创建人',
    `modify_by`        bigint                DEFAULT NULL COMMENT '修改人',
    `created`          datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_start_time` (`start_time`) USING BTREE,
    KEY `idx_ent_time` (`end_time`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统弹窗';


DROP TABLE IF EXISTS `sys_pop_ip`;
CREATE TABLE `sys_pop_ip`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip`         varchar(20) NOT NULL COMMENT '访问者ip地址',
    `sys_pop_id` bigint      NOT NULL COMMENT '弹窗id',
    `status`     tinyint     NOT NULL COMMENT '当前时间是否展示弹窗',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `pk_ip_pop_id` (`ip`, `sys_pop_id`) USING BTREE

) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统弹窗';


DROP TABLE IF EXISTS `member_apply`;
CREATE TABLE `member_apply`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`          bigint COMMENT '用户id',
    `register_no`      varchar(255) COMMENT '登记号码',
    `company_name`     varchar(255) COMMENT '公司名称',
    `landline`         varchar(255) COMMENT '座机',
    `fax`              varchar(255) COMMENT '传真',
    `mobile`           varchar(255) COMMENT '手机号码',
    `email`            varchar(255) COMMENT '邮箱地址',
    `address`          varchar(255) COMMENT '位置',
    `register_type`    varchar(255) COMMENT '注册方式',
    `holder_name`      varchar(255) COMMENT '账户持有人',
    `holder_birth`     varchar(255) COMMENT '账户持有人出生일期',
    `bank_name`        varchar(255) COMMENT '银行卡名称',
    `account`          varchar(255) COMMENT '账号',
    `month_pay`        int(10) COMMENT '每월支付',
    `pay_type`         tinyint COMMENT '1 每월自动扣款 2 直接存款',
    `pay_date`         int COMMENT '付款일期',
    `pay_money`        decimal COMMENT '缴款金额',
    `service`          tinyint COMMENT '指定业务 0 选中 1 不选中',
    `check_business`   tinyint COMMENT '其他业务 0 选中 1 不选中',
    `business`         varchar(255) COMMENT '其他业务',
    `info_collect`     tinyint COMMENT '其他业务 0 不同意 1 同意',
    `third_provide`    tinyint COMMENT '向第三方提供个人信息 0 不同意 1 同意',
    `created`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT =' 赞助商注册申请 ';
ALTER TABLE `member_apply`
    ADD COLUMN `detail_address` varchar(255) DEFAULT '' COMMENT '详细地址';
ALTER TABLE `member_apply`
    ADD COLUMN `user_name` varchar(255) DEFAULT '' COMMENT '用户名';
ALTER TABLE `member_apply`
    ADD COLUMN `copartner` tinyint(1) DEFAULT 0 COMMENT '';
ALTER TABLE `member_apply`
    ADD COLUMN `post_code` varchar(255) DEFAULT '' COMMENT '邮编';

DROP TABLE IF EXISTS `sys_user_statis`;
CREATE TABLE `sys_user_statis`
(
    `id`               bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `browser`          varchar(50) NOT NULL COMMENT '访客浏览器',
    `version`          varchar(50) COMMENT '浏览器版本',
    `engine`           varchar(50) COMMENT '浏览器内核',
    `engine_version`   varchar(50) COMMENT '内核版本',
    `os`               varchar(50) COMMENT '访客操作系统',
    `remote_host`      varchar(50) COMMENT '访客ip',
    `url`              varchar(255) COMMENT '访问路径',
    `plat_form`        varchar(50) COMMENT '访客操作系统',
    `device`           varchar(50) COMMENT '访客设备',
    `user_id`          bigint(20) COMMENT '访客会员id 0未未登录',
    `created`          datetime COMMENT '初次访问时间',
    `last_update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近访问时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_user_id` (`user_id`) USING BTREE,
    KEY `idx_url` (`url`) USING BTREE,
    KEY `idx_remote_host` (`remote_host`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='访问记录';


DROP TABLE IF EXISTS `competition_schedule`;
CREATE TABLE `competition_schedule`
(
    `id`                 bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `date_time`          datetime COMMENT '比赛时间',
    `place`              varchar(50) COMMENT '比赛地点',
    `season`             varchar(20) COMMENT '赛季',
    `season_img_url`     varchar(1000) COMMENT '赛季logo',
    `home_team`          varchar(50) COMMENT '主场',
    `visit_team`         varchar(50) COMMENT '客场',
    `home_team_img_url`  varchar(50) COMMENT '主场logo',
    `visit_team_img_url` varchar(50) COMMENT '客场logo',
    `home_scope`         int(3) COMMENT '主队得分',
    `visit_scope`        int(3) COMMENT '客队得分',
    `created`            datetime COMMENT '初次访问时间',
    `last_update_time`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近访问时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='比赛计划';


DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`               bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `article_id`       bigint   NOT NULL COMMENT '文章id',
    `parent_id`        bigint   NOT NULL COMMENT '上级评论id',
    `priv`             tinyint  NOT NULL DEFAULT 0 COMMENT '是否仅自己可见',
    `content`          text     NOT NULL COMMENT '回复内容',
    `create_by`        bigint            DEFAULT NULL COMMENT '创建人',
    `created`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='评论表';



DROP TABLE IF EXISTS `ranking`;
CREATE TABLE `ranking`
(
    `id`                        bigint   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ranks`                     int COMMENT '排名',
    `start_time`                datetime COMMENT '赛季开始时间',
    `end_time`                  datetime COMMENT '赛季结束时间',
    `club_logo_img_url`         varchar(255) COMMENT '俱乐部标志',
    `club_name`                 varchar(50) COMMENT '俱乐部名称',
    `season`                    varchar(50) COMMENT '赛季',
    `current_competition_times` int COMMENT '当前比赛场次',
    `points`                    int COMMENT '胜利点数',
    `win`                       int COMMENT '胜利场次',
    `draw`                      int COMMENT '打平场次',
    `fail`                      int COMMENT '失败场次',
    `goal_win`                  int COMMENT '净胜球1',
    `goal_draw`                 int COMMENT '净胜球2',
    `goal_fail`                 int COMMENT '净胜球3',
    `total_competition_times`   int COMMENT '总比赛场次',
    `created`                   datetime COMMENT '初次访问时间',
    `last_update_time`          datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近访问时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1022060671264763907
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='联赛排名';


ALTER TABLE `article`
    ADD COLUMN `menu_id` bigint(20)  COMMENT '所属菜单id';


ALTER TABLE `sys_menu`
    ADD COLUMN `article_type` int(1)  COMMENT '用于关联文章';

ALTER TABLE `article` ADD COLUMN `copy_id` bigint(25) COMMENT  '文章拷贝来源';

ALTER TABLE `article` ADD COLUMN `author_user_id` bigint(25) COMMENT  '创建者用户id';

ALTER TABLE `article` ADD COLUMN `author_ip` varchar(30) COMMENT  '创建者ip地址';


