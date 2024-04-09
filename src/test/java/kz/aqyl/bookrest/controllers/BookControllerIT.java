package kz.aqyl.bookrest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.aqyl.bookrest.TestData;
import kz.aqyl.bookrest.domain.Book;
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

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIT {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testThatBookIsCreated() throws Exception{
    final Book book = TestData.testBook();
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

}
