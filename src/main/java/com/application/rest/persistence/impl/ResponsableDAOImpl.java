package com.application.rest.persistence.impl;

import com.application.rest.entities.Responsable;
import com.application.rest.persistence.IResponsableDAO;
import com.application.rest.repository.ResponsableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ResponsableDAOImpl implements IResponsableDAO {

    @Autowired
    ResponsableRepository responsableRepository;

    @Override
    public List<Responsable> findAll() {
        return (List<Responsable>) responsableRepository.findAll();
    }

    @Override
    public Optional<Responsable> findById(int id) {
        return responsableRepository.findById(id);
    }

    @Override
    public void save(Responsable responsable) {
        responsableRepository.save(responsable);
    }

    @Override
    public void deleteById(int id) {
        responsableRepository.deleteById(id);
    }
}
