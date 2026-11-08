package tn.esprit.tpfoyer.web.mapper;

/**
 * Converts between a JPA entity and its API request/response DTOs.
 *
 * @param <T>   entity type
 * @param <REQ> request DTO type
 * @param <RES> response DTO type
 */
public interface EntityMapper<T, REQ, RES> {

    T toEntity(REQ request);

    RES toResponse(T entity);
}
