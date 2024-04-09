package kz.aqyl.bookrest.services.impl;

import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.domain.BookEntity;
import kz.aqyl.bookrest.repositories.Bookrepository;
import kz.aqyl.bookrest.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

  private final Bookrepository bookRepository;

  @Autowired
  public BookServiceImpl(final Bookrepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public boolean isBookExists(Book book) {
    return bookRepository.existsById(book.getIsbn());
  }

  @Override
  public Book save(final Book book) {
    final BookEntity bookEntity = bookToBookEntity(book);
    final BookEntity savedBookEntity = bookRepository.save(bookEntity);
    return bookEntityToBook(savedBookEntity);
  }

  @Override
  public Optional<Book> findById(String isbn) {
    final Optional<BookEntity> foundBook = bookRepository.findById(isbn);
    return foundBook.map(book -> bookEntityToBook(book));
  }

  @Override
  public List<Book> listBooks() {
    final List<BookEntity> foundBooks = bookRepository.findAll();
    return foundBooks.stream().map(book -> bookEntityToBook(book))
            .collect(Collectors.toList());//last operation converts stream into a list
  }

  private BookEntity bookToBookEntity(Book book){
    return BookEntity.builder()
            .isbn(book.getIsbn())
            .author(book.getAuthor())
            .title(book.getTitle())
            .build();
  }

  private Book bookEntityToBook(BookEntity bookEntity){
    return Book.builder()
            .isbn(bookEntity.getIsbn())
            .author(bookEntity.getAuthor())
            .title(bookEntity.getTitle())
            .build();
  }

}

