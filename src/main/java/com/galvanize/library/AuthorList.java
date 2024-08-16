package com.galvanize.library;

import java.util.ArrayList;
import java.util.List;

public class AuthorList {

    private List<Author> authorList;

    public AuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public AuthorList() {
        this.authorList = new ArrayList<>();
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public boolean isEmpty() {
        return this.authorList.isEmpty();
    }
}
