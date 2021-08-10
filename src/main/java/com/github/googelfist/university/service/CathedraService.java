package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.entities.dto.CathedraDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CathedraService extends GenericServiceJpa<Cathedra> {
    Page<CathedraDto> getAllDto(Pageable pageable);

    CathedraDto getDtoById(int id);

    void createDto(CathedraDto cathedraDto);

    void updateDto(CathedraDto cathedraDto);
}