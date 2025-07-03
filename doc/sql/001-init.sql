use
    orca_db;

-- 系统的机器人库
create table if not exists t_sys_robot
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    nickname    varchar(200) not null comment '机器人昵称',
    head_ico    varchar(200) not null comment '机器人头像',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '系统内置数字人';

insert into t_sys_robot (nickname, head_ico, deleted, create_time, update_time)
values
    ('令和れい', 'https://github.com/gfriends/gfriends/blob/master/Content/1-Diaz/%E4%BB%A4%E5%92%8C%E3%82%8C%E3%81%84.jpg',
     0, now(), now()),
    ('前田えま', 'https://github.com/gfriends/gfriends/blob/master/Content/1-Diaz/%E5%89%8D%E7%94%B0%E3%81%88%E3%81%BE.jpg',
     0, now(), now()),
    ('卯水咲流', 'https://github.com/gfriends/gfriends/blob/master/Content/1-Diaz/%E5%8D%AF%E6%B0%B4%E5%92%B2%E6%B5%81.jpg', 0,
     now(), now()),
    ('涼宮琴音',
     'https://github.com/gfriends/gfriends/blob/master/Content/1-Diaz/%E6%B6%BC%E5%AE%AE%E7%90%B4%E9%9F%B3.jpg', 0, now(),
     now()),
    ('杏奈',
     'https://github.com/gfriends/gfriends/blob/master/Content/1-Diaz/%E6%9D%8F%E5%A5%88.jpg', 0, now(),
     now()),
    ('七海ティナ', 'https://github.com/gfriends/gfriends/blob/master/Content/1-FALENO/%E4%B8%83%E6%B5%B7%E3%83%86%E3%82%A3%E3%83%8A.jpg', 0,
     now(), now()),
    ('AIKA', 'https://github.com/gfriends/gfriends/blob/master/Content/4-Kawaii/AIKA.jpg',
     0, now(), now()),
    ('三上悠亜',
     'https://github.com/gfriends/gfriends/blob/master/Content/2-Juicy-Honey/%E4%B8%89%E4%B8%8A%E6%82%A0%E4%BA%9C.jpg', 0, now(),
     now());


-- 系统的免费礼物
create table if not exists t_sys_gift
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    name        varchar(200) not null comment '礼物名称',
    img         varchar(200) not null comment '礼物图片',
    price       bigint       not null comment '礼物价格,分',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '系统内置礼物库';


-- 初始化内置礼物
insert into t_sys_gift(name, img, price, deleted, create_time, update_time)
values ('比心', '图片地址1', 0, 0, now(), now()),
       ('点赞', '图片地址2', 0, 0, now(), now()),
       ('鼓掌', '图片地址3', 0, 0, now(), now()),
       ('玫瑰', '图片地址4', 0, 0, now(), now());

-- 组织表 [非自增ID]

