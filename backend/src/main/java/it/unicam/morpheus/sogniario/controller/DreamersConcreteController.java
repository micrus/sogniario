package it.unicam.morpheus.sogniario.controller;

import it.unicam.morpheus.sogniario.checker.DreamerChecker;
import it.unicam.morpheus.sogniario.exception.EntityNotFoundException;
import it.unicam.morpheus.sogniario.exception.IdConflictException;
import it.unicam.morpheus.sogniario.model.Dreamer;
import it.unicam.morpheus.sogniario.repository.DreamersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class DreamersConcreteController implements DreamersController{

    @Autowired
    private DreamersRepository dreamersRepository;

    @Autowired
    private DreamerChecker dreamerChecker;

    @Override
    public Dreamer getInstance(String id) throws EntityNotFoundException {
        return dreamersRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Nessun Dreamer trovato con l'ID: "+id));
    }

    @Override
    public Dreamer create(Dreamer object) throws EntityNotFoundException, IdConflictException {
        if(exists(object.getId())) throw new IdConflictException("Id già presente");
        dreamerChecker.check(object);
        return dreamersRepository.save(object);
    }

    @Override
    public Dreamer update(Dreamer object) throws EntityNotFoundException, IdConflictException {
        if(!exists(object.getId()))
            throw new EntityNotFoundException("Nessun Dreamer con id: "+ object.getId());
        dreamerChecker.check(object);
        return dreamersRepository.save(object);
    }

    @Override
    public boolean delete(String id) {
        // TODO: 16/03/2021 implementare
        return false;
    }

    @Override
    public boolean exists(String id) {
        if(id.isBlank()) throw new IllegalArgumentException("Il campo 'ID' è vuoto");
        return dreamersRepository.existsById(id);
    }
}
