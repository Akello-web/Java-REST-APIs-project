package kz.aqyl.bookrest.services;

import kz.aqyl.bookrest.domain.Book;

import java.util.Optional;

public interface BookService {
  Book create(Book book);

  Optional<Book> findById(String isbn);
}
