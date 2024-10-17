package com.application.rest.service.impl;

import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IProyectoDAO;
import com.application.rest.service.IProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements IProyectoService {

    @Autowired
    IProyectoDAO proyectoDAO;

    @Override
    public List<Proyecto> findAll() {
        return proyectoDAO.findAll();
    }

    @Override
    public Optional<Proyecto> findById(int id) {
        return proyectoDAO.findById(id);
    }

    @Override
    public void save(Proyecto proyecto) {
        proyectoDAO.save(proyecto);
    }

    @Override
    public void deleteById(int id) {
        proyectoDAO.deleteById(id);
    }
}
