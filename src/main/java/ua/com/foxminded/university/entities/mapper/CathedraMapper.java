package ua.com.foxminded.university.entities.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.com.foxminded.university.entities.Cathedra;
import ua.com.foxminded.university.entities.dto.CathedraDtoRequest;
import ua.com.foxminded.university.entities.dto.CathedraDtoResponse;

@Mapper
public interface CathedraMapper {
    CathedraMapper INSTANCE = Mappers.getMapper(CathedraMapper.class);

    CathedraDtoResponse toCathedraDtoResponse(Cathedra cathedra);

    Cathedra toCathedraEntity(CathedraDtoRequest cathedraDtoRequest);
}