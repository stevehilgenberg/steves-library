package com.galvanize.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    private static final Logger log = LoggerFactory.getLogger(BookServiceTests.class);
    private  BookService bookService;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() { bookService = new BookService(bookRepository); }

    @Test
    void GetBookListNoParamsReturnsList() {
        Book book = new Book("How To Program In Java", "123123123", 2020);
        when(bookRepository.findAll())
                .thenReturn(Arrays.asList(book));
        BookList bookList = bookService.getBookList();
        assertThat(bookList).isNotNull();
        assertThat(bookList.isEmpty()).isFalse();
    }

    @Test
    void getBookWithTitleYearParamReturnsList() {
        Book book = new Book("The Future OF Computers", "321321321", 1970);
        when(bookRepository.findByTitleContainsAndPublicationYear(anyString(), anyInt()))
                .thenReturn(Arrays.asList(book));
        BookList bookList = bookService.getBook("The Future Of Computer", 1970);
        assertThat(bookList).isNotNull();
        assertThat(bookList.isEmpty()).isFalse();
    }

    @Test
    void getBookWithIsbn() {
        Book book = new Book("The Future OF Computers", "321321321", 1970);
        when(bookRepository.findByIsbn(anyString()))
                .thenReturn(Optional.of(book));
        Book book2 = bookService.getBook(book.getIsbn());
        assertThat(book2).isNotNull();
        assertThat(book2.getIsbn()).isEqualTo(book.getIsbn());
    }

    @Test
    void addBookValidReturnsBook() {
        Book book = new Book("The Future OF Computers", "321321321", 1970);
        when(bookRepository.save(any(Book.class)))
                .thenReturn(book);
        Book book2 = bookService.addBook(book);
        assertThat(book2).isNotNull();
        assertThat(book2.getTitle()).isEqualTo("The Future OF Computers");
    }

    @Test
    void deleteBookByIsbn() {
        Book book = new Book("The Future OF Computers", "321321321", 1970);
        when(bookRepository.findByIsbn(anyString()))
                .thenReturn(Optional.of(book));
        bookService.deleteBook(book.getIsbn());
        verify(bookRepository).delete(any(Book.class));
    }

    @Test
    void deleteBookByIsbnNotExists() {
        when(bookRepository.findByIsbn(anyString()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(BookNotFoundException.class)
                .isThrownBy(() -> { bookService.deleteBook("NOTEXISTS-ISBN"); });
    }
}
