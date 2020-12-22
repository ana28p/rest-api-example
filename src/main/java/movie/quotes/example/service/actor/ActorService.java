package movie.quotes.example.service.actor;

import movie.quotes.example.model.Actor;
import movie.quotes.example.model.Quote;
import movie.quotes.example.repository.IActorRepository;
import movie.quotes.example.exception.EntityResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService implements IActorService {

    private final IActorRepository actorRepository;

    @Autowired
    public ActorService(IActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Quote> getQuotesById(int id) {
        return getActorIfExists(id).getQuotes();
    }

    @Override
    public List<Actor> getAll() {
        return actorRepository.findAll();
    }

    @Override
    public Page<Actor> getAll(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    @Override
    public Actor add(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Actor update(Actor actor, int id) {
        Actor existing_actor = getActorIfExists(id);
        existing_actor.setName(actor.getName());
        return actorRepository.save(existing_actor);
    }

    @Override
    public Actor getById(int id) {
        return getActorIfExists(id);
    }

    @Override
    public Actor deleteById(int id) {
        Actor actor = getActorIfExists(id);
        actorRepository.deleteById(id);
        return actor;
    }
    
    private Actor getActorIfExists(int id) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if (actorOptional.isPresent()) {
            return actorOptional.get();
        } else {
            throw new EntityResourceNotFoundException("Actor with id " + id + " not found!");
        }
    }
}
