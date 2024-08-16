package com.galvanize.library;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
