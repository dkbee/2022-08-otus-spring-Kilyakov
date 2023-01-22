package ru.otus.spring.kilyakov.controller.reactive;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.kilyakov.dto.BookDto;
import ru.otus.spring.kilyakov.repository.BookRepository;

@RestController()
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookReactiveController {

    private final BookRepository bookRepository;

    @GetMapping
    public Flux<BookDto> list() {
        return bookRepository.findAll().map(BookDto::toDto);
    }

    @GetMapping("/{id}")
    public Mono<BookDto> list(@PathVariable String id) {
        return bookRepository.findById(id).map(BookDto::toDto);
    }

    @PostMapping
    public Mono<BookDto> add(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookDto.toDomainObject()).map(BookDto::toDto);
    }

    @PutMapping
    public Mono<BookDto> edit(@RequestBody BookDto bookDto) {
        return bookRepository.save(bookDto.toDomainObject()).map(BookDto::toDto);
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> deleteBook(@PathVariable String id) {
        return bookRepository.deleteById(id).then(ServerResponse.ok().build());
    }

}
