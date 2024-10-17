package com.application.rest.persistence;

import com.application.rest.entities.Inconveniente;

import java.util.List;
import java.util.Optional;

public interface IInconvenienteDAO {

    List<Inconveniente> findAll();

    Optional<Inconveniente> findById(int id);

    void save(Inconveniente inconveniente);

    void deleteById(int id);

}
