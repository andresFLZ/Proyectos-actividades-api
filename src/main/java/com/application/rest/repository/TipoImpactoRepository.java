package com.application.rest.repository;

import com.application.rest.entities.TipoImpacto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoImpactoRepository extends CrudRepository<TipoImpacto, Integer> {
}
