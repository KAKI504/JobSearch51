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

INSERT INTO categories (name, parent_id)
SELECT 'Digital Marketing', (SELECT id FROM categories WHERE name = 'Sales & Marketing')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Digital Marketing');

INSERT INTO categories (name, parent_id)
SELECT 'Sales Manager', (SELECT id FROM categories WHERE name = 'Sales & Marketing')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Sales Manager');

INSERT INTO categories (name, parent_id)
SELECT 'Product Manager', (SELECT id FROM categories WHERE name = 'Management')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Product Manager');

INSERT INTO categories (name, parent_id)
SELECT 'Project Manager', (SELECT id FROM categories WHERE name = 'Management')
    WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Project Manager');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'Иван', 'Петров', 'ivan@example.com', 'password123', '+79001234567', 'APPLICANT', 30
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ivan@example.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'Елена', 'Сидорова', 'elena@example.com', 'password456', '+79002345678', 'APPLICANT', 27
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'elena@example.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'Алексей', 'Иванов', 'alexey@example.com', 'password789', '+79003456789', 'APPLICANT', 35
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'alexey@example.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'ООО Рога и Копыта', '', 'hr@rogaicopyta.com', 'password123', '+79009876543', 'EMPLOYER', 0
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hr@rogaicopyta.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'ЗАО ТехноПром', '', 'hr@technoprom.com', 'password456', '+79008765432', 'EMPLOYER', 0
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hr@technoprom.com');

