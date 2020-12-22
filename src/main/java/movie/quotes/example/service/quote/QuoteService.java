package movie.quotes.example.service.quote;

import movie.quotes.example.model.Actor;
import movie.quotes.example.model.Movie;
import movie.quotes.example.model.Quote;
import movie.quotes.example.repository.IQuoteRepository;
import movie.quotes.example.service.actor.IActorService;
import movie.quotes.example.service.movie.IMovieService;
import movie.quotes.example.exception.EntityResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService implements IQuoteService {

    @Autowired private IQuoteRepository quoteRepository;
    @Autowired private IMovieService movieService;
    @Autowired private IActorService actorService;

    @Override
    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    @Override
    public Page<Quote> getAll(Pageable pageable) {
        return quoteRepository.findAll(pageable);
    }

    @Override
    public Quote add(Quote quote) throws EntityResourceNotFoundException {
        if (quote.getActor() != null) {
            Actor actor = actorService.getById(quote.getActor().getId());
            actor.addQuote(quote);
            quote.setActor(actor);
        }
        if (quote.getMovie() != null) {
            Movie movie = movieService.getById(quote.getMovie().getId());
            movie.addQuote(quote);
            quote.setMovie(movie);
        }
        return quoteRepository.save(quote);
    }

    @Override
    public Quote update(Quote quote, int id) throws EntityResourceNotFoundException {
        Quote existing_quote = getQuoteIfExists(id);
        if (quote.getActor() != null) {
            Actor actor = actorService.getById(quote.getActor().getId());
            existing_quote.setActor(actor);
        }
        if (quote.getMovie() != null) {
            Movie movie = movieService.getById(quote.getMovie().getId());
            existing_quote.setMovie(movie);
        }
        if (quote.getText() != null) {
            existing_quote.setText(quote.getText());
        }
        return quoteRepository.save(existing_quote);
    }

    @Override
    public Quote getById(int id) throws EntityResourceNotFoundException {
        return getQuoteIfExists(id);
    }

    @Override
    public Quote deleteById(int id) throws EntityResourceNotFoundException {
        Quote quote = getQuoteIfExists(id);
        quoteRepository.deleteById(id);
        return quote;
    }

    private Quote getQuoteIfExists(int id) {
        Optional<Quote> quoteOptional = quoteRepository.findById(id);
        if (quoteOptional.isPresent()) {
            return quoteOptional.get();
        } else {
            throw new EntityResourceNotFoundException("Quote with id " + id + " not found!");
        }
    }
}
