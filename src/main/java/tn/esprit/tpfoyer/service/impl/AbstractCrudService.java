package tn.esprit.tpfoyer.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.service.CrudService;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;

import java.util.List;

@Transactional
public abstract class AbstractCrudService<T> implements CrudService<T> {

    protected abstract JpaRepository<T, Long> repository();

    protected abstract Long getId(T entity);

    protected abstract String entityName();

    protected abstract void validate(T entity);

    protected abstract void copyMutableFields(T existing, T updates);

    @Override
    public T create(T entity) {
        requireEntity(entity);
        if (getId(entity) != null) {
            throw new IllegalArgumentException("A new entity must not have an id");
        }
        validate(entity);
        return repository().save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Long id) {
        return repository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName(), id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository().findAll();
    }

    @Override
    public T update(Long id, T entity) {
        requireEntity(entity);
        T existing = findById(id);
        validate(entity);
        copyMutableFields(existing, entity);
        return repository().save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository().existsById(id)) {
            throw new ResourceNotFoundException(entityName(), id);
        }
        repository().deleteById(id);
    }

    private void requireEntity(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
    }
}
