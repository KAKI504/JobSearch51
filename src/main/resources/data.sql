INSERT INTO contact_types (type)
VALUES ('EMAIL'),
       ('PHONE'),
       ('TELEGRAM'),
       ('FACEBOOK'),
       ('LINKEDIN');

INSERT INTO categories (name)
VALUES ('IT & Development'),
       ('Sales & Marketing'),
       ('Management'),
       ('Administrative'),
       ('Finance'),
       ('Education'),
       ('Healthcare');

INSERT INTO categories (name, parent_id)
VALUES ('Frontend Developer', 1),
       ('Backend Developer', 1),
       ('Full Stack Developer', 1),
       ('Mobile Developer', 1),
       ('DevOps Engineer', 1),
       ('QA Engineer', 1),
       ('Data Scientist', 1);

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
VALUES ('Иван', 'Петров', 'ivan@example.com', 'password123', '+79001234567', 'APPLICANT', 30),
       ('ООО Рога и Копыта', '', 'hr@rogaicopyta.com', 'password123', '+79009876543', 'EMPLOYER', 0);

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
VALUES (1, 'Java Developer', 9, '150000', true),
       (1, 'Python Developer', 9, '130000', true);

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
VALUES ('Senior Java Developer', 'Требуется опытный Java разработчик', 9, '180000-220000', 3, 5, true, 2),
       ('Python Team Lead', 'Ищем тимлида для Python команды', 9, '200000-250000', 4, 7, true, 2);

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES (1, 1, false);

INSERT INTO messages (responded_applicants_id, content, sender_id)
VALUES (1, 'Добрый день, я заинтересован в вашей вакансии', 1),
       (1, 'Здравствуйте! Когда вам будет удобно пройти собеседование?', 2);

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES (1, 3, 'ООО Технологии', 'Java разработчик', 'Разработка и поддержка веб-приложений');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
VALUES (1, 'Московский Технический Университет', 'Информатика и ВТ', '2015-09-01', '2019-06-30', 'Бакалавр');

INSERT INTO contacts_info (type_id, resume_id, value)
VALUES (1, 1, 'ivan@example.com'),
       (2, 1, '+79001234567');