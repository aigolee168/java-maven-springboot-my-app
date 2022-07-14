create table `authority` (
     `id` bigint not null auto_increment primary key,
     `code` varchar(100) not null,
     `text` varchar(100) not null,
     `type` tinyint not null,
     `parent_id` bigint default null
);

create table `role` (
    `id` int not null auto_increment primary key,
    `name` varchar(50) not null,
    `create_time` timestamp null default null,
    `status` tinyint not null,
    `auth_pattern` varchar(255) default null
);

create table `sys_user` (
    `id` int not null auto_increment primary key,
    `create_time` timestamp not null,
    `username` varchar(50) not null,
    `password` varchar(50) not null,
    `nickname` varchar(50) default null,
    `status` tinyint not null,
    `last_login_time` timestamp null default null,
    `parent_id` int not null
);

create table `role_authority` (
    `role_id` int not null,
    `authority_id` bigint not null,
    primary key (`role_id`,`authority_id`),
    constraint `role_authority_fk_1` foreign key (`role_id`) references `role` (`id`) on delete cascade on update cascade,
    constraint `role_authority_fk_2` foreign key (`authority_id`) references `authority` (`id`) on delete cascade on update cascade
);

create table `user_role` (
    `user_id` int not null,
    `role_id` int not null,
    primary key (`user_id`,`role_id`),
    constraint `user_role_fk_1` foreign key (`user_id`) references `sys_user` (`id`) on delete cascade on update cascade,
    constraint `user_role_fk_2` foreign key (`role_id`) references `role` (`id`) on delete cascade on update cascade
);