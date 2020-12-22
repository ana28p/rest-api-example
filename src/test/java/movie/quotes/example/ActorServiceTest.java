package movie.quotes.example;

import movie.quotes.example.controller.QuoteController;
import movie.quotes.example.model.Actor;
import movie.quotes.example.model.Quote;
import movie.quotes.example.repository.IActorRepository;
import movie.quotes.example.service.actor.ActorService;
import movie.quotes.example.service.actor.IActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    private final IActorRepository actorRepository = mock(IActorRepository.class);
    private IActorService actorService;

    @BeforeEach
    void initUseCase() {
        actorService = new ActorService(actorRepository);
    }

    @Test
    void getQuotesById() {
        Actor a = new Actor();
        int id = 1;
        a.setId(id);
        a.setName("Brad");

        Quote q = new Quote();
        q.setId(1);
        q.setText("To be or not to be");
        q.setActor(a);
        a.addQuote(q);

        when(actorRepository.findById(id)).thenReturn(Optional.of(a));

        List<Quote> quotes = actorService.getQuotesById(id);

        verify(actorRepository).findById(id);

        assertEquals(1, quotes.get(0).getId());
        assertEquals(id, quotes.get(0).getActor().getId());
        assertEquals("Brad", quotes.get(0).getActor().getName());
    }

    @Test
    void getAll() {
        List<Actor> al = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Actor a = new Actor();
            a.setId(i);
            al.add(a);
        }
        when(actorRepository.findAll()).thenReturn(al);

        List<Actor> actorList = actorService.getAll();

        verify(actorRepository).findAll();

        assertEquals(3, actorList.size());
        for (int i = 1; i <= 3; i++) {
            assertEquals(i, actorList.get(i-1).getId());
        }
    }

    @Test
    void add() {
        Actor a = new Actor();
        a.setId(1);
        a.setName("Brad");

        when(actorRepository.save(a)).thenReturn(a);

        Actor actor = actorService.add(a);

        verify(actorRepository).save(a);

        assertEquals(1, actor.getId());
        assertEquals("Brad", actor.getName());
    }

    @Test
    void update() {
        Actor a = new Actor();
        int id = 1;
        a.setId(id);
        a.setName("Brad");

        when(actorRepository.findById(id)).thenReturn(Optional.of(a));

        a.setName("New Brad");
        when(actorRepository.save(a)).thenReturn(a);

        Actor actor = actorService.update(a, id);

        verify(actorRepository).findById(id);
        verify(actorRepository).save(a);

        assertEquals(1, actor.getId());
        assertEquals("New Brad", actor.getName());
    }

    @Test
    void getById() {
        Actor a = new Actor();
        int id = 1;
        a.setId(id);
        a.setName("Brad");
        when(actorRepository.findById(id)).thenReturn(Optional.of(a));

        Actor actor = actorService.getById(id);

        verify(actorRepository).findById(id);

        assertEquals(id, actor.getId());
        assertEquals("Brad", actor.getName());
    }

    @Test
    void deleteById() {
        Actor a = new Actor();
        int id = 1;
        a.setId(id);
        a.setName("Brad");
        when(actorRepository.findById(id)).thenReturn(Optional.of(a));

        Actor actor = actorService.deleteById(id);

        verify(actorRepository).deleteById(id);

        assertEquals("Brad", actor.getName());
    }
}