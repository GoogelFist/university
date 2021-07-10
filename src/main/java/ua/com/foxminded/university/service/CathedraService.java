package ua.com.foxminded.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDto;

public interface CathedraService extends GenericServiceJpa<Cathedra> {
    Page<CathedraDto> getAllDto(Pageable pageable);

    CathedraDto getDtoById(int id);

    void createDto(CathedraDto cathedraDto);

    void updateDto(CathedraDto cathedraDto);
}