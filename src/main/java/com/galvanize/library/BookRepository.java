package com.galvanize.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainsAndPublicationYear(String Title, int year);

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);
}
