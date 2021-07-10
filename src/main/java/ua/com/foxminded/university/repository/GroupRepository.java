package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Group;

public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {
    Page<Group> findByCathedraId(int cathedraId, Pageable pageable);
}