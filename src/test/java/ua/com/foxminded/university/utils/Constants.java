package ua.com.foxminded.university.utils;

import java.time.LocalDate;
import java.time.LocalTime;

public class Constants {
    private Constants() {
    }
    public static final String TEST_DATA_SQL_PATH = "/testData.sql";
    public static final String CREATE_TABLES_SQL_PATH = "classpath:createTestTable.sql";

    public static final String TOTAL_ELEMENTS = "totalElements";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String START_TIME = "startTime";
    public static final String LECTURE_HALL = "lectureHall";
    public static final String SUBJECT = "subject";
    public static final String GROUP = "group";
    public static final String GROUPS = "groups";
    public static final String GROUP_ID = "group.id";
    public static final String TEACHER = "teacher";
    public static final String TEACHERS = "teachers";
    public static final String TEACHER_ID = "teacher.id";
    public static final String STUDENTS = "students";
    public static final String STUDENT = "student";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE = "phone";
    public static final String QUALIFICATION = "qualification";
    public static final String CATHEDRA = "cathedra";
    public static final String CATHEDRAS = "cathedras";
    public static final String CATHEDRA_ID = "cathedra.id";
    public static final String MONTH_TIMETABLE = "monthTimetable";
    public static final String MONTH_TIMETABLES = "monthTimetables";
    public static final String MONTH_TIMETABLE_ID = "monthTimetable.id";
    public static final String DAY_TIMETABLE = "dayTimetable";
    public static final String NAME = "name";
    public static final String PHYSICS = "physics";
    public static final String MEDICALS = "medicals";
    public static final String MESSAGE_PROPERTY_NAME = "message";

    public static final int ID_0_VALUE = 0;
    public static final int ID_1_VALUE = 1;
    public static final int ID_2_VALUE = 2;
    public static final int ID_3_VALUE = 3;
    public static final int ID_5_VALUE = 5;

    public static final int INT_VALUE_2 = 2;

    public static final String QUALIFICATION_1_VALUE = "1";
    public static final String QUALIFICATION_2_VALUE = "2";

    public static final String CATHEDRA_1_NAME_VALUE = "physics";
    public static final String CATHEDRA_2_NAME_VALUE = "medicals";
    public static final String CATHEDRA_3_NAME_VALUE = "math";

    public static final String TEACHER_1_FIRST_NAME_VALUE = "Jonathan";
    public static final String TEACHER_1_LAST_NAME_VALUE = "Bride";
    public static final String TEACHER_1_PHONE_VALUE = "612345";

    public static final String TEACHER_2_FIRST_NAME_VALUE = "Bill";
    public static final String TEACHER_2_LAST_NAME_VALUE = "Noise";
    public static final String TEACHER_2_PHONE_VALUE = "64321";

    public static final String TEACHER_3_FIRST_NAME_VALUE = "Stacy";
    public static final String TEACHER_3_LAST_NAME_VALUE = "Jonson";
    public static final String TEACHER_3_PHONE_VALUE = "54325";

    public static final String STUDENT_1_FIRST_NAME_VALUE = "James";
    public static final String STUDENT_1_LAST_NAME_VALUE = "Gosling";
    public static final String STUDENT_1_PHONE_VALUE = "12345";

    public static final String STUDENT_2_FIRST_NAME_VALUE = "Mikhail";
    public static final String STUDENT_2_LAST_NAME_VALUE = "Denver";
    public static final String STUDENT_2_PHONE_VALUE = "54321";

    public static final String STUDENT_3_FIRST_NAME_VALUE = "John";
    public static final String STUDENT_3_LAST_NAME_VALUE = "Manson";
    public static final String STUDENT_3_PHONE_VALUE = "777";


    public static final String GROUP_1_NAME_VALUE = "AC-10";
    public static final String GROUP_2_NAME_VALUE = "BC-20";
    public static final String GROUP_3_NAME_VALUE = "CC-10";

    public static final LocalDate MONTH_TIMETABLE_DATE_VALUE_1 = LocalDate.of(2021, 4, 23);
    public static final LocalDate MONTH_TIMETABLE_DATE_VALUE_2 = LocalDate.of(2021, 4, 24);
    public static final LocalDate MONTH_TIMETABLE_DATE_VALUE_3 = LocalDate.of(2021, 4, 25);

    public static final LocalTime DAY_TIMETABLE_1_TIME_VALUE = LocalTime.of(8, 0);
    public static final LocalTime DAY_TIMETABLE_2_TIME_VALUE = LocalTime.of(10, 0);
    public static final LocalTime DAY_TIMETABLE_3_TIME_VALUE = LocalTime.of(12, 0);
    public static final LocalTime DAY_TIMETABLE_4_TIME_VALUE = LocalTime.of(14, 0);

    public static final String LECTURE_HALL_1_VALUE = "112";
    public static final String LECTURE_HALL_2_VALUE = "223";
    public static final String LECTURE_HALL_3_VALUE = "312";
    public static final String LECTURE_HALL_4_VALUE = "412";

    public static final String SUBJECT_1_VALUE = "math";
    public static final String SUBJECT_2_VALUE = "biology";
    public static final String SUBJECT_3_VALUE = "physic";
    public static final String SUBJECT_4_VALUE = "arts";

    public static final String MESSAGE_PROPERTY_VALUE = "student with ID 101 not found";

    public static final Long TOTAL_ELEMENTS_VALUE_1 = 1L;
    public static final Long TOTAL_ELEMENTS_VALUE_2 = 2L;

    public static final String COLUMN_LABEL_ID = "id";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_LABEL_NAME = "name";
    public static final String COLUMN_LABEL_FIRST_NAME = "first_name";
    public static final String COLUMN_LABEL_LAST_NAME = "last_name";
    public static final String COLUMN_LABEL_DATE = "date";
    public static final String COLUMN_LABEL_PHONE = "phone";
    public static final String COLUMN_LABEL_QUALIFICATION = "qualification";
    public static final String COLUMN_LABEL_LECTURE_HALL = "lecture_hall";
    public static final String COLUMN_LABEL_SUBJECT = "subject";
    public static final String COLUMN_LABEL_GROUP_ID = "group_id";
    public static final String COLUMN_LABEL_TEACHER_ID = "teacher_id";
    public static final String COLUMN_LABEL_MONT_TIMETABLE_ID = "month_timetable_id";
    public static final String COLUMN_LABEL_CATHEDRA_ID = "cathedra_id";

    public static final int NUMBER_OF_INVOCATIONS_VALUE = 1;
    public static final int PAGE = 0;
    public static final int SIZE = 2;

    public static final String SERVICE_EXCEPTION_MESSAGE_BY_ID = "Unable to %s %s with Id %s";
    public static final String SERVICE_EXCEPTION_MESSAGE_BY_ENTITY_ID = "Unable to %s %s by %s with Id %s";
    public static final String SERVICE_EXCEPTION_MESSAGE_CREATE = "Unable to create %s";
    public static final String SERVICE_EXCEPTION_MESSAGE_GET_ALL = "Unable to get all %s";
    public static final String SERVICE_EXCEPTION_MESSAGE_ASSIGN = "Unable %s %s with id %s to %s with id %s";
    public static final String SERVICE_EXCEPTION_MESSAGE_ASSIGNMENT = "Unable to %s assignment %s with id %s to %s with id %s";
    public static final String GET = "get";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String ASSIGN = "assign";

    public static final String REDIRECT = "redirect:/%s";
}