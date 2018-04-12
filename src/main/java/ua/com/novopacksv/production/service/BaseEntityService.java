package ua.com.novopacksv.production.service;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.BaseEntity;

import java.util.List;

public interface BaseEntityService<T extends BaseEntity> {

    T findById(Long id) throws ResourceNotFoundException;

    List<T> findAll();

    T save(T t);

    T update(T t);

    void delete(Long id);

}
