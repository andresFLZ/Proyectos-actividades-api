package com.application.rest.service.impl;

import com.application.rest.entities.Asignacion;
import com.application.rest.persistence.IAsignacionDAO;
import com.application.rest.service.IAsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionServiceImpl implements IAsignacionService {

    @Autowired
    IAsignacionDAO asignacionDAO;

    @Override
    public List<Asignacion> findAll() {
        return asignacionDAO.findAll();
    }

    @Override
    public Optional<Asignacion> findById(int id) {
        return asignacionDAO.findById(id);
    }

    @Override
    public void save(Asignacion asignacion) {
        asignacionDAO.save(asignacion);
    }

    @Override
    public void deleteById(int id) {
        asignacionDAO.deleteById(id);
    }
}
