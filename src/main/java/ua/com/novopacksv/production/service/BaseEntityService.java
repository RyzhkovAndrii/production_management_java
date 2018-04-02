package ua.com.novopacksv.production.service;

import java.util.List;

public interface BaseEntityService<T extends BaseEntity> {

    T findById(Long id);

    List<T> findAll();

    T save(T t);

    void update(T t);

    void delete(Long id);

}
