package com.github.googelfist.university.entities.mapper;

import com.github.googelfist.university.entities.dto.rest.CathedraDtoRequest;
import com.github.googelfist.university.entities.dto.rest.CathedraDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.github.googelfist.university.entities.Cathedra;
import com.github.googelfist.university.entities.dto.CathedraDto;

@Mapper
public interface CathedraMapper {
    CathedraMapper INSTANCE = Mappers.getMapper(CathedraMapper.class);

    CathedraDtoResponse toCathedraDtoResponse(Cathedra cathedra);

    Cathedra toCathedraEntity(CathedraDtoRequest cathedraDtoRequest);

    CathedraDto toCathedraDto(Cathedra cathedra);

    Cathedra toCathedraEntity(CathedraDto cathedraDto);
}