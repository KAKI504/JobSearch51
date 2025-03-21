create table if not exists users
(
    id           int auto_increment primary key,
    name         varchar(100) not null,
    surname      varchar(100),
    email        varchar(100) unique not null,
    password     varchar(100) not null,
    phone_number varchar(20),
    avatar       varchar(255),
    account_type varchar(20) not null,
    age          int
    );

create table if not exists categories
(
    id        int auto_increment primary key,
    name      varchar(100) not null,
    parent_id int,
    foreign key (parent_id) references categories (id)
    );

create table if not exists contact_types
(
    id   int auto_increment primary key,
    type varchar(50) not null
    );

create table if not exists vacancies
(
    id           int auto_increment primary key,
    name         varchar(255) not null,
    description  text,
    category_id  int,
    salary       varchar(100),
    exp_from     int,
    exp_to       int,
    is_active    boolean default true,
    author_id    int     not null,
    created_date timestamp default current_timestamp,
    update_time  timestamp default current_timestamp,
    foreign key (category_id) references categories (id),
    foreign key (author_id) references users (id)
    );

create table if not exists resumes
(
    id           int auto_increment primary key,
    applicant_id int          not null,
    name         varchar(255) not null,
    category_id  int,
    salary       varchar(100),
    is_active    boolean default true,
    created_date timestamp default current_timestamp,
    update_time  timestamp default current_timestamp,
    foreign key (applicant_id) references users (id),
    foreign key (category_id) references categories (id)
    );

create table if not exists contacts_info
(
    id           int auto_increment primary key,
    type_id      int          not null,
    resume_id    int          not null,
    contact_value varchar(255) not null,
    foreign key (type_id) references contact_types (id),
    foreign key (resume_id) references resumes (id)
    );

create table if not exists work_experience_info
(
    id               int auto_increment primary key,
    resume_id        int          not null,
    years            int,
    company_name     varchar(255) not null,
    position         varchar(255) not null,
    responsibilities text,
    foreign key (resume_id) references resumes (id)
    );

create table if not exists education_info
(
    id          int auto_increment primary key,
    resume_id   int          not null,
    institution varchar(255) not null,
    program     varchar(255),
    start_date  date,
    end_date    date,
    degree      varchar(255),
    foreign key (resume_id) references resumes (id)
    );

create table if not exists responded_applicants
(
    id           int auto_increment primary key,
    resume_id    int     not null,
    vacancy_id   int     not null,
    confirmation boolean default false,
    foreign key (resume_id) references resumes (id),
    foreign key (vacancy_id) references vacancies (id)
    );

create table if not exists messages
(
    id                      int auto_increment primary key,
    responded_applicants_id int     not null,
    content                 text    not null,
    timestamp               timestamp default current_timestamp,
    sender_id               int     not null,
    foreign key (responded_applicants_id) references responded_applicants (id),
    foreign key (sender_id) references users (id)
    );