package ua.com.foxminded.university.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.com.foxminded.university.entities.Cathedra;

public interface CathedraRepository extends PagingAndSortingRepository<Cathedra, Integer> {
}
