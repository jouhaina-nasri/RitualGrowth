drop schema rg if exists cascade;
create schema rg;

create table rg.user
(
   id bigint not null,
   disabled boolean,
   email varchar (255) not null,
   full_name varchar (511) not null,
   creation_time timestamp not null,
   ap_uid varchar (127) not null,
   reactivation_token varchar (64),
   primary key (id),
   constraint UNQ_user_email unique (email),
   constraint UNQ_user_full_name unique (full_name),
   constraint UNQ_user_ap_uid unique (ap_uid)
);


create table rg.privilege
(
   id integer not null,
   name varchar (255) not null,
   primary key (id),
   constraint UK_privilege_name unique (name)
);

create table rg.user_privileges
(
   user_id bigint not null,
   privilege_id integer not null,
   primary key(user_id, privilege_id),
   constraint FK_user_priv_priv_id foreign key (privilege_id) references rg.privilege on delete cascade on update cascade,
   constraint FK_user_priv_user_id foreign key (user_id) references rg.user on delete cascade on update cascade
);

create table rg.client_app
(
	id bigint not null,
	user_id bigint not null,
  	messaging_token varchar (255),
	primary key (id),
	constraint UK_client_app_user_messaging_token_unique unique (user_id, messaging_token),
	constraint FK_client_app_to_user foreign key (user_id) references rg.user on delete cascade on update cascade
);


create table rg.user_statuses
(
   user_id bigint not null,
   statuses integer,
   constraint FK_user_statuses_user_id foreign key (user_id) references rg.user
);



-- https://stackoverflow.com/questions/47896049/h2-sequence-is-generating-negative-no-in-column-jpa-spring
create sequence rg.user_sequence start with 1 increment by 50;
create sequence rg.client_app_sequence start with 1 increment by 50;


