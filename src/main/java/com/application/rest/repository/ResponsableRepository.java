package com.application.rest.repository;

import com.application.rest.entities.Responsable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableRepository extends CrudRepository<Responsable, Integer> {
}
