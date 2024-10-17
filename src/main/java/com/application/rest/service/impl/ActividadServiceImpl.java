package com.application.rest.service.impl;

import com.application.rest.entities.Actividad;
import com.application.rest.persistence.IActividadDAO;
import com.application.rest.service.IActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadServiceImpl implements IActividadService {

    @Autowired
    IActividadDAO actividadDAO;

    @Override
    public List<Actividad> findAll() {
        return actividadDAO.findAll();
    }

    @Override
    public Optional<Actividad> findById(int id) {
        return actividadDAO.findById(id);
    }

    @Override
    public void save(Actividad actividad) {
        actividadDAO.save(actividad);
    }

    @Override
    public void deleteById(int id) {
        actividadDAO.deleteById(id);
    }
}
