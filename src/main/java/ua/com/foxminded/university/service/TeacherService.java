package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

public interface TeacherService extends GenericServiceJpa<Teacher> {
    Page<Teacher> getAll(Pageable pageable);

    List<Teacher> getByCathedraId(int cathedraId);

    Page<Teacher> getByCathedraId(int cathedraId, Pageable pageable);
}