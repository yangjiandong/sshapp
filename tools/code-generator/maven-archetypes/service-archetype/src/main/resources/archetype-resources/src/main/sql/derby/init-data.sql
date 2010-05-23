insert into SS_USER (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL) values(1,'admin','admin','Admin','admin@springside.org.cn');

insert into SS_ROLE (ID,NAME) values(1,'管理员');
insert into SS_ROLE (ID,NAME) values(2,'用户');

insert into SS_USER_ROLE values(1,1);
insert into SS_USER_ROLE values(1,2);