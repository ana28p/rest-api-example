package movie.quotes.example.repository;

import movie.quotes.example.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuoteRepository extends JpaRepository<Quote, Integer> {
}
