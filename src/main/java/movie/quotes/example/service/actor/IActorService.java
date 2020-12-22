package movie.quotes.example.service.actor;

import movie.quotes.example.model.Actor;
import movie.quotes.example.model.Quote;
import movie.quotes.example.service.BasicService;

import java.util.List;

public interface IActorService extends BasicService<Actor> {

    public List<Quote> getQuotesById(int id);
}
