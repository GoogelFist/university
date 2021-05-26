DELETE
FROM cathedras
WHERE TRUE;
ALTER TABLE cathedras
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO cathedras
VALUES (1, 'physics');
INSERT INTO cathedras
VALUES (2, 'medicals');

DELETE
FROM groups
WHERE TRUE;
ALTER TABLE groups
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO groups
VALUES (1, 'AC-10', 1);
INSERT INTO groups
VALUES (2, 'BC-20', 2);

DELETE
FROM students
WHERE TRUE;
ALTER TABLE students
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO students
VALUES (1, 'James', 'Gosling', '12345', 1);
INSERT INTO students
VALUES (2, 'Mikhail', 'Denver', '54321', 2);

DELETE
FROM teachers
WHERE TRUE;
ALTER TABLE teachers
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO teachers
VALUES (1, 'Jonathan', 'Bride', '612345', '1', 1);
INSERT INTO teachers
VALUES (2, 'Bill', 'Noise', '64321', '2', 2);

DELETE
FROM month_timetables
WHERE TRUE;
ALTER TABLE month_timetables
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO month_timetables
VALUES (1, '2021-04-23');
INSERT INTO month_timetables
VALUES (2, '2021-04-24');

DELETE
FROM day_timetables
WHERE TRUE;
ALTER TABLE day_timetables
    ALTER COLUMN id RESTART WITH 1;

INSERT INTO day_timetables
VALUES (1, '08:00', '112', 'math', 1, 1, 1);
INSERT INTO day_timetables
VALUES (2, '10:00', '312', 'physic', 2, 2, 2);