
create table user
(
   user_id integer not null IDENTITY (1,1),
   email varchar not null,
   password varchar not null,
   primary key(user_id)
);

create table product
(
   product_id integer not null IDENTITY (1,1),
   title varchar not null,
   quantity integer not null,
   price float not null,
   primary key(product_id)
);


create table cart
(
session_id varchar(255) not null,
product_id integer not null,
quantity integer not null,

)

