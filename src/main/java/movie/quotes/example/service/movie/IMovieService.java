package movie.quotes.example.service.movie;

import movie.quotes.example.model.Movie;
import movie.quotes.example.model.Quote;
import movie.quotes.example.service.BasicService;

import java.util.List;

public interface IMovieService  extends BasicService<Movie> {

    public List<Quote> getQuotesById(int id);
}
