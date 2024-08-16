package com.galvanize.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookList {

    private List<Book> bookList;

    public BookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public BookList() {
        this.bookList = new ArrayList<>();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public boolean isEmpty() {
        return this.bookList.isEmpty();
    }


}
