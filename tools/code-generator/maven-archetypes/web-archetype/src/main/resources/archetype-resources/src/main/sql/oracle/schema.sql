
    drop table ss_authority cascade constraints;

    drop table ss_resource cascade constraints;

    drop table ss_resource_authority cascade constraints;

    drop table ss_role cascade constraints;

    drop table ss_role_authority cascade constraints;

    drop table ss_user cascade constraints;

    drop table ss_user_role cascade constraints;

    drop sequence hibernate_sequence;

    create table ss_authority (
        id number(19,0) not null,
        display_name varchar2(255 char),
        name varchar2(255 char),
        primary key (id)
    );

    create table ss_resource (
        id number(19,0) not null,
        position double precision not null,
        resource_type varchar2(255 char),
        value varchar2(255 char),
        primary key (id)
    );

    create table ss_resource_authority (
        resource_id number(19,0) not null,
        authority_id number(19,0) not null,
        primary key (resource_id, authority_id)
    );

    create table ss_role (
        id number(19,0) not null,
        name varchar2(255 char),
        primary key (id)
    );

    create table ss_role_authority (
        role_id number(19,0) not null,
        authority_id number(19,0) not null,
        primary key (role_id, authority_id)
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

    alter table ss_resource_authority 
        add constraint FKF58B2007C67601C1 
        foreign key (authority_id) 
        references ss_authority;

    alter table ss_resource_authority 
        add constraint FKF58B200770DB5B33 
        foreign key (resource_id) 
        references ss_resource;

    alter table ss_role_authority 
        add constraint FKE9CCCC9F93BA26B3 
        foreign key (role_id) 
        references ss_role;

    alter table ss_role_authority 
        add constraint FKE9CCCC9FC67601C1 
        foreign key (authority_id) 
        references ss_authority;

    alter table ss_user_role 
        add constraint FKF6CCD9C693BA26B3 
        foreign key (role_id) 
        references ss_role;

    alter table ss_user_role 
        add constraint FKF6CCD9C638E4EA93 
        foreign key (user_id) 
        references ss_user;

    create sequence hibernate_sequence;
