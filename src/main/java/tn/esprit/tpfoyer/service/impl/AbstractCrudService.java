package tn.esprit.tpfoyer.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.tpfoyer.service.CrudService;

import java.util.List;

@Transactional
public abstract class AbstractCrudService<T> implements CrudService<T> {

    private final JpaRepository<T, Long> repository;

    protected AbstractCrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Entity not found: " + id));
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(findById(id));
    }
}
