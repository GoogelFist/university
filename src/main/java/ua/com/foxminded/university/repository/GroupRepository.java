package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {
    List<Group> findByCathedraId(int cathedraId);

    Page<Group> findByCathedraId(int cathedraId, Pageable pageable);
}
