package com.application.rest.persistence;

import com.application.rest.entities.Actividad;

import java.util.List;
import java.util.Optional;

public interface IActividadDAO {

    List<Actividad> findAll();

    Optional<Actividad> findById(int id);

    void save(Actividad actividad);

    void deleteById(int id);

}
