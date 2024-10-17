package com.application.rest.persistence.impl;

import com.application.rest.entities.Inconveniente;
import com.application.rest.persistence.IInconvenienteDAO;
import com.application.rest.repository.InconvenienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InconvenienteDAOImpl implements IInconvenienteDAO {

    @Autowired
    InconvenienteRepository inconvenienteRepository;

    @Override
    public List<Inconveniente> findAll() {
        return (List<Inconveniente>) inconvenienteRepository.findAll();
    }

    @Override
    public Optional<Inconveniente> findById(int id) {
        return inconvenienteRepository.findById(id);
    }

    @Override
    public void save(Inconveniente inconveniente) {
        inconvenienteRepository.save(inconveniente);
    }

    @Override
    public void deleteById(int id) {
        inconvenienteRepository.deleteById(id);
    }
}
