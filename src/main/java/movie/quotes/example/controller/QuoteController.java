package movie.quotes.example.controller;

import movie.quotes.example.model.Quote;
import movie.quotes.example.service.quote.IQuoteService;
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
@RequestMapping("api/quote")
public class QuoteController {
    
    private final IQuoteService quoteService;

    @Autowired
    public QuoteController(IQuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * Get Quote entry based on id.
     *
     * @param id the id of the entry as string, only numeric
     * @return ResponseEntity with the retrieved Quote entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Quote> getQuoteById(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id ) {
        return ResponseEntity.ok(quoteService.getById(Integer.parseInt(id)));
    }

    /**
     * Get pageable of all Quote entries in the db.
     *
     * @param pageable
     * @return ResponseEntity with the Page Quote entries in body
     */
    @GetMapping(path = "/allpageable", produces = "application/json")
    public ResponseEntity<Page<Quote>> getQuotes(Pageable pageable) {
        return ResponseEntity.ok(quoteService.getAll(pageable));
    }

    /**
     * Get list of all Quote entries in the db.
     *
     * @return ResponseEntity with the list of entries in body
     */
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<Quote>> getQuotes() {
        List<Quote> quotes = quoteService.getAll();
        if (quotes == null || quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(quotes);
    }

    /**
     * Create a new Quote entry.
     *
     * @param quote entry with filled fields, in json format
     * @return ResponseEntity with the created entry in body
     */
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Quote> createQuote(@Valid @RequestBody Quote quote) {
        Quote created_quote = quoteService.add(quote);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created_quote.getId())
                .toUri();

        return ResponseEntity.created(uri).body(created_quote);
    }

    /**
     * Delete a Quote entry based on id.
     *
     * @param id the id of the entry to be removed
     * @return ResponseEntity with the deleted entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Quote> deleteQuote(
            @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
            @PathVariable(value = "id") String id) {
        return ResponseEntity.ok(quoteService.deleteById(Integer.parseInt(id)));
    }

    /**
     * Update an existing Quote entry.
     *
     * @param quote entry with updated fields, in json format
     * @param id the id of the entry to be updated
     * @return ResponseEntity with the updated entry in body
     * @exception movie.quotes.example.exception.EntityResourceNotFoundException if the entry does not exist
     */
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Quote> updateQuote(@Valid @RequestBody Quote quote,
                                        @Valid @Pattern(regexp = FormatPattern.ID_PATTERN, message = Message.INVALID_ID)
                                        @PathVariable(value = "id") String id ) {
        return ResponseEntity.ok(quoteService.update(quote, Integer.parseInt(id)));
    }
}
