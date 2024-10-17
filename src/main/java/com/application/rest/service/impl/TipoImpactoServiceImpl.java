package com.application.rest.service.impl;

import com.application.rest.entities.TipoImpacto;
import com.application.rest.persistence.ITipoImpactoDAO;
import com.application.rest.service.ITipoImpactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoImpactoServiceImpl implements ITipoImpactoService {

    @Autowired
    ITipoImpactoDAO tipoImpactoDAO;

    @Override
    public List<TipoImpacto> findAll() {
        return tipoImpactoDAO.findAll();
    }

    @Override
    public Optional<TipoImpacto> findById(int id) {
        return tipoImpactoDAO.findById(id);
    }

    @Override
    public void save(TipoImpacto tipoImpacto) {
        tipoImpactoDAO.save(tipoImpacto);
    }

    @Override
    public void deleteById(int id) {
        tipoImpactoDAO.deleteById(id);
    }
}
