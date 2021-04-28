package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.TeacherDAO;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherDAO teacherDAO;

    @Autowired
    public TeacherServiceImpl(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public void create(Teacher teacher) {
        teacherDAO.create(teacher);
    }

    @Override
    public Teacher getById(int id) {
        return teacherDAO.getById(id);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherDAO.getAll();
    }

    @Override
    public void update(int id, Teacher teacher) {
        teacherDAO.update(id, teacher);
    }

    @Override
    public void delete(int id) {
        teacherDAO.delete(id);
    }

    @Override
    public List<Teacher> getByCathedraId(int cathedraId) {
        return teacherDAO.getByCathedraId(cathedraId);
    }

    @Override
    public void assignToCathedra(int cathedraId, int teacherId) {
        teacherDAO.assignToCathedra(cathedraId, teacherId);
    }

    @Override
    public void updateAssignment(int cathedraId, int teacherId) {
        teacherDAO.updateAssignment(cathedraId, teacherId);
    }

    @Override
    public void deleteAssignment(int teacherId) {
        teacherDAO.deleteAssignment(teacherId);
    }
}