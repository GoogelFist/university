package ua.com.foxminded.university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CathedraDAO;
import ua.com.foxminded.university.dao.exceptions.DaoException;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Teacher;
import ua.com.foxminded.university.service.exceptions.ServiceException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CathedraServiceImpl implements CathedraService {
    private final CathedraDAO cathedraDAO;
    private final GroupService groupService;
    private final TeacherService teacherService;

    private static final Logger logger = LoggerFactory.getLogger(CathedraServiceImpl.class);

    @Autowired
    public CathedraServiceImpl(CathedraDAO cathedraDAO, GroupService groupService, TeacherService teacherService) {
        this.cathedraDAO = cathedraDAO;
        this.groupService = groupService;
        this.teacherService = teacherService;
    }

    @Override
    public void create(Cathedra cathedra) {
        logger.debug("CathedraService calls cathedraDAO.create({})", cathedra);
        try {
            cathedraDAO.create(cathedra);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Cathedra getById(int id) {
        logger.debug("CathedraService calls cathedraDAO.getById(id {})", id);
        Cathedra cathedraById;
        try {
            cathedraById = cathedraDAO.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return cathedraById;
    }

    @Override
    public List<Cathedra> getAll() {
        logger.debug("CathedraService calls cathedraDAO.getAll()");
        List<Cathedra> cathedras;
        try {
            cathedras = cathedraDAO.getAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        return cathedras;
    }

    @Override
    public Page<Cathedra> getAll(Pageable pageable) {
        logger.debug("StudentService calls cathedraService.getAll({})", pageable);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Cathedra> cathedras;
        List<Cathedra> list;
        try {
            cathedras = cathedraDAO.getAll();
            cathedras.sort(Comparator.comparing(Cathedra::getId));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        if (cathedras.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, cathedras.size());
            list = cathedras.subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), cathedras.size());
    }

    @Override
    public void update(int id, Cathedra cathedra) {
        logger.debug("CathedraService calls cathedraDAO.update(id {}, {})", id, cathedra);
        try {
            cathedraDAO.update(id, cathedra);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        logger.debug("CathedraService calls cathedraDAO.delete(id {})", id);
        try {
            cathedraDAO.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private void setGroupsInCathedra(Cathedra cathedra) {
        logger.debug("Call cathedra.getId()");
        int cathedraId = cathedra.getId();

        logger.debug("Call groupService.getByCathedraId(id {})", cathedraId);
        List<Group> groupsByCathedraId = groupService.getByCathedraId(cathedraId);

        logger.debug("Set {} to the {}", groupsByCathedraId, cathedra);
        cathedra.setGroups(groupsByCathedraId);
    }

    private void setTeachersInCathedra(Cathedra cathedra) {
        logger.debug("Call cathedra.getId()");
        int cathedraId = cathedra.getId();

        logger.debug("Call teacherService.getByCathedraId(id {})", cathedraId);
        List<Teacher> teachersByCathedraId = teacherService.getByCathedraId(cathedraId);

        logger.debug("Set {} to the {}", teachersByCathedraId, cathedra);
        cathedra.setTeachers(teachersByCathedraId);
    }
}