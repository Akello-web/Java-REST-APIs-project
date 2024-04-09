package kz.aqyl.bookrest.controllers;

import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(final BookService bookService) {
    this.bookService = bookService;
  }

  @PutMapping(path = "/bookrest/{isbn}")
  public ResponseEntity<Book> createBook(
          @PathVariable final String isbn,
          @RequestBody final Book book){
    book.setIsbn(isbn);
    final Book savedBook = bookService.create(book);
    final ResponseEntity<Book> response = new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
    return response;
  }

  @GetMapping(path = "/bookrest/{isbn}")
  public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn){
    final Optional<Book> foundBook = bookService.findById(isbn);
    return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
            .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/books")
  public ResponseEntity<List<Book>> listBooks(){
    return new ResponseEntity<List<Book>>(bookService.listBooks(), HttpStatus.OK);
  }
}
