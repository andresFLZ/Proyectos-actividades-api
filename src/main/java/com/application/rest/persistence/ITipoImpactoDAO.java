package com.application.rest.persistence;

import com.application.rest.entities.TipoImpacto;

import java.util.List;
import java.util.Optional;

public interface ITipoImpactoDAO {

    List<TipoImpacto> findAll();

    Optional<TipoImpacto> findById(int id);

    void save(TipoImpacto tipoImpacto);

    void deleteById(int id);

}
