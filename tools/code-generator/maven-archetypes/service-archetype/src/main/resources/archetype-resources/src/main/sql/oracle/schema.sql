
    drop table ss_role cascade constraints;

    drop table ss_user cascade constraints;

    drop table ss_user_role cascade constraints;

    drop sequence hibernate_sequence;

    create table ss_role (
        id number(19,0) not null,
        name varchar2(255 char),
        primary key (id)
    );

    create table ss_user (
        id number(19,0) not null,
        email varchar2(255 char),
        login_name varchar2(255 char),
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (id)
    );

    create table ss_user_role (
        user_id number(19,0) not null,
        role_id number(19,0) not null,
        primary key (user_id, role_id)
    );

    alter table ss_user_role 
        add constraint FKF6CCD9C6C6000347 
        foreign key (role_id) 
        references ss_role;

    alter table ss_user_role 
        add constraint FKF6CCD9C66B2AC727 
        foreign key (user_id) 
        references ss_user;

    create sequence hibernate_sequence;
