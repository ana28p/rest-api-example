package movie.quotes.example.controller;

import movie.quotes.example.model.Movie;
import movie.quotes.example.model.Quote;
import movie.quotes.example.service.movie.IMovieService;
import movie.quotes.example.util.FormatPattern;
import movie.quotes.example.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired private IMovieService movieService;

    /**
     * Get Movie entry based on id.
     *
     * @param id the id of the entry as string, only numeric
     * @return ResponseEntity with the retrieved entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Movie> getMovieById(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id ) {
        return ResponseEntity.ok(movieService.getById(Integer.parseInt(id)));
    }

    /**
     * Get pageable of all Movie entries in the db.
     *
     * @param pageable
     * @return ResponseEntity with the Page Movie entries in body
     */
    @GetMapping(path = "/allpageable", produces = "application/json")
    public ResponseEntity<Page<Movie>> getMovies(Pageable pageable) {
        return ResponseEntity.ok(movieService.getAll(pageable));
    }

    /**
     * Get list of all Movie entries in the db.
     *
     * @return ResponseEntity with the list of entries in body
     */
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.getAll();
        if (movies == null || movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(movies);
    }

    /**
     * Create a new Movie entry.
     *
     * @param movie entry with filled fields, in json format
     * @return ResponseEntity with the created entry in body
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        Movie created_movie = movieService.add(movie);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created_movie.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created_movie);
    }

    /**
     * Delete a Movie entry based on id.
     *
     * @param id the id of the entry to be removed
     * @return ResponseEntity with the deleted entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Movie> deleteMovie(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(movieService.deleteById(Integer.parseInt(id)));
    }

    /**
     * Update an existing Movie entry.
     *
     * @param movie entry with updated fields, in json format
     * @param id the id of the entry to be updated
     * @return ResponseEntity with the updated entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Movie> updateMovie(@Valid @RequestBody Movie movie,
                                             @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
                                             @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(movieService.update(movie, Integer.parseInt(id)));
    }

    /**
     * Get list of all Quote entries for a Movie entry.
     *
     * @param id the id of the Movie entry
     * @return ResponseEntity with the list of Quote entries in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @GetMapping(path = "/{id}/quotes", produces = "application/json")
    public ResponseEntity<List<Quote>> getMovieQuotesById(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id ) {
        List<Quote> movieQuotes = movieService.getQuotesById(Integer.parseInt(id));
        if (movieQuotes == null || movieQuotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(movieQuotes);
    }

}
