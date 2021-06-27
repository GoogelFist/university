package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Cathedra;

public interface CathedraService extends GenericServiceJpa<Cathedra> {
    Page<Cathedra> getAll(Pageable pageable);
}