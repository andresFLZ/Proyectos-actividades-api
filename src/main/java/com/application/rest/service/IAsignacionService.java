package com.application.rest.service;

import com.application.rest.entities.Asignacion;

import java.util.List;
import java.util.Optional;

public interface IAsignacionService {

    List<Asignacion> findAll();

    Optional<Asignacion> findById(int id);

    void save(Asignacion asignacion);

    void deleteById(int id);

}
