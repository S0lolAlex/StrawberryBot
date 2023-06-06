create table if not exists booking(
id bigint not null auto_increment primary key,
sort varchar(50),
bush_count int,
phone bigint,
client_id bigint,
status boolean default false,
CONSTRAINT booking_fk_client FOREIGN KEY (client_id) REFERENCES client(id)
)