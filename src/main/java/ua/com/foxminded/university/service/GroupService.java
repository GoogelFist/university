package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupService extends GenericServiceJpa<Group> {
    Page<Group> getByCathedraId(int cathedraId, Pageable pageable);

    Page<Group> getAll(Pageable pageable);

    List<Group> getByCathedraId(int cathedraId);
}