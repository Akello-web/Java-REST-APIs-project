package kz.aqyl.bookrest.repositories;

import kz.aqyl.bookrest.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bookrepository extends JpaRepository<BookEntity, String> {

}
