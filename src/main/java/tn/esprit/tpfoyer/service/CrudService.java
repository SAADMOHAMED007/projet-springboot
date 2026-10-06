package tn.esprit.tpfoyer.service;

import java.util.List;

public interface CrudService<T> {

    T save(T entity);

    List<T> findAll();

    T findById(Long id);

    void deleteById(Long id);
}
