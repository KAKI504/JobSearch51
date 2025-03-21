-- Типы контактов
INSERT INTO contact_types (type)
SELECT 'EMAIL' WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'EMAIL');

INSERT INTO contact_types (type)
SELECT 'PHONE' WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'PHONE');

INSERT INTO contact_types (type)
SELECT 'TELEGRAM' WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'TELEGRAM');

INSERT INTO contact_types (type)
SELECT 'FACEBOOK' WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'FACEBOOK');

INSERT INTO contact_types (type)
SELECT 'LINKEDIN' WHERE NOT EXISTS (SELECT 1 FROM contact_types WHERE type = 'LINKEDIN');

-- Категории
INSERT INTO categories (name)
SELECT 'IT & Development' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'IT & Development');

INSERT INTO categories (name)
SELECT 'Sales & Marketing' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Sales & Marketing');

INSERT INTO categories (name)
SELECT 'Management' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Management');

INSERT INTO categories (name)
SELECT 'Administrative' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Administrative');

INSERT INTO categories (name)
SELECT 'Finance' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Finance');

INSERT INTO categories (name)
SELECT 'Education' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Education');

INSERT INTO categories (name)
SELECT 'Healthcare' WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Healthcare');

-- Подкатегории
INSERT INTO categories (name, parent_id)
SELECT 'Frontend Developer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Frontend Developer');

INSERT INTO categories (name, parent_id)
SELECT 'Backend Developer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Backend Developer');

INSERT INTO categories (name, parent_id)
SELECT 'Full Stack Developer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Full Stack Developer');

INSERT INTO categories (name, parent_id)
SELECT 'Mobile Developer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Mobile Developer');

INSERT INTO categories (name, parent_id)
SELECT 'DevOps Engineer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'DevOps Engineer');

INSERT INTO categories (name, parent_id)
SELECT 'QA Engineer', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'QA Engineer');

INSERT INTO categories (name, parent_id)
SELECT 'Data Scientist', (SELECT id FROM categories WHERE name = 'IT & Development')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Data Scientist');

-- Пользователи: соискатель и работодатель
INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'Иван', 'Петров', 'ivan@example.com', 'password123', '+79001234567', 'APPLICANT', 30
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ivan@example.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'ООО Рога и Копыта', '', 'hr@rogaicopyta.com', 'password123', '+79009876543', 'EMPLOYER', 0
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hr@rogaicopyta.com');

-- Резюме для соискателя (только если пользователь существует)
INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
SELECT
    (SELECT id FROM users WHERE email = 'ivan@example.com'),
    'Java Developer',
    (SELECT id FROM categories WHERE name = 'Backend Developer'),
    '150000',
    true
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'ivan@example.com')
AND NOT EXISTS (SELECT 1 FROM resumes WHERE applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com') AND name = 'Java Developer');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
SELECT
    (SELECT id FROM users WHERE email = 'ivan@example.com'),
    'Python Developer',
    (SELECT id FROM categories WHERE name = 'Backend Developer'),
    '130000',
    true
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'ivan@example.com')
AND NOT EXISTS (SELECT 1 FROM resumes WHERE applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com') AND name = 'Python Developer');

-- Вакансии для работодателя (только если работодатель существует)
INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Senior Java Developer',
    'Требуется опытный Java разработчик',
    (SELECT id FROM categories WHERE name = 'Backend Developer'),
    '180000-220000',
    3,
    5,
    true,
    (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@rogaicopyta.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com') AND name = 'Senior Java Developer');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Python Team Lead',
    'Ищем тимлида для Python команды',
    (SELECT id FROM categories WHERE name = 'Backend Developer'),
    '200000-250000',
    4,
    7,
    true,
    (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@rogaicopyta.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com') AND name = 'Python Team Lead');