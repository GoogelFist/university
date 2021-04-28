package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

@Service
public class CathedraServiceImpl implements CathedraService {
    private final CathedraDAO cathedraDAO;
    private final GroupService groupService;
    private final TeacherService teacherService;

    @Autowired
    public CathedraServiceImpl(CathedraDAO cathedraDAO, GroupService groupService, TeacherService teacherService) {
        this.cathedraDAO = cathedraDAO;
        this.groupService = groupService;
        this.teacherService = teacherService;
    }

    @Override
    public void create(Cathedra cathedra) {
        cathedraDAO.create(cathedra);
    }

    @Override
    public Cathedra getById(int id) {
        Cathedra cathedraById = cathedraDAO.getById(id);
        setGroupsInCathedra(cathedraById);
        setTeachersInCathedra(cathedraById);
        return cathedraById;
    }

    @Override
    public List<Cathedra> getAll() {
        List<Cathedra> cathedras = cathedraDAO.getAll();
        setGroupsAndTeachersInCathedras(cathedras);
        return cathedras;
    }

    @Override
    public void update(int id, Cathedra cathedra) {
        cathedraDAO.update(id, cathedra);
    }

    @Override
    public void delete(int id) {
        cathedraDAO.delete(id);
    }

    private void setGroupsInCathedra(Cathedra cathedra) {
        int cathedraId = cathedra.getId();
        List<Group> groupsByCathedraId = groupService.getByCathedraId(cathedraId);
        cathedra.setGroups(groupsByCathedraId);
    }

    private void setTeachersInCathedra(Cathedra cathedra) {
        int id = cathedra.getId();
        List<Teacher> teachersByCathedraId = teacherService.getByCathedraId(id);
        cathedra.setTeachers(teachersByCathedraId);
    }

    private void setGroupsAndTeachersInCathedras(List<Cathedra> cathedras) {
        cathedras.forEach(cathedra -> {
            setGroupsInCathedra(cathedra);
            setTeachersInCathedra(cathedra);
        });
    }
}