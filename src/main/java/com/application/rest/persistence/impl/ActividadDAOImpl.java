package com.application.rest.persistence.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.persistence.IActividadDAO;
import com.application.rest.repository.ActividadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ActividadDAOImpl implements IActividadDAO {

    @Autowired
    ActividadRepository actividadRepository;

    @Override
    public List<Actividad> findAll() {
        return (List<Actividad>) actividadRepository.findAll();
    }

    @Override
    public Optional<Actividad> findById(int id) {
        return actividadRepository.findById(id);
    }

    @Override
    public void save(Actividad actividad) {
        actividadRepository.save(actividad);
    }

    @Override
    public void deleteById(int id) {
        actividadRepository.deleteById(id);
    }
}
