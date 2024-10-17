package com.application.rest.service.impl;

import com.application.rest.entities.Inconveniente;
import com.application.rest.persistence.IInconvenienteDAO;
import com.application.rest.service.IInconvenienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InconvenienteServiceImpl implements IInconvenienteService {

    @Autowired
    IInconvenienteDAO inconvenienteDAO;

    @Override
    public List<Inconveniente> findAll() {
        return inconvenienteDAO.findAll();
    }

    @Override
    public Optional<Inconveniente> findById(int id) {
        return inconvenienteDAO.findById(id);
    }

    @Override
    public void save(Inconveniente inconveniente) {
        inconvenienteDAO.save(inconveniente);
    }

    @Override
    public void deleteById(int id) {
        inconvenienteDAO.deleteById(id);
    }
}
