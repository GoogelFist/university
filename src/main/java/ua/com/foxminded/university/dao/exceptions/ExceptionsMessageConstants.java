package ua.com.foxminded.university.dao.exceptions;

public class ExceptionsMessageConstants {
    private ExceptionsMessageConstants() {
    }

    public static final String UNABLE_CREATE = "Unable to create %s";

    public static final String ENTITY_NOT_FOUND = "%s with ID %s not found";

    public static final String UNABLE_GET_BY_ID = "Unable to get %s with ID %s";
    public static final String UNABLE_GET_BY_GROUP_ID = "Unable to get %s by group id %s";
    public static final String UNABLE_GET_BY_CATHEDRA_ID = "Unable to get %s by cathedra id %s";
    public static final String UNABLE_GET_BY_TEACHER_ID = "Unable to get %s by teacher id %s";
    public static final String UNABLE_GET_BY_MONTH_TIMETABLE_ID = "Unable to get %s by month_timetable id %s";

    public static final String UNABLE_GET_ALL = "Unable to get all %s";

    public static final String UNABLE_UPDATE = "Unable to update %s with ID %s";

    public static final String UNABLE_UPDATE_ASSIGNMENT_STUDENT_TO_GROUP = "Unable to update assignment student id %s to group with id %s";
    public static final String UNABLE_UPDATE_ASSIGNMENT_GROUP_TO_CATHEDRA = "Unable to update assignment group id %s to cathedra with id %s";
    public static final String UNABLE_UPDATE_ASSIGNMENT_TEACHER_TO_CATHEDRA = "Unable to update assignment teacher id %s to cathedra with id %s";

    public static final String UNABLE_DELETE = "Unable to delete %s with ID %s";

    public static final String UNABLE_DELETE_ASSIGNMENT_STUDENT = "Unable to delete assignment student id %s from group";
    public static final String UNABLE_DELETE_ASSIGNMENT_GROUP = "Unable to delete assignment group id %s from cathedra";
    public static final String UNABLE_DELETE_ASSIGNMENT_TEACHER = "Unable to delete assignment teacher id %s from cathedra";

    public static final String UNABLE_ASSIGN_STUDENT_TO_GROUP = "Unable assign student with id %s to group with id %s";
    public static final String UNABLE_ASSIGN_GROUP_TO_CATHEDRA = "Unable assign group with id %s to cathedra with id %s";
    public static final String UNABLE_ASSIGN_TEACHER_TO_CATHEDRA = "Unable assign teacher with id %s to cathedra with id %s";
}