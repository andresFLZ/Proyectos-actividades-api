package com.application.rest.persistence;

import com.application.rest.entities.Asignacion;

import java.util.List;
import java.util.Optional;

public interface IAsignacionDAO {

    List<Asignacion> findAll();

    Optional<Asignacion> findById(int id);

    void save(Asignacion asignacion);

    void deleteById(int id);

}
