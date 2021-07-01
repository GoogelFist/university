package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Teacher;

import java.util.List;

public interface TeacherRepository extends PagingAndSortingRepository<Teacher, Integer> {
    List<Teacher> findByCathedraId(int cathedraId);

    Page<Teacher> findByCathedraId(int cathedraId, Pageable pageable);
}
