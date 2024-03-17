use school;

drop table if exists students;
create table students (
    id int not null auto_increment primary key,
    name varchar(45) not null,
    birthday date not null,
    created_at datetime default current_timestamp,
    updated_at datetime on update current_timestamp
);

drop table if exists subject;
create table subject (
    id int not null auto_increment primary key,
    name varchar(35) not null,
    created_at datetime default current_timestamp,
    updated_at datetime on update current_timestamp
);

drop table if exists teacher;
create table teacher (
    id int not null auto_increment primary key,
    name varchar(45),
    birthday date not null,
    dpi varchar(15),
    created_at datetime default current_timestamp,
    updated_at datetime on update current_timestamp
);

drop table if exists subject_teacher;
create table subject_teacher (
    id int not null auto_increment primary key,
    subject_id int not null,
    teacher_id int not null,
    foreign key(subject_id) references subject(id),
    foreign key(teacher_id) references teacher(id),
    created_at datetime default current_timestamp,
    updated_at datetime on update current_timestamp
);

insert into students(name, birthday) values("First", curdate()), ("Second", curdate()), ("Third", curdate()), ("Fourth", curdate()), ("Fift", curdate());

