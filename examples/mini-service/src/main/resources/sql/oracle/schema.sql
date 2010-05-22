
    drop table acct_role cascade constraints;

    drop table acct_user cascade constraints;

    drop table acct_user_role cascade constraints;

    drop sequence hibernate_sequence;

    create table acct_role (
        id number(19,0) not null,
        name varchar2(255 char) not null unique,
        primary key (id)
    );

    create table acct_user (
        id number(19,0) not null,
        email varchar2(255 char),
        login_name varchar2(255 char) not null unique,
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (id)
    );

    create table acct_user_role (
        user_id number(19,0) not null,
        role_id number(19,0) not null
    );

    alter table acct_user_role 
        add constraint FKFE85CB3E16A0ABF1 
        foreign key (user_id) 
        references acct_user;

    alter table acct_user_role 
        add constraint FKFE85CB3E7175E811 
        foreign key (role_id) 
        references acct_role;

    create sequence hibernate_sequence;
