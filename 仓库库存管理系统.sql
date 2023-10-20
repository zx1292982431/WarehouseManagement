create database manage_system;
use manage_system;

创建管理员表
create table admin(
    admin_id char(5) primary key,
    name varchar(40) not null unique,
    sex char(10) check(sex='男' or sex='女'),
    age int check(age>=18),
    salary decimal default 0,
    phone char(11) not null,
    password blob not null);

创建系统管理员表 // 添加邮箱字段
create table super_admin(
    super_admin_id char(5) primary key,
    email varchar(255) not null,
    password blob not null);

创建仓库表
create table warehouse(
    warehouse_id int AUTO_INCREMENT PRIMARY KEY,
    max_cap decimal not null check(max_cap!=0),
    used_cap decimal not null);

创建货品表
create table good(
    good_id int AUTO_INCREMENT PRIMARY KEY,
    cap decimal not null,
    value decimal not null,
    name varchar(255) not null unique);

创建存放表
create table deposit(
    good_id int,
    warehouse_id int,
    good_num int not null,
    primary key(good_id,warehouse_id),
    foreign key(warehouse_id) references warehouse(warehouse_id),
    foreign key(good_id) references good(good_id));

创建出库表
create table out_warehouse(
    out_warehouse_id int AUTO_INCREMENT primary key,
    admin_id char(5) not null,
    warehouse_id int not null,
    good_id int not null,
    out_warehouse_num int not null,
    out_warehouse_time datetime,
    foreign key(admin_id) references admin(admin_id),
    foreign key(warehouse_id) references warehouse(warehouse_id),
    foreign key(good_id) references good(good_id));

创建入库表
create table in_warehouse(
    in_warehouse_id int AUTO_INCREMENT primary key,
    admin_id char(5) not null,
    warehouse_id int not null,
    good_id int not null,
    in_warehouse_num int not null,
    in_warehouse_time datetime,
    foreign key(admin_id) references admin(admin_id),
    foreign key(warehouse_id) references warehouse(warehouse_id),
    foreign key(good_id) references good(good_id));
