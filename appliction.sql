create table admin (auth_idx bigint not null auto_increment, created_date datetime, modified_date datetime, class_number varchar(4) not null, email varchar(30) not null, name varchar(100) not null, password varchar(100) not null, primary key (auth_idx)) engine=InnoDB
create table admin_roles (admin_auth_idx bigint not null, roles varchar(255)) engine=InnoDB
create table equipment (equ_idx bigint not null auto_increment, created_date datetime, modified_date datetime, content varchar(255), count integer not null, img_equipment varchar(255), name varchar(255), primary key (equ_idx)) engine=InnoDB
create table equipment_allow (eqa_idx bigint not null auto_increment, created_date datetime, modified_date datetime, amount integer, equipment_enum varchar(255), reason varchar(300), admin_idx bigint, equ_idx bigint, primary key (eqa_idx)) engine=InnoDB
create table laptop (laptop_idx bigint not null auto_increment, created_date datetime, modified_date datetime, class_number varchar(255), laptop_serial_number varchar(255), student_name varchar(255), admin_idx bigint, spec_idx bigint, primary key (laptop_idx)) engine=InnoDB
create table laptop_spec (spec_idx bigint not null auto_increment, created_date datetime, modified_date datetime, cpu varchar(255), gpu varchar(255), hdd varchar(255), ram varchar(255), ssd varchar(255), laptop_brand varchar(255), laptop_name varchar(255), primary key (spec_idx)) engine=InnoDB
create table notice (notice_idx bigint not null auto_increment, created_date datetime, modified_date datetime, admin_idx bigint, content varchar(255), title varchar(255), primary key (notice_idx)) engine=InnoDB
alter table admin add constraint UK_c0r9atamxvbhjjvy5j8da1kam unique (email)
alter table laptop add constraint UK_q0ce44nor3yb8dx1b88ge2jje unique (laptop_serial_number)
alter table admin_roles add constraint FKcti0i2l1g0co7xp7i2gwgkexo foreign key (admin_auth_idx) references admin (auth_idx)
alter table equipment_allow add constraint FKkjparjxwb0f5nwa2sjfkkidsq foreign key (admin_idx) references admin (auth_idx)
alter table equipment_allow add constraint FKahy33otk2jtlokp2814frac0f foreign key (equ_idx) references equipment (equ_idx)
alter table laptop add constraint FKmq6ydn109vkr9burfmwy8ipdw foreign key (admin_idx) references admin (auth_idx)
alter table laptop add constraint FKgwevqldy605uv5v0nbydvmyy1 foreign key (spec_idx) references laptop_spec (spec_idx)