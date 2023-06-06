create table if not exists strawberry(
id bigint not null auto_increment primary key,
name varchar(50) not null,
price bigint,
bush_count int,
status boolean default false
)