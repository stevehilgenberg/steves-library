package com.galvanize.library;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookList getBookList() {
        return new BookList(bookRepository.findAll());
    }

    public BookList getBook(String title, int year) {
        List<Book> bookList = bookRepository.findByTitleContainsAndPublicationYear(title, year);
        if(!bookList.isEmpty()) {
            return new BookList(bookList);
        }
        return null;
    }

    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);
    }

    public Book updateTitle(String title) {
        Optional<Book> optBook = bookRepository.findByTitle(title);
        if(optBook.isPresent()) {
            optBook.get().setTitle(title);
            return bookRepository.save(optBook.get());
        }
        return null;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(String isbn) {
        Optional<Book> optBook = bookRepository.findByIsbn(isbn);
        if(optBook.isPresent()) {
            bookRepository.delete(optBook.get());
        } else {
            throw new BookNotFoundException();
        }
    }
}
