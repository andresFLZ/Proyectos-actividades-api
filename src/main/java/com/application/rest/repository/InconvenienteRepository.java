package com.application.rest.repository;

import com.application.rest.entities.Inconveniente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InconvenienteRepository extends CrudRepository<Inconveniente, Integer> {
}
