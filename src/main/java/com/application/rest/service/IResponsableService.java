package com.application.rest.service;

import com.application.rest.entities.Responsable;

import java.util.List;
import java.util.Optional;

public interface IResponsableService {

    List<Responsable> findAll();

    Optional<Responsable> findById(int id);

    void save(Responsable responsable);

    void deleteById(int id);

}
