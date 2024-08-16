package com.galvanize.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenresServiceTests {

    private GenresService genresService;

    @Mock
    GenresRepository genresRepository;

    @BeforeEach
    void setUp() { genresService = new GenresService(genresRepository); }

    @Test
    void getGenresListNoArgumentsReturnsList() {
        Genre genre = new Genre("Documentary");
        when(genresRepository.findAll())
                .thenReturn(Arrays.asList(genre));
        GenresList genresList = genresService.getGenresList();
        assertThat(genresList).isNotNull();
        assertThat(genresList.isEmpty()).isFalse();
    }

    @Test
    void getGenresWithNameParamsReturnsName() {
        Genre genre = new Genre("Mystery");
        when(genresRepository.findByName(anyString()))
                .thenReturn(Optional.of(genre));
        String name = String.valueOf(genresService.getGenre("Mystery"));
        assertThat(name).isNotNull();
        assertThat(name).isNotEmpty();
    }

    @Test
    void addGenreValidReturnsGenre () {
        Genre genre = new Genre("Science Fiction");
        when(genresRepository.save(any(Genre.class)))
                .thenReturn(genre);
        Genre genre1 = genresService.addGenre(genre);
        assertThat(genre1).isNotNull();
        assertThat(genre1.getName()).isEqualTo("Science Fiction");
    }

//    @Test
//    void updateGenrePatchReturnsGenre () {
//        Genre genre = new Genre("Kaiju");
//        when(genresRepository.findByGenre(anyString()))
//                .thenReturn(Optional.of(genre));
//        when(genresRepository.save(any(Genre.class)))
//                .thenReturn(genre);
//        Genre genre1 = genresService.updateGenre(genre.getName(), "Kaiju");
//        assertThat(genre1).isNotNull();
//        assertThat(genre1.getName()).isEqualTo(genre.getName());
//    }

    @Test
    void deleteGenreByName () {
        Genre genre = new Genre("WallEye");
        when(genresRepository.findByName(anyString()))
                .thenReturn(Optional.of(genre));
        genresService.deleteGenre(genre.getName());
        verify(genresRepository).delete(any(Genre.class));
    }

    @Test
    void deleteGenreByNameNotExists () {
        when(genresRepository.findByName(anyString()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(GenreNotFoundException.class)
                .isThrownBy(() -> { genresService.deleteGenre("NOTEXISTS-NAME"); });
    }
}
