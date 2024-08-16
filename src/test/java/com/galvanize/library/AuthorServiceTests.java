package com.galvanize.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {

    private AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    @BeforeEach
    void setUp() { authorService = new AuthorService(authorRepository); }

    @Test
    void getAuthorListNoParamsReturnsList() {
        Author author = new Author("Clive Barker", 1950);
        when(authorRepository.findAll())
                .thenReturn(Arrays.asList(author));
        AuthorList authorList = authorService.getAuthorList();
        assertThat(authorList).isNotNull();
        assertThat(authorList.isEmpty()).isFalse();
    }

    @Test
    void getAuthorWithNameBirthYearParamReturnsList() {
        Author author = new Author("Clive Barker", 1950);
        when(authorRepository.findByNameAndBirthYear(anyString(), anyInt()))
                .thenReturn(Arrays.asList(author));
        AuthorList authorList = authorService.getAuthor("Clive Barker", 1950);
        assertThat(authorList).isNotNull();
        assertThat(authorList.isEmpty()).isFalse();
    }

    @Test
    void addAuthorValidReturnsAuthor() {
        Author author = new Author("Stephen King", 1948);
        when(authorRepository.save(any(Author.class)))
                .thenReturn(author);
        Author author2 = authorService.addAuthor(author);
        assertThat(author2).isNotNull();
        assertThat(author2.getName()).isEqualTo("Stephen King");
    }

    @Test
    void deleteAuthorByName() {
        Author author = new Author("Clive Barker", 1950);
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(author));
        authorService.deleteAuthor(author.getName());
        verify(authorRepository).delete(any(Author.class));
    }

    @Test
    void updateAuthorPatchReturnsAuthor() {
        Author author = new Author("Edwardo", 1950);
        when(authorRepository.findByName(anyString()))
                .thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class)))
                .thenReturn(author);
        Author author2 = authorService.updateAuthor(author.getName(), "Clive Barker");
        assertThat(author2).isNotNull();
        assertThat(author2.getName()).isEqualTo(author.getName());
    }
}
