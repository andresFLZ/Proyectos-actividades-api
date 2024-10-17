package com.application.rest.service;

import com.application.rest.entities.Inconveniente;

import java.util.List;
import java.util.Optional;

public interface IInconvenienteService {

    List<Inconveniente> findAll();

    Optional<Inconveniente> findById(int id);

    void save(Inconveniente inconveniente);

    void deleteById(int id);

}
