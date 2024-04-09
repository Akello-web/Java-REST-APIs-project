package kz.aqyl.bookrest.services.impl;

import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.domain.BookEntity;
import kz.aqyl.bookrest.repositories.Bookrepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kz.aqyl.bookrest.TestData.testBook;
import static kz.aqyl.bookrest.TestData.testBookEntity;
import static org.mockito.ArgumentMatchers.any;
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

    final Book result = underTest.save(book);
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

  @Test
  public void testListBooksReturnsEmptyWhenNoBook(){
    when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
    final List<Book> result = underTest.listBooks();
    Assertions.assertEquals(0, result.size());
  }

  @Test
  public void testListBooksReturnBookListWhenExists(){
    final BookEntity bookEntity = testBookEntity();
    when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
    final List<Book> result = underTest.listBooks();
    Assertions.assertEquals(1, result.size());
  }

  @Test
  public void testIsBookExistsReturnsFalseWhenNoBook(){
    when(bookRepository.existsById(any())).thenReturn(false);
    final boolean result = underTest.isBookExists(testBook());
    Assertions.assertFalse(result);
  }

  @Test
  public void testIsBookExistsReturnsTrueWhenBookExists(){
    when(bookRepository.existsById(any())).thenReturn(true);
    final boolean result = underTest.isBookExists(testBook());
    Assertions.assertTrue(result);
  }

}
