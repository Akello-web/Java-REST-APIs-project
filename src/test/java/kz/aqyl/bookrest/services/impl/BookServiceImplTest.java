package kz.aqyl.bookrest.services.impl;

import kz.aqyl.bookrest.repositories.Bookrepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BookServiceImplTest {

  @Mock
  private Bookrepository bookrepository;

  @InjectMocks
  private BookServiceImpl underTest;

}
