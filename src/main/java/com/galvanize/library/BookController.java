package com.galvanize.library;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library/book")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<BookList> getBookList() {
        BookList bookList;
        bookList = bookService.getBookList();
        return bookList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(bookList);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Book> getBook(@PathVariable String title) {
        try {
            bookService.getBook(title);
        } catch (BookNotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(bookService.getBook(title));
    }

    @PostMapping()
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PatchMapping("/{badTitle}")
    public Book updateBook(@PathVariable String badTitle,
                             @RequestBody UpdateBookNameRequest update) {
        Book book  = bookService.updateTitle(badTitle);
        book.setTitle(update.getTitle());
        return book;
    }

    @DeleteMapping("/{title}")
    public ResponseEntity deleteBook(@PathVariable String title) {
        try {
            bookService.deleteBook(title);
        } catch (BookNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void BookNotFoundExceptionHandler(BookNotFoundException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidBookExceptionHandler(InvalidBookException e) {
    }
}
