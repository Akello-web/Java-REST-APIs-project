package kz.aqyl.bookrest.services.impl;

import kz.aqyl.bookrest.TestData;
import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.domain.BookEntity;
import kz.aqyl.bookrest.repositories.Bookrepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static kz.aqyl.bookrest.TestData.testBook;
import static kz.aqyl.bookrest.TestData.testBookEntity;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

  @Mock
  private Bookrepository bookRepository;

  @InjectMocks
  private BookServiceImpl underTest;

  @Test
  public void testThatBookIsSaved(){
    final Book book = testBook();

    final BookEntity bookEntity = testBookEntity();

    when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

    final Book result = underTest.create(book);
    Assertions.assertEquals(book, result);

  }

  @Test
  public void testThatFindByIdReturnsEmptyWhenNoBook(){
    final String isbn = "123123123";
    when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());
    final Optional<Book> result = underTest.findById(isbn);
    Assertions.assertEquals(Optional.empty(), result);
  }

  @Test
  public void testThatFindByIdReturnsBookWhenExists(){
    final Book book = testBook();
    final BookEntity bookEntity = testBookEntity();
    when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));
    final Optional<Book> result = underTest.findById(book.getIsbn());
    Assertions.assertEquals(Optional.of(book), result);
  }
}
