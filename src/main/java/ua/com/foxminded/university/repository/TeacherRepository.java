package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Teacher;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Integer> {
    Page<Teacher> findByCathedraId(int cathedraId, Pageable pageable);
}
