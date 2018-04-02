package ua.com.novopacksv.production.service;

import java.util.Collection;

public interface BaseEntityService<T extends BaseEntity> {

    T findById(Long id);

    Collection<T> findAll();

    T save(T t);

    void update(T t);

    void delete(Long id);

}
