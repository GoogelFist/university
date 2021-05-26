DROP TABLE IF EXISTS students CASCADE;
CREATE TABLE students
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    phone      VARCHAR(50) NOT NULL,
    group_id   INT REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    cathedra_id INT REFERENCES cathedras (id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS teachers CASCADE;
CREATE TABLE teachers
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    phone         VARCHAR(50) NOT NULL,
    qualification VARCHAR(50) NOT NULL,
    cathedra_id   INT REFERENCES cathedras (id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS cathedras CASCADE;
CREATE TABLE cathedras
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
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
    id                 SERIAL PRIMARY KEY,
    start_time         TIME        NOT NULL,
    lecture_hall       VARCHAR(50) NOT NULL,
    subject            VARCHAR(50) NOT NULL,
    group_id           INT REFERENCES groups (id) ON UPDATE CASCADE ON DELETE CASCADE,
    teacher_id         INT REFERENCES teachers (id) ON UPDATE CASCADE ON DELETE CASCADE,
    month_timetable_id INT REFERENCES month_timetables (id) ON UPDATE CASCADE ON DELETE CASCADE
);