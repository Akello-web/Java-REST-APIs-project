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

}
