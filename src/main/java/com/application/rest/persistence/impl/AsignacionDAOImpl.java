package com.application.rest.persistence.impl;

import com.application.rest.entities.Asignacion;
import com.application.rest.persistence.IAsignacionDAO;
import com.application.rest.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AsignacionDAOImpl implements IAsignacionDAO {

    @Autowired
    AsignacionRepository asignacionRepository;

    @Override
    public List<Asignacion> findAll() {
        return (List<Asignacion>) asignacionRepository.findAll();
    }

    @Override
    public Optional<Asignacion> findById(int id) {
        return asignacionRepository.findById(id);
    }

    @Override
    public void save(Asignacion asignacion) {
        asignacionRepository.save(asignacion);
    }

    @Override
    public void deleteById(int id) {
        asignacionRepository.deleteById(id);
    }
}
