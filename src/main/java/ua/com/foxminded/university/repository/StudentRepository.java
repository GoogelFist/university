package ua.com.foxminded.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Integer> {
    Page<Student> findByGroupId(int groupId, Pageable pageable);
}