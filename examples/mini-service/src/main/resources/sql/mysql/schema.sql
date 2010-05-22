
    alter table acct_user_role 
        drop foreign key FKFE85CB3E16A0ABF1;

    alter table acct_user_role 
        drop foreign key FKFE85CB3E7175E811;

    drop table if exists acct_role;

    drop table if exists acct_user;

    drop table if exists acct_user_role;

    create table acct_role (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table acct_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table acct_user_role (
        user_id bigint not null,
        role_id bigint not null
    ) ENGINE=InnoDB;

    alter table acct_user_role 
        add constraint FKFE85CB3E16A0ABF1 
        foreign key (user_id) 
        references acct_user (id);

    alter table acct_user_role 
        add constraint FKFE85CB3E7175E811 
        foreign key (role_id) 
        references acct_role (id);
