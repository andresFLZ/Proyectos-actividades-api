package com.application.rest.service.impl;

import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IResponsableDAO;
import com.application.rest.service.IResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsableServiceImpl implements IResponsableService {

    @Autowired
    IResponsableDAO responsableDAO;

    @Override
    public List<Responsable> findAll() {
        return responsableDAO.findAll();
    }

    @Override
    public Optional<Responsable> findById(int id) {
        return responsableDAO.findById(id);
    }

    @Override
    public void save(Responsable responsable) {
        responsableDAO.save(responsable);
    }

    @Override
    public void deleteById(int id) {
        responsableDAO.deleteById(id);
    }
}
