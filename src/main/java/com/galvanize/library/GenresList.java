package com.galvanize.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenresList {

    private List<Genre> genresList;

    public GenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }

    public GenresList() {
        this.genresList = new ArrayList<>();
    }

    public List<Genre> getGenresList() {
        return genresList;
    }

    public boolean isEmpty() {
        return this.genresList.isEmpty();
    }


}
