package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.Student;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void create(Student student) {
        studentDAO.create(student);
    }

    @Override
    public Student getById(int id) {
        return studentDAO.getById(id);
    }

    @Override
    public List<Student> getAll() {
        return studentDAO.getAll();
    }

    @Override
    public void update(int id, Student student) {
        studentDAO.update(id, student);
    }

    @Override
    public void delete(int id) {
        studentDAO.delete(id);
    }

    @Override
    public List<Student> getByGroupId(int groupId) {
        return studentDAO.getByGroupId(groupId);
    }

    @Override
    public void assignToGroup(int groupId, int studentId) {
        studentDAO.assignToGroup(groupId, studentId);
    }

    @Override
    public void updateAssignment(int groupId, int studentId) {
        studentDAO.updateAssignment(groupId, studentId);
    }

    @Override
    public void deleteAssignment(int studentId) {
        studentDAO.deleteAssignment(studentId);
    }
}