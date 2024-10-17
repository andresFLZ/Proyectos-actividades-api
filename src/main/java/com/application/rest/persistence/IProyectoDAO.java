package com.application.rest.persistence;

import com.application.rest.entities.Proyecto;

import java.util.List;
import java.util.Optional;

public interface IProyectoDAO {

    List<Proyecto> findAll();

    Optional<Proyecto> findById(int id);

    void save(Proyecto proyecto);

    void deleteById(int id);

}
