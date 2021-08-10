package com.github.googelfist.university.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.github.googelfist.university.entities.Cathedra;

public interface CathedraRepository extends PagingAndSortingRepository<Cathedra, Integer> {
}
