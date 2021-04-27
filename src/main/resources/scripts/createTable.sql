DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    phone      VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS groups_students CASCADE;
CREATE TABLE groups_students
(
    group_id   INT REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    student_id INT REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (group_id, student_id)
);

DROP TABLE IF EXISTS teachers CASCADE;
CREATE TABLE teachers
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    phone         VARCHAR(50) NOT NULL,
    qualification VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS cathedras CASCADE;
CREATE TABLE cathedras
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS cathedra_groups CASCADE;
CREATE TABLE cathedra_groups
(
    cathedra_id INT REFERENCES cathedras (id) ON UPDATE CASCADE ON DELETE CASCADE,
    group_id    INT REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (cathedra_id, group_id)

);

DROP TABLE IF EXISTS cathedra_teachers CASCADE;
CREATE TABLE cathedra_teachers
(
    cathedra_id INT REFERENCES cathedras (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id  INT REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (cathedra_id, teacher_id)
);

DROP TABLE IF EXISTS month_timetables CASCADE;
CREATE TABLE month_timetables
(
    id   SERIAL PRIMARY KEY,
    date DATE NOT NULL
);

DROP TABLE IF EXISTS day_timetables CASCADE;
CREATE TABLE day_timetables
(
    id           SERIAL PRIMARY KEY,
    start_time   TIME        NOT NULL,
    lecture_hall VARCHAR(50) NOT NULL,
    subject      VARCHAR(50) NOT NULL,
    group_id     INT REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id   INT REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS day_timetables_month_timetables CASCADE;
CREATE TABLE day_timetables_month_timetables
(
    month_timetable_id INT REFERENCES month_timetables (id) ON UPDATE CASCADE ON DELETE CASCADE,
    day_timetable_id   INT REFERENCES day_timetables (id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (day_timetable_id, month_timetable_id)
);