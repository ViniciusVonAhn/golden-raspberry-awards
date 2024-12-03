package com.golden.raspberry.awards.mappers;

import com.golden.raspberry.awards.dtos.MovieDto;
import com.golden.raspberry.awards.models.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "winner", expression = "java(dto.getWinner() != null && dto.getWinner().equalsIgnoreCase(\"yes\"))")
    Movie toEntity(MovieDto dto);
}

