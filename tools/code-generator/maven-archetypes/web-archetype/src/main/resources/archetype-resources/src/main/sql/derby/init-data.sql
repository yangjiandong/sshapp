insert into SS_USER (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL) values(1,'admin','admin','Admin','admin@springside.org.cn');

insert into SS_ROLE (ID,NAME) values(1,'管理员');
insert into SS_ROLE (ID,NAME) values(2,'用户');
              
insert into SS_AUTHORITY (ID,NAME,DISPLAY_NAME) values(1,'A_VIEW_USER','查看用户');
insert into SS_AUTHORITY (ID,NAME,DISPLAY_NAME) values(2,'A_MODIFY_USER','管理用户');
insert into SS_AUTHORITY (ID,NAME,DISPLAY_NAME) values(3,'A_VIEW_ROLE','查看角色');
insert into SS_AUTHORITY (ID,NAME,DISPLAY_NAME) values(4,'A_MODIFY_ROLE','管理角色');

insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(1,'url','/security/user!save*',1.0);
insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(2,'url','/security/user!delete*',2.0);
insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(3,'url','/security/user*',3.0);
insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(4,'url','/security/role!save*',4.0);
insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(5,'url','/security/role!delete*',5.0);
insert into SS_RESOURCE (ID,RESOURCE_TYPE,VALUE,POSITION) values(6,'url','/security/role*',6.0);

insert into SS_USER_ROLE values(1,1);
insert into SS_USER_ROLE values(1,2);

insert into SS_ROLE_AUTHORITY values(1,1);
insert into SS_ROLE_AUTHORITY values(1,2);
insert into SS_ROLE_AUTHORITY values(1,3);
insert into SS_ROLE_AUTHORITY values(1,4);
insert into SS_ROLE_AUTHORITY values(2,1);
insert into SS_ROLE_AUTHORITY values(2,3);

insert into SS_RESOURCE_AUTHORITY values(2,1);
insert into SS_RESOURCE_AUTHORITY values(2,2);
insert into SS_RESOURCE_AUTHORITY values(1,3);
insert into SS_RESOURCE_AUTHORITY values(4,4);
insert into SS_RESOURCE_AUTHORITY values(4,5);
insert into SS_RESOURCE_AUTHORITY values(3,6);