INSERT INTO users (name, surname, email, password, phone_number, account_type, age)
SELECT 'ООО Стартап', '', 'hr@startup.com', 'password789', '+79007654321', 'EMPLOYER', 0
    WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'hr@startup.com');

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

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
SELECT
    (SELECT id FROM users WHERE email = 'elena@example.com'),
    'Frontend React Developer',
    (SELECT id FROM categories WHERE name = 'Frontend Developer'),
    '120000',
    true
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'elena@example.com')
AND NOT EXISTS (SELECT 1 FROM resumes WHERE applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com') AND name = 'Frontend React Developer');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
SELECT
    (SELECT id FROM users WHERE email = 'alexey@example.com'),
    'DevOps Инженер',
    (SELECT id FROM categories WHERE name = 'DevOps Engineer'),
    '170000',
    true
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'alexey@example.com')
AND NOT EXISTS (SELECT 1 FROM resumes WHERE applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com') AND name = 'DevOps Инженер');

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active)
SELECT
    (SELECT id FROM users WHERE email = 'alexey@example.com'),
    'Team Lead',
    (SELECT id FROM categories WHERE name = 'Management'),
    '200000',
    false
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'alexey@example.com')
AND NOT EXISTS (SELECT 1 FROM resumes WHERE applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com') AND name = 'Team Lead');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT
    (SELECT id FROM contact_types WHERE type = 'EMAIL'),
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    'ivan@example.com'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM contacts_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND contact_value = 'ivan@example.com');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT
    (SELECT id FROM contact_types WHERE type = 'PHONE'),
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    '+79001234567'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM contacts_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND contact_value = '+79001234567');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT
    (SELECT id FROM contact_types WHERE type = 'TELEGRAM'),
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    '@ivanpetrov'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM contacts_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND contact_value = '@ivanpetrov');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT
    (SELECT id FROM contact_types WHERE type = 'EMAIL'),
    (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')),
    'elena@example.com'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com'))
AND NOT EXISTS (SELECT 1 FROM contacts_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')) AND contact_value = 'elena@example.com');

INSERT INTO contacts_info (type_id, resume_id, contact_value)
SELECT
    (SELECT id FROM contact_types WHERE type = 'LINKEDIN'),
    (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')),
    'linkedin.com/in/elenasidorova'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com'))
AND NOT EXISTS (SELECT 1 FROM contacts_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')) AND contact_value = 'linkedin.com/in/elenasidorova');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    'Московский Государственный Университет',
    'Информатика и вычислительная техника',
    '2010-09-01',
    '2014-06-30',
    'Бакалавр'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND institution = 'Московский Государственный Университет');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    'Coursera',
    'Java Programming Masterclass',
    '2015-03-01',
    '2015-06-30',
    'Сертификат'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND institution = 'Coursera');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')),
    'Санкт-Петербургский Политехнический Университет',
    'Веб-разработка',
    '2012-09-01',
    '2016-06-30',
    'Бакалавр'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com'))
AND NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')) AND institution = 'Санкт-Петербургский Политехнический Университет');

INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
SELECT
    (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')),
    'МГТУ им. Баумана',
    'Информационная безопасность',
    '2005-09-01',
    '2010-06-30',
    'Магистр'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com'))
AND NOT EXISTS (SELECT 1 FROM education_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')) AND institution = 'МГТУ им. Баумана');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    3,
    'ООО КодМастер',
    'Java Developer',
    'Разработка и поддержка корпоративных приложений на Java. Работа с Spring Framework, Hibernate, PostgreSQL.'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND company_name = 'ООО КодМастер');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    2,
    'ЗАО ТехноСофт',
    'Junior Java Developer',
    'Поддержка и доработка существующих Java-приложений. Написание unit-тестов.'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND company_name = 'ЗАО ТехноСофт');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT
    (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')),
    2,
    'Стартап "ВебАпп"',
    'Frontend Developer',
    'Разработка пользовательских интерфейсов с использованием React.js, Redux, TypeScript.'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com'))
AND NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')) AND company_name = 'Стартап "ВебАпп"');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT
    (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')),
    5,
    'ПАО Технологии Будущего',
    'DevOps Engineer',
    'Настройка и поддержка CI/CD пайплайнов. Администрирование серверов Linux. Работа с Docker, Kubernetes, Jenkins.'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com'))
AND NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')) AND company_name = 'ПАО Технологии Будущего');

INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
SELECT
    (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')),
    3,
    'ООО ИТ-Сервис',
    'System Administrator',
    'Администрирование серверов Windows и Linux. Настройка и поддержка сетевой инфраструктуры.'
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com'))
AND NOT EXISTS (SELECT 1 FROM work_experience_info WHERE resume_id = (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')) AND company_name = 'ООО ИТ-Сервис');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Senior Java Developer',
    'Требуется опытный Java разработчик для работы над высоконагруженными системами. Обязанности: разработка и поддержка бэкенд-сервисов на Java. Требования: знание Spring Framework, Hibernate, опыт работы с микросервисной архитектурой.',
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
    'Ищем тимлида для Python команды. Требуется опыт управления командой разработчиков, хорошие знания Python, Django, опыт работы с СУБД PostgreSQL.',
    (SELECT id FROM categories WHERE name = 'Backend Developer'),
    '200000-250000',
    4,
    7,
    true,
    (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@rogaicopyta.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com') AND name = 'Python Team Lead');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Middle Frontend Developer',
    'Требуется Frontend-разработчик с опытом работы с React.js. Обязанности: разработка пользовательских интерфейсов, работа с REST API, написание unit-тестов.',
    (SELECT id FROM categories WHERE name = 'Frontend Developer'),
    '150000-170000',
    2,
    4,
    true,
    (SELECT id FROM users WHERE email = 'hr@technoprom.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@technoprom.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@technoprom.com') AND name = 'Middle Frontend Developer');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'DevOps Engineer',
    'Ищем DevOps инженера с опытом работы с контейнеризацией и оркестрацией. Требования: знание Docker, Kubernetes, Jenkins, опыт работы с AWS или GCP.',
    (SELECT id FROM categories WHERE name = 'DevOps Engineer'),
    '180000-210000',
    3,
    5,
    true,
    (SELECT id FROM users WHERE email = 'hr@technoprom.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@technoprom.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@technoprom.com') AND name = 'DevOps Engineer');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Junior React Developer',
    'Приглашаем в команду начинающего React-разработчика. Требования: базовые знания JavaScript, React.js, готовность к обучению.',
    (SELECT id FROM categories WHERE name = 'Frontend Developer'),
    '80000-100000',
    0,
    1,
    true,
    (SELECT id FROM users WHERE email = 'hr@startup.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@startup.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@startup.com') AND name = 'Junior React Developer');

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id)
SELECT
    'Full Stack Developer',
    'Требуется разработчик для работы над полным стеком технологий. Обязанности: разработка серверной и клиентской части приложений. Требования: знание JavaScript, Node.js, React.js, MongoDB.',
    (SELECT id FROM categories WHERE name = 'Full Stack Developer'),
    '150000-180000',
    2,
    4,
    true,
    (SELECT id FROM users WHERE email = 'hr@startup.com')
    WHERE EXISTS (SELECT 1 FROM users WHERE email = 'hr@startup.com')
AND NOT EXISTS (SELECT 1 FROM vacancies WHERE author_id = (SELECT id FROM users WHERE email = 'hr@startup.com') AND name = 'Full Stack Developer');

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
SELECT
    (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')),
    (SELECT id FROM vacancies WHERE name = 'Senior Java Developer'),
    false
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
AND EXISTS (SELECT 1 FROM vacancies WHERE name = 'Senior Java Developer')
AND NOT EXISTS (SELECT 1 FROM responded_applicants WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com')) AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer'));

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
SELECT
    (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')),
    (SELECT id FROM vacancies WHERE name = 'Middle Frontend Developer'),
    true
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com'))
AND EXISTS (SELECT 1 FROM vacancies WHERE name = 'Middle Frontend Developer')
AND NOT EXISTS (SELECT 1 FROM responded_applicants WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Frontend React Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'elena@example.com')) AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Middle Frontend Developer'));

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
SELECT
    (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')),
    (SELECT id FROM vacancies WHERE name = 'DevOps Engineer'),
    false
    WHERE EXISTS (SELECT 1 FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com'))
AND EXISTS (SELECT 1 FROM vacancies WHERE name = 'DevOps Engineer')
AND NOT EXISTS (SELECT 1 FROM responded_applicants WHERE resume_id = (SELECT id FROM resumes WHERE name = 'DevOps Инженер' AND applicant_id = (SELECT id FROM users WHERE email = 'alexey@example.com')) AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'DevOps Engineer'));

INSERT INTO messages (responded_applicants_id, content, sender_id)
SELECT
    (SELECT id FROM responded_applicants
     WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
       AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer')),
    'Здравствуйте! Я заинтересован в вашей вакансии. Готов обсудить детали.',
    (SELECT id FROM users WHERE email = 'ivan@example.com')
    WHERE EXISTS (SELECT 1 FROM responded_applicants 
                 WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
                 AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer'))
AND NOT EXISTS (SELECT 1 FROM messages WHERE responded_applicants_id = (SELECT id FROM responded_applicants 
                                            WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
                                            AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer'))
                AND content = 'Здравствуйте! Я заинтересован в вашей вакансии. Готов обсудить детали.');

INSERT INTO messages (responded_applicants_id, content, sender_id)
SELECT
    (SELECT id FROM responded_applicants
     WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
       AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer')),
    'Добрый день! Спасибо за интерес к нашей вакансии. Когда вам удобно пройти техническое интервью?',
    (SELECT id FROM users WHERE email = 'hr@rogaicopyta.com')
    WHERE EXISTS (SELECT 1 FROM responded_applicants 
                 WHERE resume_id = (SELECT id FROM resumes WHERE name = 'Java Developer' AND applicant_id = (SELECT id FROM users WHERE email = 'ivan@example.com'))
                 AND vacancy_id = (SELECT id FROM vacancies WHERE name = 'Senior Java Developer'))
AND NOT EXISTS