package com.application.rest.persistence.impl;

import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.ITipoImpactoDAO;
import com.application.rest.repository.TipoImpactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TipoImpactoDAOImpl implements ITipoImpactoDAO {

    @Autowired
    TipoImpactoRepository tipoImpactoRepository;

    @Override
    public List<TipoImpacto> findAll() {
        return (List<TipoImpacto>) tipoImpactoRepository.findAll();
    }

    @Override
    public Optional<TipoImpacto> findById(int id) {
        return tipoImpactoRepository.findById(id);
    }

    @Override
    public void save(TipoImpacto tipoImpacto) {
        tipoImpactoRepository.save(tipoImpacto);
    }

    @Override
    public void deleteById(int id) {
        tipoImpactoRepository.deleteById(id);
    }
}