create table if not exists t_organization
(
    id            bigint primary key comment 'ID',
    name          varchar(200) not null comment '企业(机构)名称',
    org_code      varchar(200) not null comment '企业唯一标识',
    org_logo      varchar(150) comment 'LOGO',
    org_room_bg   varchar(150) comment '房间背景设置',
    notice        varchar(1000) comment '直播间公告',
    living_price  bigint       not null default 1 comment '直播单价',
    video_price   bigint       not null default 1 comment '点播单价',
    storage_price bigint       not null default 1 comment '存储单价',
    deleted       int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '组织表';


-- 组织管理者
create table if not exists t_org_admin
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    account     varchar(200) not null comment '机构账号',
    password    varchar(200) not null comment '密码',
    status      int          not null default 1 comment '状态 0 锁定 1 正常',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '管理者';

-- 组织的数字人
create table if not exists t_org_robot
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    code        varchar(100) not null comment '机器人编号',
    nickname    varchar(200) not null comment '机器人昵称',
    head_ico    varchar(200) not null comment '机器人头像',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '数字人';


-- 组织 礼物库
create table if not exists t_org_gift_lib
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    name        varchar(200) not null comment '礼物名称',
    img         varchar(200) not null comment '礼物图片',
    price       bigint       not null comment '礼物价格,分',
    gift_type   int          not null default 1 comment '礼物类型 1 静态礼物 2 动态礼物',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '礼物库';


-- 组织 商品库
create table if not exists t_org_goods_lib
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    name           varchar(200) not null comment '商品名称',
    img            varchar(200) not null comment '商品图片',
    good_type      int          not null default 1 comment '商品类型 1 虚拟商品 2 实物商品',
    original_price bigint comment '原价(分)',
    price          bigint       not null comment '现价(分)',
    pay_type       int          not null comment '支付类型 1 跳转支付 2 在线支付 3 小程序支付 4 二维码支付',
    mini_page      varchar(200) comment '小程序支付跳转链接',
    jump_page      varchar(200) comment '跳转链接支付',
    qrcode         varchar(200) comment '二维码支付',
    bounds         int          not null default 0 comment '限单 0 否 1 是',

    deleted        int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '商品库';


-- 组织 敏感词
create table if not exists t_org_sensitive_word
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint   not null comment '机构ID',
    words       text     not null comment '敏感词',
    deleted     int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime not null comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '敏感词';

-- 组织 免审核词
create table if not exists t_org_exempt_word
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    name        varchar(100) not null comment '免审词名称',
    words       longtext     not null comment '免审词列表',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '免审词';


-- 直播房间 [非自增ID]
create table if not exists t_living_room
(
    id            bigint primary key comment 'ID',
    org_id        bigint       not null comment '机构ID',
    name          varchar(150) not null comment '直播房间名称',
    anchor_pwd    varchar(100) not null comment '主播密码',
    assistant_pwd varchar(100) not null comment '助理密码',
    share_pwd     varchar(100) not null comment '用户端密码',
    living_time   datetime comment '直播时间',

    cover         varchar(150) comment '直播封面',
    logo         varchar(255) comment '直播LOGO',
    remark        text comment '直播介绍',


    living_type   int          not null default 1 comment '直播类型: 1 标准延迟(标准直播), 2 超低延迟(快直播)',
    video_quality int          not null default 3 comment '清晰度: 1 流畅270p 2 标清480p 3 准高清720p 4 高清1080p 5 超高清2K',
    video_type    int          not null default 1 comment '直播类型: 1 真人直播, 2 视频直播',

    schedule_time time         not null comment '伪直播调度时间',
    media_biz_id  bigint comment '伪直播的关联媒体ID',
    cycle_times   int          not null default 1 comment '媒体直播循环次数',
    status        int          not null default 0 comment '直播状态  0  直播中  1  未直播',

    deleted       int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '直播房间';


-- 直播房间页面配置
create table if not exists t_living_room_page
(
    id            bigint AUTO_INCREMENT primary key comment 'ID',
    org_id        bigint       not null comment '机构ID',
    room_id       bigint       not null comment '直播房间ID',

    show_draw     int          not null comment '白板开关 0 关闭 1 开启',
    show_ppt      int          not null comment '课件是否展示 0 关闭 1 开启',
    pc_switch     int          not null comment '电脑端观看开关 0 关闭 1 开启',
    mobile_layout varchar(100) not null comment '移动端模板 1:全屏 2:二分屏 3:三分屏',
    pad_pc_layout varchar(100) not null comment 'PAD(PC)端模板',

    bg_image      varchar(150) not null comment '直播封面背景图',
    watermark     varchar(150) not null comment '视频水印内容',

    deleted       int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '直播房间页面配置';

-- 直播房间 权限配置
create table if not exists t_living_room_permission
(
    id              bigint AUTO_INCREMENT primary key comment 'ID',
    org_id          bigint   not null comment '机构ID',
    room_id         bigint   not null comment '直播房间ID',

    permission_type int      not null comment '观看权限: 1 无密码 2 密码 3 付费 ',
    permission_json longtext comment '权限JSON',

    deleted         int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time     datetime not null comment '创建时间',
    update_time     datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '直播房间权限配置';

-- 直播房间 互动设置
create table if not exists t_living_room_interact
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint   not null comment '机构ID',
    room_id        bigint   not null comment '直播房间ID',

    login_tips     int      not null default 1 comment '观众登陆提示 0 关闭 1 开启',
    all_mute       int      not null default 1 comment '全员禁言开关 0 关闭 1 开启',
    msg_approve    int      not null default 0 comment '消息审核开关 0 关闭 1 开启',
    exempt_enable  int      not null default 0 comment '免审词开关 0 关闭 1 开启',
    exempt_word_id bigint comment '免审词映射ID',
    tuple_enable   int      not null default 1 comment '人数倍数开关 0 关闭 1 开启',
    tuple_num      int      not null default 1 comment '人数倍数',

    deleted        int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime not null comment '创建时间',
    update_time    datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '直播房间互动设置';


-- 直播房间商品设置
create table if not exists t_living_room_market_goods
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    room_id     bigint       not null comment '直播房间ID',

    btn_txt     varchar(100) not null comment '购买按钮描述',
    count_down  int          not null default 0 comment '倒计时(秒): 0 不设置, 60 1分钟, 120 2分钟, 300 5分钟, 600 10分钟',
    show_style  int          not null default 1 comment '展示样式: 1 小弹框 2 大弹框',

    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '直播房间商品设置';

-- 直播房间商品条目
create table if not exists t_living_room_market_goods_item
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    room_id        bigint       not null comment '直播房间ID',

    goods_lib_id   bigint       not null comment '商品库ID',
    name           varchar(200) not null comment '商品名称',
    img            varchar(200) not null comment '商品图片',
    good_type      int          not null default 1 comment '商品类型 1 虚拟商品 2 实物商品',
    original_price bigint comment '原价(分)',
    price          bigint       not null comment '现价(分)',
    pay_type       int          not null comment '支付类型 1 跳转支付 2 在线支付 3 小程序支付 4 二维码支付',
    mini_page      varchar(200) comment '小程序支付跳转链接',
    jump_page      varchar(200) comment '跳转链接支付',
    qrcode         varchar(200) comment '二维码支付',

    sell_status    int          not null comment '销售状态 1 上架 2 下架 3 售罄',
    priority       int          not null default 0 comment '优先级',
    bounds         int          not null default 0 comment '限单 0 否 1 是',

    deleted        int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '直播房间商品条目';


-- 直播房间礼物条目
create table if not exists t_living_room_market_gift_item
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    room_id     bigint       not null comment '直播房间ID',

    gift_lib_id bigint       not null comment '礼品库ID',
    name        varchar(200) not null comment '商品名称',
    img         varchar(200) not null comment '商品图片',
    price       bigint       not null comment '现价(分)',
    gift_type   int          not null default 1 comment '礼物类型 1 静态礼物 2 动态礼物',
    status      int          not null comment '状态 1 上架 2 下架',
    priority    int          not null default 0 comment '优先级',

    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '直播房间礼物条目';


-- 直播房间课件信息
create table if not exists t_living_room_courseware
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint       not null comment '机构ID',
    room_id     bigint       not null comment '直播房间ID',

    name        varchar(100) not null comment '课件名称',
    status      int          not null comment '状态 1 转换中 2 转换成功 3 转换失败',
    deleted     int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '直播房间课件信息';

-- 直播房间课件信息
create table if not exists t_living_room_courseware_item
(
    id            bigint AUTO_INCREMENT primary key comment 'ID',
    org_id        bigint       not null comment '机构ID',
    room_id       bigint       not null comment '直播房间ID',

    courseware_id bigint       not null comment '课件ID',
    image         varchar(200) not null comment '课件条目图片',


    deleted       int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '直播房间课件信息';


-- 直播房间直播记录ID[非自增ID]
create table if not exists t_living_room_live_record
(
    id          bigint primary key comment 'ID',
    org_id      bigint   not null comment '机构ID',
    room_id     bigint   not null comment '直播房间ID',
    start_date  date     not null comment '开始直播日期',
    begin_time  datetime not null comment '开始直播时间',
    stop_date   date comment '结束直播日期',
    end_time    datetime comment '结束直播时间',
    cost        bigint comment '总共直播时长',
    status      int      not null comment '状态 0 直播中 1 直播结束',
    need_record int      not null default 0 comment '需要录制: 0 不录制 1 需要录制',
    live_source int      not null comment '直播来源 1 dashboard触发 2 视频、镜像直播触发 4 主播端触发',
    deleted     int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime not null comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '直播房间直播记录ID';


-- 媒体库表
create table if not exists t_living_media_lib
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    name           varchar(100) not null comment '媒体名称',
    file_name      varchar(100) not null comment '文件原名称',
    bucket         varchar(100) not null comment 'bucket名称',
    path_name      varchar(255) not null comment 'bucket内名称',
    media_type     int          not null comment '类型: 1 手动上传 2 从录制而来',
    room_id        bigint comment '房间ID',
    room_record_id bigint comment '记录ID',
    duration       bigint comment '时长',
    capacity       bigint comment '视频大小',
    image          varchar(255) comment '截取图',
    convert_status int default 0 comment '转码状态 0 进行中 1 成功 2 失败',
    deleted        int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '媒体库表';


create table if not exists t_living_trigger_oplog
(
    id            bigint AUTO_INCREMENT primary key comment 'ID',
    org_id        bigint   not null comment '机构ID',
    room_id       bigint   not null comment '直播房间ID',
    `day`         date     not null comment '日期',
    schedule_time time     not null comment '伪直播调度时间',
    biz_id        bigint   not null comment '触发业务ID: 媒体库ID || 直播记录ID',
    biz_type      int      not null comment '业务类型 1 媒体库 2 直播记录',
    push_task_id  bigint comment '推送任务ID',
    status        int comment '状态 0 等待启动 1 运行中 2 已停止,正常结束(自动｜主动) 3 已停止且失败',
    duration      bigint comment '推流任务执行时长',
    msg           varchar(255) comment '错误信息',
    deleted       int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime not null comment '创建时间',
    update_time   datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '智能直播操作记录表';


-- 短链接表
create table if not exists t_living_room_short_url
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    org_id      bigint   not null comment '机构ID',
    room_id     bigint   not null comment '直播房间ID',
    channel_id  bigint comment '渠道ID',
    random_str  varchar(255) comment '短链接随机字符串',
    deleted     int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime not null comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '短链接表';


-- 企业账户和流水
create table if not exists t_org_account
(
    id          bigint primary key comment 'ID',
    org_id      bigint   not null comment '机构ID',
    balance     bigint   not null comment '账户余额',
    status      int      not null comment '账户状态 1 正常 2 冻结',
    deleted     int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time datetime not null comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '企业账户';

create table if not exists t_org_account_record
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint        not null comment '机构ID',
    account_id     bigint        not null comment '账户ID',
    event_time    datetime      not null comment '创建时间',
    record_no      varchar(100)  not null comment '流水记录NO',
    biz_no         varchar(100)  not null comment '业务编号',
    before_balance bigint        not null comment '变更前余额',
    after_balance  bigint        not null comment '变更后余额',
    amount         bigint        not null comment '变更金额',
    record_type    int           not null comment '记录类型 1 充值 2 扣费',
    remark         varchar(1024) not null comment '记录备注',
    record_name    varchar(255)  not null comment '记录名称',
    deleted        int           not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime      not null comment '创建时间',
    update_time    datetime      not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '企业账户流水记录';


create table if not exists t_org_account_order
(
    id            bigint AUTO_INCREMENT primary key comment 'ID',
    org_id        bigint       not null comment '机构ID',
    account_id    bigint       not null comment '账户ID',
    amount        bigint       not null comment '充值金额',
    order_no      varchar(100) not null comment '工单流水号',
    status        int          not null comment '订单状态 1 初始化(未支付) 2 充值失败(支付超时) 3 成功充值(支付成功) 4 订单关闭',
    bank_order_no varchar(100) comment '银行流水号',
    remark        varchar(255) comment '备注',
    deleted       int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time   datetime     not null comment '创建时间',
    update_time   datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '企业账户变更申请工单';


-- 氛围场控表
create table if not exists t_control_atmosphere_field
(
    id            bigint AUTO_INCREMENT primary key comment 'ID',
    org_id        bigint        not null comment '机构ID',
    room_id       bigint        not null comment '房间ID',
    text_content  varchar(1024) not null comment '文本内容',
    gift_content  varchar(1024) not null comment '礼物内容',
    quantity      int           not null comment '发送数量',
    text_interval decimal(5, 1) null comment '文本消息发送间隔, 单位: 秒',
    gift_interval decimal(5, 1) null comment '礼物消息发送间隔, 单位: 秒',
    exec_status   int           not null comment '执行状态, 0: 已停止, 1: 已启动',
    exp_end_time  datetime      null comment '预计结束时间',

    deleted       int           not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time   datetime      not null comment '创建时间',
    update_time   datetime      not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '氛围场控';

-- 场控消息表
create table if not exists t_control_field_message
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    room_id        bigint       not null comment '房间ID',
    robot_id       bigint       not null comment '机器人ID',
    robot_nickname varchar(200) not null comment '机器人昵称',
    robot_head_ico varchar(200) not null comment '机器人头像',
    control_type   int          not null comment '场控类别, 1: 氛围场控',
    control_id     bigint       null comment '场控ID',
    message_type   int          not null comment '消息类别, 1: 文本, 2: 礼物',
    message        varchar(255) not null comment '消息内容',
    trigger_time   datetime     not null comment '消息触发时间',
    status         int          not null comment '发送状态, 0: 未发送, 1: 已发送',

    deleted        int          not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '场控消息';


create table `t_atmosphere_template_control`
(
    `id`         bigint        NOT NULL COMMENT '订单号',
    `org_id`     bigint        NOT NULL COMMENT '机构id',
    `room_id`    bigint        NOT NULL COMMENT '直播间ID',
    text_content varchar(1024) not null comment '文本内容',
    gift_content varchar(1024) not null comment '礼物内容',
    deleted      int           not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time  datetime      not null comment '创建时间',
    update_time  datetime      not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '氛围模板';

-- 剧本表
create table if not exists t_control_script
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    room_id        bigint       not null comment '房间ID',
    robot_id       bigint       not null comment '机器人ID',
    robot_code     varchar(100) not null comment '机器人编号',
    robot_nickname varchar(200) not null comment '机器人昵称',
    robot_head_ico varchar(200) not null comment '机器人头像',
    message_type   int          null comment '消息类别, 1: 文本, 2: 礼物',
    content        varchar(256) null comment '发送内容',

    deleted        int          not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '场控剧本';


create table if not exists t_file_info
(
    id          varchar(255) primary key comment 'ID',
    file_name   varchar(100) not null comment '文件原名称',
    real_name   varchar(100) not null comment '文件真实名称',
    bucket      varchar(100) not null comment 'bucket名称',
    path_name   varchar(255) not null comment 'bucket内名称',
    file_size   bigint       not null comment '文件大小',
    file_part   int          not null comment '分片大小',
    file_md5    varchar(50) comment '文件MD5值',
    file_crc32  bigint(20) comment ' 文件crc32值',
    status      int          not null comment '状态 0 初始化 1 完成 2 终止 ',
    deleted     int          not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '文件表';


create table if not exists t_file_part_info
(
    id          bigint AUTO_INCREMENT primary key comment 'ID',
    file_id     varchar(255) not null comment '分片上传标识',
    file_name   varchar(100) not null comment '文件原名称',
    bucket      varchar(100) not null comment 'bucket名称',
    path_name   varchar(255) not null comment 'bucket内名称',
    part_num    int          not null comment '分片索引',
    part_size   bigint       not null comment '分片大小',
    part_crc32  bigint comment '分片CRC32',
    etag        varchar(255) not null comment '分片ETAG编号',
    status      int          not null comment '状态 1 正常 2 终止',
    deleted     int          not null default 0 comment '是否删除, 0: 未删除, 1: 已删除',
    create_time datetime     not null comment '创建时间',
    update_time datetime     not null default current_timestamp on update current_timestamp comment '更新时间'
) comment '文件分片信息';



create table if not exists t_share_user_view_record
(
    id             bigint AUTO_INCREMENT primary key comment 'ID',
    org_id         bigint       not null comment '机构ID',
    view_date      date         not null comment '日期',
    room_id        bigint       not null comment '房间ID',
    channel_id     bigint       not null comment '渠道ID',
    uid            varchar(100) not null comment '用户ID',
    room_record_id bigint comment '房间记录ID',
    nickname       varchar(100) comment '昵称',
    head_ico       varchar(255) comment '头像',
    view_duration  bigint                default 0 comment '观看时长(秒)',
    msg_num        bigint                default 0 comment '发言数',
    gift_num       bigint                default 0 comment '送礼物数',
    user_agent     TEXT comment 'UA',
    ts_online      bigint comment '上线时间戳',
    ts_offline     bigint comment '离线时间戳',
    online_time    datetime comment '上线时间',
    offline_time   datetime comment '下线时间',
    event_ts       bigint comment '事件时间戳',
    event_time     datetime comment '事件时间',
    status         int comment '状态 1 在线 2 离线',
    online_times   int                   default 1 comment '上线次数',
    deleted        int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time    datetime     not null comment '创建时间',
    update_time    datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    unique key (view_date, room_id, channel_id, uid)
) comment '用户在线观看历史表';


create table if not exists t_room_statics_record
(
    id                bigint AUTO_INCREMENT primary key comment 'ID',
    org_id            bigint   not null comment '机构ID',
    stat_time         datetime not null comment '统计日期',
    room_id           bigint   not null comment '房间ID',
    room_record_id    bigint   not null comment '房间记录ID',
    view_page_num     bigint comment '观看PV',
    online_num        bigint comment '在线人数',
    offline_num       bigint comment '离线人数',
    sender_num        bigint comment '发言人数',
    order_num         bigint comment '订单数',
    gift_num          bigint comment '礼物数量',
    msg_num           bigint comment '消息数量',
    wait_order_num    bigint comment '等待支付订单数',
    payed_order_num   bigint comment '已支付的订单数',
    cancel_order_num  bigint comment '取消订单数',
    timeout_order_num bigint comment '超时未订单数',

    deleted           int      not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time       datetime not null comment '创建时间',
    update_time       datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    unique key (stat_time, room_id, room_record_id)
) comment '房间记录数据统计';


create table if not exists t_sys_tlpay
(
    id              bigint AUTO_INCREMENT primary key comment 'ID',
    cus_id          varchar(100) not null comment '通联支付商户号',
    app_id          varchar(100) not null comment '通联支付appid',
    wx_app_id       varchar(100) not null comment '微信支付的appid',
    api_base_url    varchar(255) not null comment '通联支付基础url',
    sign_type       varchar(100) not null comment '通联支付签名方式 RSA、MD5、SM2',
    version         varchar(32)  not null comment '版本',
    mch_rsa_pri_key text comment '通联支付商户私钥 请求 加签',
    tl_rsa_pub_key  text comment '通联支付平台公钥 结果 验签',
    notify          varchar(255) comment '异步通知地址',
    valid           int                   default 30 comment '超时时间 默认 30',
    deleted         int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '通联信息配置表';



insert into t_sys_tlpay
(cus_id, app_id, wx_app_id, sign_type, version, valid,
 notify,
 deleted, create_time, update_time,
 api_base_url,
 tl_rsa_pub_key,
 mch_rsa_pri_key)
values ('通联客户ID', '通联APPID', '微信APPID', 'RSA', '11', 30,
        '通联回调地址',
        0, now(), now(),
        'https://vsp.allinpay.com/apiweb/unitorder',
        '通联RSA加密公钥',
        '通联RSA加密私钥');



CREATE TABLE `t_channel_info`
(
    `id`                       bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `org_id`                   bigint       NOT NULL COMMENT '机构ID',
    `channel_name`             varchar(255) NOT NULL COMMENT '渠道名称',
    `channel_id`               int          NOT NULL COMMENT '渠道ID 目前是6位数字',
    `channel_owner`            varchar(6)   NOT NULL COMMENT '渠道负责人',
    `contact_phone`            varchar(11)  NOT NULL COMMENT '联系电话',
    `channel_description`      varchar(100) COMMENT '渠道说明',
    `commission_ratio`         int          NOT NULL COMMENT '分佣比例 百分数1-100',
    `prepare_commission_ratio` int COMMENT '准备的分佣比例 百分数1-100（下一天生效）',
    `commission_method`        int COMMENT '分佣方式 1 自动分佣 2 手动分佣',
    `commission_period`        int COMMENT '分佣周期 1 每天结算 2 T+7结算 3 T+15结算 4 T+30结算',
    `payment_fee`              int          NOT NULL DEFAULT '0' COMMENT '支付手续费 百分数1-100',
    `bank_account_type`        int COMMENT '打款银行账户 1 对公打款 2 对私打款',
    `receiving_unit`           varchar(100) COMMENT '收款单位',
    `bank_account_number`      varchar(50) COMMENT '银行账户',
    `bank_branch`              varchar(100) COMMENT '开户行',
    `settlement_currency`      int          NOT NULL default '1' COMMENT '结算币种 1 人民币（CNY）2 美元($)',
    `create_user`              varchar(200) NOT NULL COMMENT '创建人账号',
    `update_user`              varchar(200) COMMENT '修改人账号',
    `sys_inner`                int          NOT NULL DEFAULT '0' COMMENT '是否默认: 0 不是 1 是',
    `deleted`                  int          NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`              datetime     NOT NULL COMMENT '创建时间',
    `update_time`              datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_channel_id` (`channel_id`)
) ENGINE = InnoDB COMMENT ='渠道信息';

CREATE TABLE `t_day_channel_data`
(
    `id`                 bigint   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `org_id`             bigint   NOT NULL COMMENT '机构id',
    `channel_id`         int      NOT NULL COMMENT '渠道ID 渠道表的channel_id字段',
    `channel_name`       varchar(255) COMMENT '渠道名称',
    `flow_people`        int      NOT NULL DEFAULT '0' COMMENT '渠道日引流人数',
    `transaction_amount` bigint   NOT NULL DEFAULT '0' COMMENT '渠道引流日交易金额(分)',
    `commission_ratio`   int      NOT NULL DEFAULT '0' COMMENT '当日分佣比例 百分数1-100',
    `sub_commission`     bigint   NOT NULL DEFAULT '0' COMMENT '渠道日分佣金额(分)',
    `day`                date     NOT NULL COMMENT '日期',
    `deleted`            int      NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`        datetime NOT NULL COMMENT '创建时间',
    `update_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    unique key uk_channel_data (channel_id, `day`, deleted)
) ENGINE = InnoDB COMMENT ='渠道日数据信息';


CREATE TABLE `t_day_living_room_channel_data`
(
    `id`                 bigint   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `room_id`            bigint   NOT NULL COMMENT '直播间ID',
    `live_record_id`     bigint COMMENT '直播记录id',
    `channel_id`         int      NOT NULL COMMENT '渠道ID 渠道表的channel_id字段',
    `channel_name`       varchar(255) COMMENT '渠道名称',
    `flow_people`        int      NOT NULL DEFAULT '0' COMMENT '渠道为直播间引流人数',
    `transaction_amount` bigint   NOT NULL DEFAULT '0' COMMENT '渠道引流人在直播间交易金额(分)',
    `sub_commission`     bigint   NOT NULL DEFAULT '0' COMMENT '渠道在直播间分佣金额(分)',
    `commission_ratio`   int      NOT NULL DEFAULT '0' COMMENT '当日分佣比例 百分数1-100',
    `day`                date     NOT NULL COMMENT '日期',
    `deleted`            int      NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`        datetime NOT NULL COMMENT '创建时间',
    `update_time`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    unique key uk_channel_data (room_id, channel_id, `day`, deleted)
) COMMENT ='直播间(渠道)数据信息';

CREATE TABLE `t_order_info`
(
    `id`                       bigint       NOT NULL COMMENT '订单号',
    `order_no`                 varchar(100) NOT NULL COMMENT '订单号',
    `org_id`                   bigint       NOT NULL COMMENT '机构id',
    `transaction_id`           varchar(20) COMMENT '支付流水号',
    `room_id`                  bigint       NOT NULL COMMENT '订单发生的直播间id',
    `channel_id`               int          NOT NULL COMMENT '订单来源渠道id',
    `goods_id`                 bigint       NOT NULL COMMENT '交易商品id',
    `user_id`                  bigint COMMENT '下单人ID',
    `user_name`                varchar(20) COMMENT '下单人名称',
    `order_time`               datetime COMMENT '下单时间',
    `timeout_time`             datetime COMMENT '订单超时时间',
    `trade_time`               datetime COMMENT '成交时间（支付时间）',
    `refund_time`              datetime COMMENT '退款时间',
    `cancel_time`              datetime COMMENT '订单取消时间',
    `type`                     int          NOT NULL default '0' COMMENT '商品类型 1 虚拟商品 2 实物商品',
    `original_price`           bigint COMMENT '商品原价(分)',
    `coupon`                   varchar(10) COMMENT '使用的优惠卷',
    `price`                    bigint       not null DEFAULT '0' COMMENT '实际付款的金额(分)',
    `real_amt`                 bigint       not null DEFAULT '0' COMMENT '实际付款的金额(分)',
    `commission_ratio`         bigint       not null DEFAULT '0' COMMENT '该笔渠道分佣比例',
    `sub_commission`           bigint       not null DEFAULT '0' COMMENT '分佣金额',
    `payment_fee`              int          NOT NULL DEFAULT '0' COMMENT '支付手续费比例',
    `actual_accounting_amount` bigint                DEFAULT '0' COMMENT '实际分账金额',
    `order_status`             int          NOT NULL DEFAULT '1' COMMENT '订单状态 1待支付 2已支付 3已取消',
    `recipient_name`           varchar(50) COMMENT '收件人姓名 type=2必填',
    `recipient_phone`          varchar(20) COMMENT '收件人手机号 type=2必填',
    `recipient_province`       varchar(50) COMMENT '收件人所在省份 type=2必填',
    `recipient_city`           varchar(50) COMMENT '收件人所在城市 type=2必填',
    `recipient_country`        varchar(50) COMMENT '收件人所在县区 type=2必填',
    `recipient_address`        text COMMENT '收件人详细地址 type=2必填',
    `tl_pay_id`                bigint       not null comment '通联配置ID',
    `order_date`               date         NOT NULL COMMENT '下单日期',
    `deleted`                  int          NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`              datetime     NOT NULL COMMENT '创建时间',
    `update_time`              datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT ='订单信息表';


CREATE TABLE `t_user_info`
(
    `id`             bigint       NOT NULL COMMENT 'ID',
    `open_id`        varchar(255) NOT NULL COMMENT '当前开发者账号唯一',
    `nick_name`      varchar(20)  NOT NULL COMMENT '用户昵称',
    `head_image_url` varchar(300) NOT NULL COMMENT '用户头像URL地址',
    `union_id`       varchar(255) COMMENT '用户的unionid,唯一',
    `deleted`        int          NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `update_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT ='微信授权用户信息表';



CREATE TABLE `t_user_channel_relation`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `org_id`         bigint       NOT NULL COMMENT '机构id',
    `channel_id`     int          NOT NULL COMMENT '渠道ID 渠道表的channel_id字段',
    `room_id`        bigint       NOT NULL COMMENT '直播间ID',
    `user_id`        bigint       not null COMMENT '下单人ID',
    `open_id`        varchar(255) NOT NULL COMMENT '当前开发者账号唯一',
    `bind_date`      date         NOT NULL COMMENT '日期',
    `live_record_id` bigint comment '直播记录 ID',
    `deleted`        int          NOT NULL DEFAULT '0' COMMENT '是否删除: 0 未删除 1 已删除',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `update_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT ='微信授权用户与渠道映射表';


create table if not exists  t_sys_short_domain
(
    id              bigint AUTO_INCREMENT primary key comment 'ID',
    domain          varchar(255)  not null comment '短域名formatter',
    deleted         int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '短域名配置';


create table if not exists  t_org_short_domain
(
    id              bigint AUTO_INCREMENT primary key comment 'ID',
    `org_id`         bigint       NOT NULL COMMENT '机构id',
    domain          varchar(255)  not null comment '短域名formatter',
    deleted         int          not null default 0 comment '是否删除: 0 未删除 1 已删除',
    create_time     datetime     not null comment '创建时间',
    update_time     datetime     not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '机构定制短域名配置';
