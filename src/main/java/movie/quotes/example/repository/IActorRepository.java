package movie.quotes.example.repository;

import movie.quotes.example.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActorRepository extends JpaRepository<Actor,Integer> {
}
