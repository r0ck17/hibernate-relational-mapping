package by.javaguru.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    Optional<E> findById(K key);
    List<E> findAll();
    E save(E entry);
    E update(E entry);
    void delete(E entry);
}