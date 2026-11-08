package tn.esprit.tpfoyer.service;

import java.util.List;

/**
 * Generic CRUD contract expressed with DTOs: entities never cross the service boundary.
 *
 * @param <REQ> request DTO type
 * @param <RES> response DTO type
 */
public interface CrudService<REQ, RES> {

    RES create(REQ request);

    List<RES> findAll();

    RES findById(Long id);

    RES update(Long id, REQ request);

    void deleteById(Long id);
}
