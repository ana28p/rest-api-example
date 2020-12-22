package movie.quotes.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BasicService<T> {

    List<T> getAll();
    Page<T> getAll(Pageable pageable);
    T add(T obj);
    T update(T obj, int id);
    T getById(int id);
    T deleteById(int id);

}
