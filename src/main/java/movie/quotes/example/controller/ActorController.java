package movie.quotes.example.controller;

import movie.quotes.example.model.Actor;
import movie.quotes.example.model.Quote;
import movie.quotes.example.service.actor.IActorService;
import movie.quotes.example.util.FormatPattern;
import movie.quotes.example.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/actor")
public class ActorController {

    private IActorService actorService;

    @Autowired
    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }

    /**
     * Get Actor entry based on id.
     *
     * @param id the id of the entry as string, only numeric
     * @return ResponseEntity with the retrieved Actor entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Actor> getActorById(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id ) {
        return ResponseEntity.ok(actorService.getById(Integer.parseInt(id)));
    }

    /**
     * Get pageable of all Actor entries in the db.
     *
     * @param pageable
     * @return ResponseEntity with the Page Actor entries in body
     */
    @GetMapping(path = "/allpageable", produces = "application/json")
    public ResponseEntity<Page<Actor>> getActors(Pageable pageable) {
        return ResponseEntity.ok(actorService.getAll(pageable));
    }

    /**
     * Get list of all Actor entries in the db.
     *
     * @return ResponseEntity with the list of Actor entries in body
     */
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<Actor>> getActors() {
        List<Actor> actors = actorService.getAll();
        if (actors == null || actors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(actors);
    }

    /**
     * Create a new Actor entry.
     *
     * @param actor entry with filled fields, in json format
     * @return ResponseEntity with the created entry in body
     */
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Actor> createActor(@Valid @RequestBody Actor actor) {
        Actor created_actor = actorService.add(actor);
        return new ResponseEntity<>(created_actor, HttpStatus.CREATED);
    }

    /**
     * Delete an Actor entry based on id.
     *
     * @param id the id of the entry to be removed
     * @return ResponseEntity with the deleted entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Actor> deleteActor(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id) {
        Actor deletedActor = actorService.deleteById(Integer.parseInt(id));
        return ResponseEntity.ok(deletedActor);
    }

    /**
     * Update an existing Actor entry.
     *
     * @param actor entry with updated fields, in json format
     * @param id the id of the entry to be updated
     * @return ResponseEntity with the updated entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Actor> updateActor(@Valid @RequestBody Actor actor,
                                              @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
                                              @PathVariable(value = "id") String id ) {
        Actor newactor = actorService.update(actor, Integer.parseInt(id));
        return ResponseEntity.ok(newactor);
    }

    /**
     * Get list of all Quote entries for an Actor entry.
     *
     * @param id the id of the Actor entry
     * @return ResponseEntity with the list of Quote entries in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @GetMapping(path = "/{id}/quotes", produces = "application/json")
    public ResponseEntity<List<Quote>> getActorQuotesById(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id) {
        List<Quote> actorQuotes = actorService.getQuotesById(Integer.parseInt(id));
        if (actorQuotes == null || actorQuotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(actorQuotes);
    }

}
