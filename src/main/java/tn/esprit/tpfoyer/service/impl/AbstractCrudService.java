package tn.esprit.tpfoyer.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpfoyer.exception.ResourceNotFoundException;
import tn.esprit.tpfoyer.service.CrudService;
import tn.esprit.tpfoyer.web.mapper.EntityMapper;

import java.util.List;

@Transactional
public abstract class AbstractCrudService<T, REQ, RES> implements CrudService<REQ, RES> {

    protected abstract JpaRepository<T, Long> repository();

    protected abstract EntityMapper<T, REQ, RES> mapper();

    protected abstract String entityName();

    protected abstract void validate(T entity);

    protected abstract void copyMutableFields(T existing, T updates);

    @Override
    public RES create(REQ request) {
        requireRequest(request);
        T entity = mapper().toEntity(request);
        validate(entity);
        return mapper().toResponse(repository().save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public RES findById(Long id) {
        return mapper().toResponse(findEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RES> findAll() {
        return repository().findAll().stream()
                .map(mapper()::toResponse)
                .toList();
    }

    @Override
    public RES update(Long id, REQ request) {
        requireRequest(request);
        T existing = findEntity(id);
        T updates = mapper().toEntity(request);
        validate(updates);
        copyMutableFields(existing, updates);
        return mapper().toResponse(repository().save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!repository().existsById(id)) {
            throw new ResourceNotFoundException(entityName(), id);
        }
        repository().deleteById(id);
    }

    protected T findEntity(Long id) {
        return repository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName(), id));
    }

    private void requireRequest(REQ request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête ne peut pas être nulle");
        }
    }
}
