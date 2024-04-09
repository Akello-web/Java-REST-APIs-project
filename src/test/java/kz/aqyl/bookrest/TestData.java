package kz.aqyl.bookrest;

import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.domain.BookEntity;

public final class TestData {
  private TestData(){

  }

  public static Book testBook(){
    return Book.builder()
            .isbn("0123456")
            .author("SomeGenuis")
            .title("How to be a Genuis")
            .build();
  }

  public static BookEntity testBookEntity(){
    return BookEntity.builder()
            .isbn("0123456")
            .author("SomeGenuis")
            .title("How to be a Genuis")
            .build();
  }
}
