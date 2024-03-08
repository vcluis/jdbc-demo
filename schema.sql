use school;

create table students (
    id int not null auto_increment primary key,
    name varchar(45) not null,
    birthday date not null,
    created_at datetime default current_timestamp,
    updated_at datetime on update current_timestamp
);
