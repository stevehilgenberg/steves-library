package com.galvanize.library;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenresService {

    GenresRepository genresRepository;

    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public GenresList getGenresList() {
        return new GenresList(genresRepository.findAll());
    }

    public Genre getGenre(String name) {
        return genresRepository.findByName(name).orElse(null);
//        Optional<Genre> oGenre = genresRepository.findByGenre(name);
//        if(!oGenre.isEmpty()) {
//            return oGenre;
//        }
//        return null;
    }

    public Genre addGenre(Genre genre) {
        return genresRepository.save(genre);
    }

    public Genre updateGenre(String name) {
        Optional<Genre> oGenre = genresRepository.findByName(name);
        if(oGenre.isPresent()) {
            oGenre.get().setName(name);
            return genresRepository.save(oGenre.get());
        }
        return null;
    }

    public void deleteGenre(String name) {
        Optional<Genre> oGenre = genresRepository.findByName(name);
        if(oGenre.isPresent()) {
            genresRepository.delete(oGenre.get());
        } else {
            throw new GenreNotFoundException();
        }
    }
}
