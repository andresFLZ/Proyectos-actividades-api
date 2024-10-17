package com.application.rest.repository;

import com.application.rest.entities.Asignacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends CrudRepository<Asignacion, Integer> {
}
