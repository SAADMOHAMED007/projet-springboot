package tn.esprit.tpfoyer.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tn.esprit.tpfoyer.service.CrudService;

import java.util.List;

public abstract class AbstractCrudController<T> {

    private final CrudService<T> service;

    protected AbstractCrudController(CrudService<T> service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @GetMapping
    public List<T> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public T findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable Long id, @RequestBody T entity) {
        service.findById(id);
        setId(entity, id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    protected abstract void setId(T entity, Long id);
}
