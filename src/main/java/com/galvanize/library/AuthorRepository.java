package com.galvanize.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameAndBirthYear(String name, int birthYear);

    Optional<Author> findByName(String name);
}
