create table if not exists client(
id bigint not null primary key,
chat_id bigint not null,
name varchar(50),
phone varchar(10),
booking varchar(500)
)