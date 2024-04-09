package kz.aqyl.bookrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.aqyl.bookrest.domain.Book;
import kz.aqyl.bookrest.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static kz.aqyl.bookrest.TestData.testBook;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private BookService bookService;

  @Test
  public void testThatBookIsCreated() throws Exception{
    final Book book = testBook();
    final ObjectMapper objectMapper = new ObjectMapper();
    final String bookJson = objectMapper.writeValueAsString(book);

    mockMvc.perform(MockMvcRequestBuilders.put("/bookrest/" + book.getIsbn())
            .contentType(MediaType.APPLICATION_JSON)
            .content(bookJson))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
  }

  @Test
  public void testThatRetrieveReturns404WhenNoBook() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/bookrest/123123123"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatRetrieveReturns200WhenExists() throws Exception{
    final Book book = testBook();
    bookService.create(book);
    mockMvc.perform(MockMvcRequestBuilders.get("/bookrest/" + book.getIsbn()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
  }

  @Test
  public void testThatListBooksReturns200EmptyWhenNoBooks() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  public void testThatListBooksReturns200BooksWhenExists() throws Exception{
    final Book book = testBook();
    bookService.create(book);


    mockMvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].isbn").value(book.getIsbn()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(book.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(book.getAuthor()));
  }
}
