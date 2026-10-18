package tn.esprit.tpfoyer.service;

import java.util.List;

public interface CrudService<T> {

    T create(T entity);

    List<T> findAll();

    T findById(Long id);

    T update(Long id, T entity);

    void deleteById(Long id);
}
