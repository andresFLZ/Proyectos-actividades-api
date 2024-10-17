package com.application.rest.persistence.impl;

import com.application.rest.entities.Proyecto;
import com.application.rest.persistence.IProyectoDAO;
import com.application.rest.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProyectoDAOImpl implements IProyectoDAO {

    @Autowired
    ProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> findAll() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    @Override
    public Optional<Proyecto> findById(int id) {
        return proyectoRepository.findById(id);
    }

    @Override
    public void save(Proyecto proyecto) {
        proyectoRepository.save(proyecto);
    }

    @Override
    public void deleteById(int id) {
        proyectoRepository.deleteById(id);
    }
}
