package movie.quotes.example.service.movie;

import movie.quotes.example.model.Movie;
import movie.quotes.example.model.Quote;
import movie.quotes.example.repository.IMovieRepository;
import movie.quotes.example.exception.EntityResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {

    private final IMovieRepository movieRepository;

    @Autowired
    public MovieService(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Movie add(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie update(Movie movie, int id) {
        Movie existing_movie = getMovieIfExists(id);
        existing_movie.setName(movie.getName());
        return movieRepository.save(existing_movie);
    }

    @Override
    public Movie getById(int id) {
        return getMovieIfExists(id);
    }

    @Override
    public Movie deleteById(int id) {
        Movie movie = getMovieIfExists(id);
        movieRepository.deleteById(id);
        return movie;
    }

    @Override
    public List<Quote> getQuotesById(int id) {
        return getMovieIfExists(id).getQuotes();
    }

    private Movie getMovieIfExists(int id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()) {
            return movieOptional.get();
        } else {
            throw new EntityResourceNotFoundException("Movie with id " + id + " not found!");
        }
    }
}
